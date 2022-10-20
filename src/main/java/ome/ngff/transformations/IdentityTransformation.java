package ome.ngff.transformations;

public class IdentityTransformation extends AbstractCoordinateTransformation
{
	public static final String TYPE = "identity";

	public IdentityTransformation()
	{
		super( TYPE );
	}

	public IdentityTransformation( String name )
	{
		super( TYPE, name );
	}

	public IdentityTransformation( String input, String output )
	{
		super( TYPE, null, input, output );
	}

	public IdentityTransformation( String name, String input, String output )
	{
		super( TYPE, name, input, output );
	}

	public IdentityTransformation( IdentityTransformation other )
	{
		super( other );
	}
}