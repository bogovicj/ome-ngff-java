package org.janelia.saalfeldlab.n5;
/**
 * Copyright (c) 2018--2020, Saalfeld lab
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */


import ij.IJ;
import ij.ImagePlus;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.SubsampleIntervalView;
import net.imglib2.view.Views;
import org.janelia.saalfeldlab.n5.blosc.BloscCompression;
import org.janelia.saalfeldlab.n5.ij.N5Factory;
import org.janelia.saalfeldlab.n5.imglib2.N5Utils;
import org.janelia.saalfeldlab.n5.metadata.N5DatasetMetadata;
import org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v04.OmeNgffMetadata;
import org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.N5URL;
import org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v04.Axis;
import org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v04.OmeNgffMetadataParser;
import org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v04.OmeNgffMultiScaleMetadata;
import org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v04.OmeNgffMultiScaleMetadata.OmeNgffDataset;
import org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v04.coordinateTransformations.CoordinateTransformation;
import org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v04.coordinateTransformations.ScaleCoordinateTransformation;
import org.scijava.ItemVisibility;
import org.scijava.app.StatusService;
import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;

@Plugin(type = Command.class, menuPath = "File>Save As>Ngff")
public class NgffExporter extends ContextCommand implements WindowListener {

  public static final String GZIP_COMPRESSION = "gzip";
  public static final String RAW_COMPRESSION = "raw";
  public static final String LZ4_COMPRESSION = "lz4";
  public static final String XZ_COMPRESSION = "xz";
  public static final String BLOSC_COMPRESSION = "blosc";

  public static final String NONE = "None";

  public static final String NO_OVERWRITE = "No overwrite";
  public static final String OVERWRITE = "Overwrite";
  public static final String WRITE_SUBSET = "Overwrite subset";

  public static enum OVERWRITE_OPTIONS {NO_OVERWRITE, OVERWRITE, WRITE_SUBSET}

  @Parameter(visibility = ItemVisibility.MESSAGE, required = false)
  private String message = "Export an ImagePlus to an OME-NGFF";

  @Parameter
  private LogService log;

  @Parameter
  private StatusService status;

  @Parameter
  private UIService ui;

  @Parameter(label = "Image")
  private ImagePlus image; // or use Dataset? - maybe later

  @Parameter(label = "Root url")
  private String rootLocation;

  @Parameter(
		  label = "Dataset",
		  required = false)
  private String dataset;

  @Parameter(label = "Block size")
  private String blockSizeArg;

  @Parameter(label = "Number of scales")
  private Integer numScales = 1;

  @Parameter(
		  label = "Compression",
		  choices = {GZIP_COMPRESSION, RAW_COMPRESSION, LZ4_COMPRESSION, XZ_COMPRESSION, BLOSC_COMPRESSION},
		  style = "listBox")
  private String compressionArg = GZIP_COMPRESSION;

  @Parameter(label = "Thread count", required = true, min = "1", max = "256")
  private int nThreads = 1;

  @Parameter(
		  label = "Overwrite options", required = true,
		  choices = {NO_OVERWRITE, OVERWRITE, WRITE_SUBSET},
		  description = "Determines whether overwriting datasets allows, and how overwriting occurs."
				  + "If selected will overwrite values in an existing dataset if they exist.")
  private String overwriteChoices = NO_OVERWRITE;

  @Parameter(label = "Overwrite subset offset", required = false,
		  description = "The point in pixel units where the origin of this image will be written into the n5-dataset (comma-delimited)")
  private String subsetOffset;

  private int[] blockSize;


	public NgffExporter() {
	}

	  public void setOptions(
		  final ImagePlus image,
		  final String rootLocation,
		  final String dataset,
		  final String blockSizeArg,
		  final String compression,
		  final int nScales,
		  final String overwriteOption,
		  final String subsetOffset,
		  final int nThreads) {

	this.image = image;
	this.rootLocation = rootLocation;
	this.dataset = N5URL.normalizeGroupPath(dataset);

	this.blockSizeArg = blockSizeArg;
	this.compressionArg = compression;
	
	this.numScales = nScales;

	this.overwriteChoices = overwriteOption;
	this.subsetOffset = subsetOffset;
	
	this.nThreads = nThreads;
  }

