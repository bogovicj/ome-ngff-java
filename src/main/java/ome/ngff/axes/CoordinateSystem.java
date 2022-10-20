package ome.ngff.axes;

import java.util.stream.IntStream;

public class CoordinateSystem
{
	public static final String KEY = "coordinateSystems";

	protected final String name;

	protected final Axis[] axes;

	public CoordinateSystem( String name, Axis[] axes )
	{
		this.name = name;
		this.axes = axes;
	}

	public String getName()
	{
		return name;
	}

	public Axis[] getAxes()
	{
		return axes;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof CoordinateSystem )
		{
			return ( ( CoordinateSystem ) other ).getName().equals( getName() );
		}
		else
			return false;
	}

	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}

	public static CoordinateSystem defaultArray( String name, int nd )
	{
		return new CoordinateSystem( name, IntStream.range( 0, nd ).mapToObj( i -> Axis.defaultArray( i ) ).toArray( Axis[]::new ) );
	}

}
