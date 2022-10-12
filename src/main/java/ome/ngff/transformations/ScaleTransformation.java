package ome.ngff.transformations;

public class ScaleTransformation extends ParametrizedCoordinateTransformation
{
	public static final String TYPE = "scale";
	
	private double[] scale;

	public ScaleTransformation( final double[] scale )
	{
		this( "", null, null, scale );
	}

	public ScaleTransformation( final String input, final String output, final double[] scale )
	{
		this( "", input, output, scale );
	}

	public ScaleTransformation( final String name, final String input, final String output, final double[] scale )
	{
		super( TYPE, name, input, output, null );
		this.scale = scale;
	}

	public ScaleTransformation( final String input, final String output, final String path )
	{
		this( "", input, output, path );
	}

	public ScaleTransformation( final String name, final String input, final String output, final String path )
	{
		super( TYPE, name, input, output, path );
		scale = null;
	}

	public double[] getScale()
	{
		return scale;
	}

}
