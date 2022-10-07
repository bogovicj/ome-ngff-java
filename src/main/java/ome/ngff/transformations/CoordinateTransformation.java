package ome.ngff.transformations;

public abstract class CoordinateTransformation
{
	protected final String name;

	protected final String type;

	public CoordinateTransformation( final String type, final String name )
	{
		this.name = name;
		this.type = type;
	}

	public CoordinateTransformation( final String type )
	{
		this( type, "" );
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

}
