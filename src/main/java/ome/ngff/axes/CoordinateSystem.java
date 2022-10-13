package ome.ngff.axes;

public class CoordinateSystem
{
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

}
