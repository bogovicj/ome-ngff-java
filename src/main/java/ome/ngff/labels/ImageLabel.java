package ome.ngff.labels;

public class ImageLabel
{
	private final String version;

	private final ImageLabelColors[] colors;

	private final ImageLabelProperties[] properties;

	private final Source source;

	public ImageLabel( final String version, final ImageLabelColors[] colors, final ImageLabelProperties[] properties, final Source source )
	{
		this.version = version;
		this.colors = colors;
		this.properties = properties;
		this.source = source;
	}

	public String getVersion()
	{
		return version;
	}

	public ImageLabelColors[] getColors()
	{
		return colors;
	}

	public ImageLabelProperties[] getProperties()
	{
		return properties;
	}

	public Source getSource()
	{
		return source;
	}
	
	public int[] getColor( final long label )
	{
		int[] c = null;
		// The last color appearing for the label value is returned
		for( ImageLabelColors labelColor : colors )
		{
			if( labelColor.getImageLabel() == label )
				c = labelColor.getRgba();
		}
		return c;
	}
	
	public <T> T getProperty( final long label, String key, Class<T> clazz )
	{
		T out = null;
		// The last property appearing for the label value is returned
		for( ImageLabelProperties labelProp : properties )
		{
			if( labelProp.getImageLabel() == label )
			{
				final T res = labelProp.get( key, clazz );
				out = res != null ? res : out;
			}
		}
		return out;
	}

}
