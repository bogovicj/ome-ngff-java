package ome.ngff.transformations;

public class SequenceCoordinateTransform extends CoordinateTransformation {

	protected final CoordinateTransformation[] transformations;

	public SequenceCoordinateTransform( final String name, final String input, final String output, final CoordinateTransformation[] transformations) {
		super("sequence", name, input, output );
		this.transformations = transformations;
	}
	
	public SequenceCoordinateTransform( final String input, final String output, final CoordinateTransformation[] transformations) {
		super("sequence", "", input, output );
		this.transformations = transformations;
	}

	public CoordinateTransformation[] getTransformations()
	{
		return transformations;
	}

}
