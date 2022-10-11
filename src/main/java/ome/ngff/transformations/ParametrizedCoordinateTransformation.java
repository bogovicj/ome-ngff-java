package ome.ngff.transformations;

public abstract class ParametrizedCoordinateTransformation extends CoordinateTransformation
{
	protected final String path;

	public ParametrizedCoordinateTransformation( final String type, final String name, final String input, final String output, final String path )
	{
		super( type, name, input, output );
		this.path = path;
	}

	public ParametrizedCoordinateTransformation( final String type, final String name, final String path )
	{
		this( type, name, null, null, path );
	}

	public ParametrizedCoordinateTransformation( final String type, final String path )
	{
		this( type, "", null, null, path );
	}

}
