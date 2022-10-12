package ome.ngff;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import ome.ngff.axes.CoordinateSystem;
import ome.ngff.transformations.CoordinateTransformation;
import ome.ngff.transformations.CoordinateTransformationAdapter;
import ome.ngff.transformations.CoordinatesTransformation;
import ome.ngff.transformations.DisplacementsTransformation;
import ome.ngff.transformations.ParametrizedCoordinateTransformation;

public class CoordinateTransformationsParseTest
{
	@Test
	public void testParsing()
	{
		final HashMap< String, Class< ? > > typesToClasses = CoordinateTransformationAdapter.getTypesToClasses();
		final Set< String > validTypes = CoordinateTransformationAdapter.getTypesToClasses().keySet();

		final URL zattrsUrl = CoordinateTransformationsParseTest.class.getResource( "cts.zarr/coordinateTransformations/.zattrs" );
		String attrs = null;
		try
		{
			attrs = Files.readString( Paths.get( zattrsUrl.toURI() ));
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

		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(CoordinateTransformation.class, new CoordinateTransformationAdapter() );
		final Gson gson = gsonBuilder.create();

		// get the first multiscales object
		final JsonElement js = gson.fromJson( attrs, JsonElement.class );
		final JsonElement csElem = js.getAsJsonObject().get("coordinateSystems");
		final CoordinateSystem[] css = gson.fromJson( csElem, CoordinateSystem[].class );

		for( CoordinateSystem cs : css )
		{
			assertNotNull( cs.name );
			assertNotNull( cs.axes );
			assertTrue( cs.axes.length > 0 );
		}

		final JsonElement ctElem = js.getAsJsonObject().get("coordinateTransformations");
		final CoordinateTransformation[] cts = gson.fromJson( ctElem, CoordinateTransformation[].class );

		for( CoordinateTransformation ct : cts )
		{
			assertTrue( validTypes.contains( ct.getType() ));

			// check that object is the correct type
			assertTrue( typesToClasses.get( ct.getType() ).isInstance( ct ));

			if( ct.getType().equals( DisplacementsTransformation.TYPE ) || ct.getType().equals( CoordinatesTransformation.TYPE ))
			{
				ParametrizedCoordinateTransformation pct = (ParametrizedCoordinateTransformation)ct;
				assertNotNull( pct.getPath() );
			}
		}
	}

}
