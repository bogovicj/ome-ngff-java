package org.janelia.saalfeldlab.n5.universe.metadata.ome.ngff.v04;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.janelia.saalfeldlab.n5.DatasetAttributes;
import org.janelia.saalfeldlab.n5.N5Reader;
import org.janelia.saalfeldlab.n5.N5Writer;
import org.janelia.saalfeldlab.n5.N5TreeNode;
import org.janelia.saalfeldlab.n5.metadata.N5DatasetMetadata;
import org.janelia.saalfeldlab.n5.metadata.N5MetadataParser;
import org.janelia.saalfeldlab.n5.metadata.N5MetadataWriter;
import org.janelia.saalfeldlab.n5.metadata.N5SingleScaleMetadata;

public class OmeNgffSingleScaleMetadataWriter implements N5MetadataWriter< N5SingleScaleMetadata >
{

	@Override
	public void writeMetadata(N5SingleScaleMetadata t, N5Writer n5, String path) throws Exception {
		// nothing to do
	}


}
