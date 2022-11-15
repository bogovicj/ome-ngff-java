package ome.ngff.transformations;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ByDimensionTransformation extends AbstractCoordinateTransformation< ByDimensionTransformation >
{
	public static final String TYPE = "byDimension";

	protected final CoordinateTransformation[] transformations;

	public ByDimensionTransformation( final String name, final String input, final String output, final CoordinateTransformation[] transformations) {
		super(TYPE, name, input, output );
		this.transformations = transformations;
	}

	public ByDimensionTransformation( final String input, final String output, final CoordinateTransformation[] transformations) {
		this( null, input, output, transformations );
	}

	public ByDimensionTransformation( ByDimensionTransformation other )
	{
		super(other);
		this.transformations = other.transformations; // TODO is shallow copy okay?
	}

	public ByDimensionTransformation( ByDimensionTransformation other, String[] inputAxes, String[] outputAxes )
	{
		super(other, inputAxes, outputAxes );
		this.transformations = other.transformations; // TODO is shallow copy okay?
	}

	public CoordinateTransformation[] getTransformations()
	{
		return transformations;
	}
	
	public static void main( String[] args ) {
		String j = "  {                                                                                                                                                                                                      \n"
				+ "               \"type\" : \"byDimension\",\n"
				+ "               \"transformations\" : [\n"
				+ "                   {\n"
				+ "                       \"type\" : \"affine\",\n"
				+ "                       \"affine\": [1,0,0, 0,1,0],\n"
				+ "                       \"input\" : [ \"i\", \"j\" ],\n"
				+ "                       \"output\" : [ \"i\", \"j\" ]\n"
				+ "                   },\n"
				+ "                   {\n"
				+ "                      \"type\" : \"identity\",\n"
				+ "                      \"input\" : [ \"k\" ],\n"
				+ "                      \"output\" : [ \"k\" ]\n"
				+ "                  }\n"
				+ "              ],\n"
				+ "              \"input\" : \"ijk\",\n"
				+ "              \"output\" : \"ijk2\"\n"
				+ "          }\n";

		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(CoordinateTransformation.class, new CoordinateTransformationAdapter() );
		final Gson gson = gsonBuilder.create();

		ByDimensionTransformation ct = gson.fromJson( j, ByDimensionTransformation.class );
		System.out.println( ct );
		System.out.println( Arrays.toString( ct.getTransformations()[0].getInputAxes() ));
		System.out.println( Arrays.toString( ct.getTransformations()[0].getOutputAxes() ));
				
	}

}
