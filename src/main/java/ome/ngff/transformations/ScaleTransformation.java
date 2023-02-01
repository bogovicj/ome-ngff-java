package ome.ngff.transformations;

public class ScaleTransformation extends ParametrizedCoordinateTransformation< ScaleTransformation >
{
	public static final String TYPE = "scale";

	private double[] scale;

	public ScaleTransformation( final double[] scale )
	{
		this( "", scale );
	}

	public ScaleTransformation( final String name, final double[] scale )
	{
		super( TYPE, name, null );
		this.scale = scale;
	}

	public ScaleTransformation( final String name, final String path )
	{
		super( TYPE, name, path );
		scale = null;
	}

	public ScaleTransformation( ScaleTransformation other, final double[] scale )
	{
		super( other );
		this.scale = scale;
	}

	public ScaleTransformation( ScaleTransformation other )
	{
		super( other );
		this.scale = other.scale;
	}

	public double[] getScale()
	{
		return scale;
	}

}
