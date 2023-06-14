package org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v03;


import org.janelia.saalfeldlab.n5.N5Writer;
import org.janelia.saalfeldlab.n5.metadata.N5MetadataWriter;
import org.janelia.saalfeldlab.n5.metadata.N5SingleScaleMetadata;

public class OmeNgffSingleScaleMetadataWriter implements N5MetadataWriter< N5SingleScaleMetadata >
{

	@Override
	public void writeMetadata(N5SingleScaleMetadata t, N5Writer n5, String path) throws Exception {
		// nothing to do
	}


}
