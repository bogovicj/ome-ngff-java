package ome.ngff.transformations;

public class BijectionTransformation extends AbstractCoordinateTransformation<BijectionTransformation>
{
	public static final String TYPE = "bijection";

	protected CoordinateTransformation forward;

	protected CoordinateTransformation inverse;

	public BijectionTransformation( final String name, final String input, final String output,
			CoordinateTransformation forward, CoordinateTransformation inverse)
	{
		super( TYPE, name, input, output );
		this.forward = forward;
		this.inverse = inverse;
	}

	public BijectionTransformation( BijectionTransformation other )
	{
		super( other );
		this.forward = other.forward;
		this.inverse = other.inverse;
	}

	public CoordinateTransformation getForward()
	{
		return forward;
	}

	public CoordinateTransformation getInverse()
	{
		return inverse;
	}
}
