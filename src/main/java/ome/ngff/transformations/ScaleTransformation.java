package ome.ngff.transformations;

public class ScaleTransformation extends ParametrizedCoordinateTransformation
{
	public static final String TYPE = "scale";
	
	private double[] scale;

	public ScaleTransformation( final double[] scale )
	{
		this( null, null, null, scale );
	}

	public ScaleTransformation( final String input, final String output, final double[] scale )
	{
		this( null, input, output, scale );
	}

	public ScaleTransformation( final String name, final String input, final String output, final double[] scale )
	{
		super( TYPE, name, input, output, null );
		this.scale = scale;
	}

	public ScaleTransformation( final String input, final String output, final String path )
	{
		this( null, input, output, path );
	}

	public ScaleTransformation( final String name, final String input, final String output, final String path )
	{
		super( TYPE, name, input, output, path );
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
