package ome.ngff.transformations;

public class IdentityTransformation extends CoordinateTransformation
{
	public static String TYPE = "identity";

	public IdentityTransformation()
	{
		super( TYPE );
	}

	public IdentityTransformation( String name )
	{
		super( TYPE, name );
	}

	public IdentityTransformation( String name, String input, String output )
	{
		super( TYPE, name, input, output );
	}

}