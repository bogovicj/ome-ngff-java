package ome.ngff.multiscales;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;

import ome.ngff.axes.Axis;
import ome.ngff.transformations.CoordinateTransformation;

import java.util.Arrays;
import java.util.List;

public class MultiscalesV04
{
	// key in json for multiscales
	public static final String MULTI_SCALE_KEY = "multiscales";

	// Serialisation
	private String version;

	private String name;

	private String type;

	private Axis[] axes; // from v0.4+ within JSON

	private Dataset[] datasets;

	// from v0.4+ within JSON
	private CoordinateTransformation[] coordinateTransformations; 

	// Runtime:
	// Simply contains the {@codeAxes[] axes}
	// but in reversed order to accommodate
	// the Java array ordering of the image data.
	private transient List< Axis > axisList;

	private int numDimensions;

	public MultiscalesV04() {}

	public void init()
	{
		axisList = Lists.reverse( Arrays.asList( axes ) );
		numDimensions = axisList.size();
	}

	// TODO Can this be done with a JSONAdapter ?
	public void applyVersionFixes( JsonElement multiscales )
	{
		String version = multiscales.getAsJsonObject().get( "version" ).getAsString();
		if ( version.equals( "0.3" ) )
		{
			JsonElement axes = multiscales.getAsJsonObject().get( "axes" );
			// FIXME
			// - populate Axes[]
			// - populate coordinateTransformations[]
			throw new RuntimeException( "Parsing version 0.3 not yet implemented." );
		}
		else if ( version.equals( "0.4" ) )
		{
			// This should just work automatically
		}
		else
		{
			JsonElement axes = multiscales.getAsJsonObject().get( "axes" );
			// FIXME
			// - populate Axes[]
			// - populate coordinateTransformations[]
			throw new RuntimeException( "Parsing version " + version + " is not yet implemented." );
		}
	}

	public int getChannelAxisIndex()
	{
		for ( int d = 0; d < numDimensions; d++ )
			if ( axisList.get( d ).getType().equals( Axis.CHANNEL_TYPE ) )
				return d;
		return -1;
	}

	public int getTimePointAxisIndex()
	{
		for ( int d = 0; d < numDimensions; d++ )
			if ( axisList.get( d ).getType().equals( Axis.TIME_TYPE ) )
				return d;
		return -1;
	}

	public int getSpatialAxisIndex( String axisName )
	{
		for ( int d = 0; d < numDimensions; d++ )
			if ( axisList.get( d ).getType().equals( Axis.SPATIAL_TYPE ) && axisList.get( d ).getName().equals( axisName ) )
				return d;
		return -1;
	}

	public List< Axis > getAxes()
	{
		return axisList;
	}

	public CoordinateTransformation[] getCoordinateTransformations()
	{
		return coordinateTransformations;
	}

	public Dataset[] getDatasets()
	{
		return datasets;
	}

	public String getVersion()
	{
		return version;
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public int numDimensions()
	{
		return numDimensions;
	}
}
