package ome.ngff.omero;

public class Omero
{
	private final String version;

	private final String name;

	private final Channel[] channels;

	private final RDefs rdefs;

	public Omero( final String version, final String name, final Channel[] channels, final RDefs rdefs )
	{
		this.version = version;
		this.name = name;
		this.channels = channels;
		this.rdefs = rdefs;
	}

	public String getVersion()
	{
		return version;
	}

	public String getName()
	{
		return name;
	}

	public Channel[] getChannels()
	{
		return channels;
	}

	public RDefs getRdefs()
	{
		return rdefs;
	}

}
