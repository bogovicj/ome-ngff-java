package ome.ngff.transformations;

public class SequenceTransformation extends AbstractCoordinateTransformation<SequenceTransformation> {
	
	public static final String TYPE = "sequence";

	protected final CoordinateTransformation[] transformations;

	public SequenceTransformation( final String name, final String input, final String output, final CoordinateTransformation[] transformations) {
		super(TYPE, name, input, output );
		this.transformations = transformations;
	}

	public SequenceTransformation( final String input, final String output, final CoordinateTransformation[] transformations) {
		this( null, input, output, transformations );
	}
	
	public SequenceTransformation( SequenceTransformation other )
	{
		super(other);
		this.transformations = other.transformations; // TODO is shallow copy okay?
	}

	public CoordinateTransformation[] getTransformations()
	{
		return transformations;
	}

}