	public void parseBlockSize() {

		final int nd = image.getNDimensions();
		String[] blockArgList = blockSizeArg.split(",");
		blockSize = new int[nd];
		int i = 0;
		while (i < blockArgList.length && i < nd) {
			blockSize[i] = Integer.parseInt(blockArgList[i]);
			i++;
		}
		int N = blockArgList.length - 1;

		while (i < nd) {
			blockSize[i] = blockSize[N];
			i++;
		}
	}

	public <T extends RealType<T> & NativeType<T>, M extends N5DatasetMetadata> void process()
			throws IOException, InterruptedException, ExecutionException {

		final N5Writer n5 = new N5Factory()
				.gsonBuilder( OmeNgffMetadataParser.gsonBuilder())
				.openWriter(rootLocation);
		final Compression compression = getCompression();
		parseBlockSize();

		double[] res;
		if (image.getNSlices() <= 1)
			res = new double[] { image.getCalibration().pixelWidth, image.getCalibration().pixelHeight };
		else
			res = new double[] { image.getCalibration().pixelWidth, image.getCalibration().pixelHeight,
					image.getCalibration().pixelDepth };

		final String normDataset = N5URL.normalizeGroupPath(dataset);

		Img<T> img = ImageJFunctions.wrap(image);
		write( img, n5, normDataset + "/s0", compression );

		DatasetAttributes[] dsetAttrs = new DatasetAttributes[numScales];
		OmeNgffDataset[] msDatasets = new OmeNgffDataset[numScales];

		String dset = normDataset + "/s0";
		dsetAttrs[0] = n5.getDatasetAttributes(dset);
		msDatasets[0] = new OmeNgffDataset();
		msDatasets[0].path = dset;
		msDatasets[0].coordinateTransformations = new CoordinateTransformation[] {
				new ScaleCoordinateTransformation( res )
		};

		int scale = 1;
		for( int i = 1; i < numScales; i++ ) {
			
			scale *= 2;
			final SubsampleIntervalView<T> imgDown = downsampleSimple( img, scale );
			dset = String.format("%s/s%d", normDataset, i);

			write(imgDown, n5, dset, compression );
			dsetAttrs[i] = n5.getDatasetAttributes(dset);
			
			msDatasets[i] = new OmeNgffDataset();
			msDatasets[i].path = dset;
			final double s = scale;
			msDatasets[i].coordinateTransformations = new CoordinateTransformation[] {
					new ScaleCoordinateTransformation( 
						Arrays.stream(res).map( x -> { return x * s; }).toArray())
			};
	
		}

		final OmeNgffMultiScaleMetadata ms = buildMetadata(normDataset, dsetAttrs, msDatasets);
		OmeNgffMultiScaleMetadata[] msList = new OmeNgffMultiScaleMetadata[] { ms };

		OmeNgffMetadata meta = new OmeNgffMetadata(normDataset, msList);
		try {
			new OmeNgffMetadataParser().writeMetadata(meta, n5, normDataset);
		} catch (Exception e) {
			e.printStackTrace();
		}

		n5.close();
	}

	public <T extends RealType<T> & NativeType<T>, M extends N5DatasetMetadata> SubsampleIntervalView<T> downsampleSimple(
			RandomAccessibleInterval<T> img, int downsampleFactor) {
		return Views.subsample(img, downsampleFactor);
	}
	
