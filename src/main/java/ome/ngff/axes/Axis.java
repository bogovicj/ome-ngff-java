package ome.ngff.axes;

public class Axis
{

	public static final String CHANNEL_TYPE = "channel";
	public static final String TIME_TYPE = "time";
	public static final String SPATIAL_TYPE = "space";
	public static final String ARRAY_TYPE = "array";

	protected final String name;
	protected final String type;
	protected final String unit;
	protected final boolean discrete;

	public Axis( String name, String type, String unit, boolean discrete )
	{
		this.name = name;
		this.type = type;
		this.unit = unit;
		this.discrete = discrete;
	}

	public Axis( String name, String type, String unit )
	{
		this( name, type, unit, false );
	}

	public Axis( String name, String type )
	{
		this( name, type, null, false );
	}

	public Axis( String name )
	{
		this( name, null, null, false );
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public String getUnit()
	{
		return unit;
	}

	public boolean isDiscrete()
	{
		return discrete;
	}

	public Axis copy( String name )
	{
		return new Axis( name, type, unit, discrete );
	}

	public Axis copy()
	{
		return new Axis( name, type, unit, discrete );
	}

}
