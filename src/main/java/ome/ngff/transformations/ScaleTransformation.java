package ome.ngff.transformations;

public class ScaleTransformation extends CoordinateTransformation
{
	public static final String TYPE = "scale";
	
	private final double[] scale;

	public ScaleTransformation( final double[] scale )
	{
		this( "", scale );
	}

	public ScaleTransformation( String name, final double[] scale )
	{
		super( TYPE, name );
		this.scale = scale;
	}

	public double[] getScale()
	{
		return scale;
	}

}
