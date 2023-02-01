package ome.ngff.transformations;

public abstract class AbstractCoordinateTransformation< T extends CoordinateTransformation > implements CoordinateTransformation
{
	protected final String name;

	protected final String type;

	public AbstractCoordinateTransformation( final String type, final String name )
	{
		this.name = name;
		this.type = type;
	}

	public AbstractCoordinateTransformation( final String type )
	{
		this( type, "" );
	}

	public AbstractCoordinateTransformation( T other )
	{
		this.name = other.getName();
		this.type = other.getType();
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public String toString()
	{
		return String.format( "\"%s\" (%s): %s > %s ", getName(), getType() );
	}

}
