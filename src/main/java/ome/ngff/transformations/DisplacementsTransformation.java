package ome.ngff.transformations;

public class DisplacementsTransformation extends ParametrizedCoordinateTransformation
{
	public static final String TYPE = "displacements";

	public DisplacementsTransformation( final String input, final String output, final String path )
	{
		this( null, input, output, path );
	}

	public DisplacementsTransformation( final String name, final String input, final String output, final String path )
	{
		super( TYPE, name, input, output, path );
	}
}
