package ome.ngff.transformations;

public class CoordinatesTransformation extends ParametrizedCoordinateTransformation<CoordinatesTransformation>
{
	public static final String TYPE = "coordinates";

	public CoordinatesTransformation( final String input, final String output, final String path )
	{
		this( null, input, output, path );
	}

	public CoordinatesTransformation( final String name, final String input, final String output, final String path )
	{
		super( TYPE, name, input, output, path );
	}

	public CoordinatesTransformation( CoordinatesTransformation other )
	{
		super( other );
	}

	public CoordinatesTransformation( CoordinatesTransformation other, String[] inputAxes, String[] outputAxes )
	{
		super( other, inputAxes, outputAxes );
	}

}
