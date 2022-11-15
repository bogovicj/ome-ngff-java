package ome.ngff;

import static org.junit.Assert.assertArrayEquals;
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

import ome.ngff.axes.Axis;
import ome.ngff.axes.CoordinateSystem;
import ome.ngff.axes.Unit;
import ome.ngff.transformations.ByDimensionTransformation;
import ome.ngff.transformations.CoordinateTransformation;
import ome.ngff.transformations.CoordinateTransformationAdapter;
import ome.ngff.transformations.CoordinatesTransformation;
import ome.ngff.transformations.DisplacementsTransformation;
import ome.ngff.transformations.ParametrizedCoordinateTransformation;
import ome.ngff.transformations.ScaleTransformation;
import ome.ngff.transformations.SequenceTransformation;
import ome.ngff.transformations.TranslationTransformation;

public class CoordinateTransformationsParseTest
{
	@Test
	public void testByDimension()
	{
		final URL zattrsUrl = CoordinateTransformationsParseTest.class.getResource( "cts.zarr/byDimension.json" );
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

		final JsonElement js = gson.fromJson( attrs, JsonElement.class );

		final JsonElement csElem = js.getAsJsonObject().get("coordinateSystems");
		final CoordinateSystem[] css = gson.fromJson( csElem, CoordinateSystem[].class );

		final JsonElement ctElem = js.getAsJsonObject().get("coordinateTransformations");
		final CoordinateTransformation[] cts = gson.fromJson( ctElem, CoordinateTransformation[].class );
		final ByDimensionTransformation ct = ( ByDimensionTransformation ) cts[0];
		CoordinateTransformation[] tforms = ct.getTransformations();

		final String[] inAxes1 = tforms[0].getInputAxes();
		final String[] inAxes2 = tforms[1].getInputAxes();
		assertArrayEquals( new String[]{"i", "j"}, inAxes1 );
		assertArrayEquals( new String[]{"k"}, inAxes2 );

		final String[] outAxes1 = tforms[0].getOutputAxes();
		final String[] outAxes2 = tforms[1].getOutputAxes();
		assertArrayEquals( new String[]{"i", "j"}, outAxes1 );
		assertArrayEquals( new String[]{"k"}, outAxes2 );
	}

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
			assertNotNull( cs.getName() );
			assertNotNull( cs.getAxes() );
			assertTrue( cs.getAxes().length > 0 );
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

	@Test
	public void writeTest()
	{
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(CoordinateTransformation.class, new CoordinateTransformationAdapter() );
		final Gson gson = gsonBuilder.create();

		CoordinateSystem csIn = new CoordinateSystem( "in",
				new Axis[] {
						new Axis( "x", Axis.SPATIAL_TYPE, Unit.MICROMETER_UNIT ),
						new Axis( "y", Axis.SPATIAL_TYPE, Unit.MICROMETER_UNIT )
				});

		CoordinateSystem csOut = new CoordinateSystem( "out",
				new Axis[] {
						new Axis( "x", Axis.SPATIAL_TYPE, Unit.MICROMETER_UNIT ),
						new Axis( "y", Axis.SPATIAL_TYPE, Unit.MICROMETER_UNIT )
				});

		SequenceTransformation seq = new SequenceTransformation( "in", "out",
				new CoordinateTransformation[] {
						new ScaleTransformation( new double[] {2,3} ),
						new TranslationTransformation( new double[] {-10,100 } ),
				});

		CoordinateSystem[] coordSystems = new CoordinateSystem[] { csIn, csOut };
//		System.out.println( gson.toJson( coordSystems ));

		CoordinateTransformation[] coordTransformations = new CoordinateTransformation[] { seq };
//		System.out.println( gson.toJson( coordTransformations ));

		// TODO automatically validate the output
	}

}
