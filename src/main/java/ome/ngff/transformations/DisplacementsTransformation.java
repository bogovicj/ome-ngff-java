package ome.ngff.transformations;

public class DisplacementsTransformation extends ParametrizedInterpolatedCoordinateTransformation<DisplacementsTransformation>
{
	public static final String TYPE = "displacements";

	public DisplacementsTransformation( final String input, final String output, final String path, final String interpolation )
	{
		this( null, input, output, path, interpolation );
	}

	public DisplacementsTransformation( final String name, final String input, final String output, final String path, 
			final String interpolation )
	{
		super( TYPE, name, input, output, path, interpolation );
	}

	public DisplacementsTransformation( DisplacementsTransformation other )
	{
		super( other );
	}

	public DisplacementsTransformation( DisplacementsTransformation other, String[] inputAxes, String[] outputAxes )
	{
		super( other, inputAxes, outputAxes );
	}
}
