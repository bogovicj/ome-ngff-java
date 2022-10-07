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

}
