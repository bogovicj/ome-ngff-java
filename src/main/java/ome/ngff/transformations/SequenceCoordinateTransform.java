package ome.ngff.transformations;

public class SequenceCoordinateTransform extends CoordinateTransformation {
	
	public static String TYPE = "sequence";

	protected final CoordinateTransformation[] transformations;

	public SequenceCoordinateTransform( final String name, final String input, final String output, final CoordinateTransformation[] transformations) {
		super(TYPE, name, input, output );
		this.transformations = transformations;
	}

	public SequenceCoordinateTransform( final String input, final String output, final CoordinateTransformation[] transformations) {
		this( null, input, output, transformations );
	}

	public CoordinateTransformation[] getTransformations()
	{
		return transformations;
	}

}