	public OmeNgffMultiScaleMetadata buildMetadata(final String path, final DatasetAttributes[] dsetAttrs, final OmeNgffDataset[] datasets) {
		
		// TODO do something with channels and timepoints
		final int nz = image.getNChannels();
		final String unit = image.getCalibration().getUnit();

		int N = 2;
		if (nz >= 1) {
			N++;
		}

		final Axis[] axes = new Axis[N];
		double[] pixelSpacing = new double[N];

		axes[0] = new Axis("x", "space", unit);
		pixelSpacing[0] = image.getCalibration().pixelWidth;

		axes[1] = new Axis("y", "space", unit);
		pixelSpacing[1] = image.getCalibration().pixelHeight;

		if (nz >= 1) {
			axes[2] = new Axis("z", "space", unit);
			pixelSpacing[2] = image.getCalibration().pixelDepth;
		}
		String name = image.getTitle();
		String type = "sampling";
		String version = "0.4";

		return new OmeNgffMultiScaleMetadata(
			N, path, name, type, version, axes,
			datasets, dsetAttrs,
			null, null ); // no global coordinate transforms of downsampling metadata
	}

	@SuppressWarnings({ "rawtypes" })
	private <T extends RealType & NativeType, M extends N5DatasetMetadata> void write(
			final RandomAccessibleInterval<T> image,
			final N5Writer n5,
			final String dataset,
			final Compression compression )
			throws IOException, InterruptedException, ExecutionException {

		if (overwriteChoices.equals(NO_OVERWRITE) && n5.datasetExists(dataset)) {
			if (ui != null)
				ui.showDialog(String.format("Dataset (%s) already exists, not writing.", dataset));
			else
				System.out.println(String.format("Dataset (%s) already exists, not writing.", dataset));

			return;
		}

		// Here, either allowing overwrite, or not allowing, but the dataset does not
		// exist

		// use threadPool even for single threaded execution for progress monitoring
		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		progressMonitor(threadPool);
		N5Utils.save(image, n5, dataset, blockSize, compression, Executors.newFixedThreadPool(nThreads));
	}

	@SuppressWarnings("unused")
	private static long[] getOffsetForSaveSubset3d(final ImagePlus imp) {
		final int nd = imp.getNDimensions();
		long[] offset = new long[nd];

		offset[0] = (int) imp.getCalibration().xOrigin;
		offset[1] = (int) imp.getCalibration().yOrigin;

		int j = 2;
		if (imp.getNSlices() > 1)
			offset[j++] = (int) imp.getCalibration().zOrigin;

		return offset;
	}

	@Override
	public void run() {

		// add more options
		try {
			process();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void progressMonitor( final ThreadPoolExecutor exec )
	{
		new Thread()
		{
			public void run()
			{
				IJ.showProgress( 0.01 );
				try
				{
					Thread.sleep( 333 );
					boolean done = false;
					while( !done && !exec.isShutdown() )
					{
						final long i = exec.getCompletedTaskCount();
						final long N = exec.getTaskCount();
						done = i == N;
						IJ.showProgress( (double)i / N );
						Thread.sleep( 333 );
					}
				}
				catch ( InterruptedException e ) { }
				IJ.showProgress( 1.0 );
			}
		}.start();
		return;
	}

	private Compression getCompression() {

		switch (compressionArg) {
		case GZIP_COMPRESSION:
			return new GzipCompression();
		case LZ4_COMPRESSION:
			return new Lz4Compression();
		case XZ_COMPRESSION:
			return new XzCompression();
		case RAW_COMPRESSION:
			return new RawCompression();
		case BLOSC_COMPRESSION:
			return new BloscCompression();
		default:
			return new RawCompression();
		}
	}

	@Override
	public void windowOpened(final WindowEvent e) {}

	@Override
	public void windowIconified(final WindowEvent e) {}

	@Override
	public void windowDeiconified(final WindowEvent e) {}

	@Override
	public void windowDeactivated(final WindowEvent e) {}

	@Override
	public void windowClosing(final WindowEvent e) {

	  try {
		process();
	  } catch (final IOException e1) {
		e1.printStackTrace();
	  } catch (final InterruptedException e1) {
		e1.printStackTrace();
	  } catch (final ExecutionException e1) {
		e1.printStackTrace();
		}
	}

	@Override
	public void windowClosed(final WindowEvent e) {}

	@Override
	public void windowActivated(final WindowEvent e) {}

}
