package ome.ngff.omero;

public class RDefs
{
	public static enum Model
	{
		color, greyscale
	};

	private final long defaultT;

	private final long defaultZ;

	private final Model model;

	public RDefs( final long defaultT, final long defaultZ, final Model model )
	{
		this.defaultT = defaultT;
		this.defaultZ = defaultZ;
		this.model = model;
	}

	public long getDefaultT()
	{
		return defaultT;
	}

	public long getDefaultZ()
	{
		return defaultZ;
	}

	public Model getModel()
	{
		return model;
	}
}
