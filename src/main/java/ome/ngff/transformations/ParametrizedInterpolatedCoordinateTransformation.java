package ome.ngff.transformations;

public abstract class ParametrizedInterpolatedCoordinateTransformation<T extends ParametrizedInterpolatedCoordinateTransformation<T>> 
	extends ParametrizedCoordinateTransformation< T >
{
	public static final String LINEAR_INTERPOLATION = "linear";
	public static final String NEAREST_INTERPOLATION = "nearest";
	public static final String CUBIC_INTERPOLATION = "cubic";

	public final String interpolation;

	public ParametrizedInterpolatedCoordinateTransformation( final String type, final String name, 
			final String input, final String output, 
			final String path, final String interpolation )
	{
		super( type, name, input, output, path );
		this.interpolation = interpolation;
	}

	public ParametrizedInterpolatedCoordinateTransformation( final String type, final String name, final String path,
			final String interpolation )
	{
		this( type, name, null, null, path, interpolation );
	}

	public ParametrizedInterpolatedCoordinateTransformation( final String type, final String path, final String interpolation)
	{
		this( type, null, null, null, path, interpolation);
	}

	public ParametrizedInterpolatedCoordinateTransformation( final String type, final String name, 
			final String input, final String output, 
			final String path )
	{
		this( type, name, input, output, path, LINEAR_INTERPOLATION );
	}

	public ParametrizedInterpolatedCoordinateTransformation( final String type, final String path )
	{
		this( type, null, null, null, path, LINEAR_INTERPOLATION );
	}

	public ParametrizedInterpolatedCoordinateTransformation( T other )
	{
		super( other );
		this.interpolation = other.interpolation;
	}

	public ParametrizedInterpolatedCoordinateTransformation( T other, String[] inputAxes, String[] outputAxes )
	{
		super( other, inputAxes, outputAxes );
		this.interpolation = other.interpolation;
	}

	public String getInterpolation()
	{
		return interpolation;
	}

}
