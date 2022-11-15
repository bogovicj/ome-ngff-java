package ome.ngff.transformations;

public abstract class ParametrizedCoordinateTransformation<T extends ParametrizedCoordinateTransformation<T>> 
	extends AbstractCoordinateTransformation<T>
{
	private final String path;

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
		this( type, null, null, null, path );
	}

	public ParametrizedCoordinateTransformation( T other )
	{
		super( other );
		this.path = other.getPath();
	}

	public ParametrizedCoordinateTransformation( T other, String[] inputAxes, String[] outputAxes )
	{
		super( other, inputAxes, outputAxes );
		this.path = other.getPath();
	}

	public String getPath()
	{
		return path;
	}

}
