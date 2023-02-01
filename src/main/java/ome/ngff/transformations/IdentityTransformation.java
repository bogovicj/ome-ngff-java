package ome.ngff.transformations;

public class IdentityTransformation extends AbstractCoordinateTransformation<IdentityTransformation>
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

	public IdentityTransformation( IdentityTransformation other )
	{
		super( other );
	}

}