package ome.ngff.transformations;

public class ScaleTransformation extends CoordinateTransformation
{
	public static final String TYPE = "scale";
	
	private final double[] scale;

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
		super( TYPE, name, input, output );
		this.scale = scale;
	}

	public double[] getScale()
	{
		return scale;
	}

}
