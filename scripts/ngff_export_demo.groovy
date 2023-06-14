#@ ImagePlus imp
#@ String rootLocation
#@ String(value="/") dataset
#@ Integer(value=1) numScales
#@ String(value="32") blockSize
#@ String(value="gzip") compression
#@ Integer(value=1) numThreads

import org.janelia.saalfeldlab.n5.NgffExporter;

exporter = new NgffExporter();
exporter.setOptions(imp, rootLocation, dataset, blockSize, compression, numScales,
        NgffExporter.OVERWRITE_OPTIONS.NO_OVERWRITE.toString(), "", numThreads);
exporter.process();
