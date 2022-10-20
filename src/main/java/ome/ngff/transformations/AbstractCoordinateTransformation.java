package ome.ngff.transformations;

public abstract class AbstractCoordinateTransformation<T extends CoordinateTransformation> implements CoordinateTransformation
{
	protected final String name;

	protected final String type;
	
	protected final String input;

	protected final String output;

	public AbstractCoordinateTransformation( final String type, final String name, final String input, final String output )
	{
		this.name = name;
		this.type = type;
		this.input = input;
		this.output = output;
	}

	public AbstractCoordinateTransformation( final String type, final String name )
	{
		this( type, name, null, null );
	}

	public AbstractCoordinateTransformation( final String type )
	{
		this( type, "", null, null );
	}
	
	public AbstractCoordinateTransformation( T other )
	{
		this.name = other.getName();
		this.type = other.getType();
		this.input = other.getInput();
		this.output = other.getOutput();
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public String getInput()
	{
		return input;
	}

	public String getOutput()
	{
		return output;
	}

	public String toString()
	{
		return String.format( "\"%s\" (%s): %s > %s ", getName(), getType(), getInput(), getOutput() );
	}

}
