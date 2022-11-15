package ome.ngff.transformations;

public abstract class AbstractCoordinateTransformation<T extends CoordinateTransformation> implements CoordinateTransformation
{
	protected final String name;

	protected final String type;
	
	protected final String input;

	protected final String output;
	
	protected transient final String[] inputAxes;

	protected transient final String[] outputAxes;

	public AbstractCoordinateTransformation( final String type, final String name, final String input, final String output )
	{
		this.name = name;
		this.type = type;
		this.input = input;
		this.output = output;
		inputAxes = null;
		outputAxes = null;
	}

	public AbstractCoordinateTransformation( final String type, final String name, final String[] inputAxes, final String[] outputAxes )
	{
		this.name = name;
		this.type = type;
		this.input = null;
		this.output = null;
		this.inputAxes = inputAxes;
		this.outputAxes = outputAxes;
	}

	public AbstractCoordinateTransformation( final String type, final String name )
	{
		this( type, name, new String[]{}, new String[]{} );
	}

	public AbstractCoordinateTransformation( final String type )
	{
		this( type, "" );
	}
	
	public AbstractCoordinateTransformation( T other )
	{
		this.name = other.getName();
		this.type = other.getType();
		this.input = other.getInput();
		this.output = other.getOutput();
		this.inputAxes = other.getInputAxes();
		this.outputAxes = other.getOutputAxes();
	}

	public AbstractCoordinateTransformation( T other, String[] inputAxes, String[] outputAxes )
	{
		this.name = other.getName();
		this.type = other.getType();
		this.input = other.getInput();
		this.output = other.getOutput();
		this.inputAxes = inputAxes;
		this.outputAxes = outputAxes;
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
	
	public String[] getInputAxes()
	{
		return inputAxes;
	}

	public String[] getOutputAxes()
	{
		return outputAxes;
	}

	public String toString()
	{
		return String.format( "\"%s\" (%s): %s > %s ", getName(), getType(), getInput(), getOutput() );
	}

}
