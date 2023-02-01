package ome.ngff.omero;

import java.awt.Color;

public class Channel
{
	private final String label;

	private final String colorString;

	private final String family;
	
	private final int coefficient;

	private transient final Color color;

	private final boolean active;

	private final boolean inverted;

	private final String emissionWave;

	private final Window window;

	public Channel( final String label, final String colorString, final String family, final int coefficient, final boolean active, final boolean inverted, final String emissionWave, final Window window )
	{
		this.label = label;
		this.colorString = colorString;
		this.family = family;
		this.coefficient = coefficient;
		this.active = active;
		this.inverted = inverted;
		this.emissionWave = emissionWave;
		this.window = window;

		final int r = Integer.parseInt( colorString.substring( 0, 2 ), 16 );
		final int g = Integer.parseInt( colorString.substring( 2, 4 ), 16 );
		final int b = Integer.parseInt( colorString.substring( 4, 6 ), 16 );
		color = new Color( r, g, b, 255 );
	}

	public String getLabel()
	{
		return label;
	}

	public Color getColor()
	{
		return color;
	}

	public String getColorString()
	{
		return colorString;
	}

	public String getFamily()
	{
		return family;
	}

	public int getCoefficient()
	{
		return coefficient;
	}

	public boolean isActive()
	{
		return active;
	}

	public boolean isInverted()
	{
		return inverted;
	}

	public String getEmissionWave()
	{
		return emissionWave;
	}

	public Window getWindow()
	{
		return window;
	}

}
