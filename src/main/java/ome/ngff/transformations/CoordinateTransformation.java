package ome.ngff.transformations;

public abstract class CoordinateTransformation
{
	protected final String name;

	protected final String type;
	
	protected final String input;

	protected final String output;

	public CoordinateTransformation( final String type, final String name, final String input, final String output )
	{
		this.name = name;
		this.type = type;
		this.input = input;
		this.output = output;
	}

	public CoordinateTransformation( final String type, final String name )
	{
		this( type, name, null, null );
	}

	public CoordinateTransformation( final String type )
	{
		this( type, "", null, null );
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

}
