package ome.ngff.omero;

public class Window
{
	private final double start, end, min, max;

	public Window( final double start, final double end, final double min, final double max )
	{
		this.start = start;
		this.end = end;
		this.min = min;
		this.max = max;
	}

	public double getStart()
	{
		return start;
	}

	public double getEnd()
	{
		return end;
	}

	public double getMin()
	{
		return min;
	}

	public double getMax()
	{
		return max;
	}

}
