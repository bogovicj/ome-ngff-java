package ome.ngff;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import ome.ngff.multiscales.MultiscalesV04;
import ome.ngff.transformations.CoordinateTransformation;
import ome.ngff.transformations.CoordinateTransformationAdapter;

public class MultiscalesTest
{
	@Test
	public void testMultiscales()
	{
		final URL zattrs = MultiscalesTest.class.getResource( "zyx.ome.zarr/.zattrs" );
		String attrs = null;
		try
		{
			attrs = new String( Files.readAllBytes( Paths.get( zattrs.toURI() ) ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			fail();
		}
		catch ( URISyntaxException e )
		{
			e.printStackTrace();
			fail();
		}

		final Gson gson = new GsonBuilder().registerTypeAdapter( CoordinateTransformation.class, new CoordinateTransformationAdapter() ).create();
		final JsonObject jobj = gson.fromJson( attrs, JsonObject.class );

		final MultiscalesV04[] msArray = gson.fromJson( jobj.get( "multiscales" ), MultiscalesV04[].class );
		final MultiscalesV04 ms = msArray[ 0 ];
		ms.init();

		assertNotNull( ms );
		assertArrayEquals( new String[] { "x", "y", "z" }, ms.getAxes().stream().map( x -> x.getName() ).toArray( String[]::new ) );
		assertArrayEquals( new String[] { "space", "space", "space" }, ms.getAxes().stream().map( x -> x.getType() ).toArray( String[]::new ) );
		assertArrayEquals( new String[] { "nanometer", "nanometer", "nanometer" }, ms.getAxes().stream().map( x -> x.getUnit() ).toArray( String[]::new ) );

	}

}
