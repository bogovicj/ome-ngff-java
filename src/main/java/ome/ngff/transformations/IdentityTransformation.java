package ome.ngff.transformations;

public class IdentityTransformation extends CoordinateTransformation
{
	public IdentityTransformation()
	{
		super( "identity" );
	}

	public IdentityTransformation( String name )
	{
		super( "identity", name );
	}

	public IdentityTransformation( String name, String input, String output )
	{
		super( "identity", name, input, output );
	}

}
