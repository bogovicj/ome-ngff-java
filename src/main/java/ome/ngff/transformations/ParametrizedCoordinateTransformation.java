package ome.ngff.transformations;

public abstract class ParametrizedCoordinateTransformation< T extends ParametrizedCoordinateTransformation< T > > extends AbstractCoordinateTransformation< T >
{
	private final String path;

	public ParametrizedCoordinateTransformation( final String type, final String name, final String path )
	{
		super( type, name );
		this.path = path;
	}

	public ParametrizedCoordinateTransformation( final String type, final String path )
	{
		this( type, null, path );
	}

	public ParametrizedCoordinateTransformation( T other )
	{
		super( other );
		this.path = other.getPath();
	}

	public String getPath()
	{
		return path;
	}

}
