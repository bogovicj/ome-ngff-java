package ome.ngff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import ome.ngff.transformations.CoordinateTransformationAdapter;
import ome.ngff.transformations.CoordinateTransformation;

public class MultiscalesParseTest
{

	/**
	 * Run passing the path to a .zattrs containing a valid v0.4 ome-ngff multiscales object
	 * as the first argument.
	 *  
	 * @param args arguments
	 * @throws IOException
	 */
	public static void main( String[] args ) throws IOException
	{
		final String attrs = Files.readString(  Paths.get( args[0] ));
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(CoordinateTransformation.class, new CoordinateTransformationAdapter() );
		final Gson gson = gsonBuilder.create();

		// get the first multiscales object
		final JsonElement js = gson.fromJson( attrs, JsonElement.class );
		final JsonElement msarr = js.getAsJsonObject().get("multiscales");
		final JsonElement msobj = msarr.getAsJsonArray().get( 0 );

		MultiscalesV04 ms = gson.fromJson( msobj, MultiscalesV04.class );
		ms.init();
		System.out.println( "parsed multiscales with name: " + ms.getName() );
	}

}
