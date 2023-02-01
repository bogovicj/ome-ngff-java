package ome.ngff;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
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

import ome.ngff.labels.ImageLabel;
import ome.ngff.labels.ImageLabelColors;
import ome.ngff.labels.ImageLabelColors.ImageLabelColorAdapter;
import ome.ngff.labels.ImageLabelProperties;
import ome.ngff.labels.ImageLabelProperties.ImageLabelPropertiesAdapter;

public class ImageLabelTest
{
	
	@Test
	public void testLabelColor()
	{
		final URL imglabel = ImageLabelTest.class.getResource( "image-label.json" );
		String attrs = null;
		try
		{
			attrs = new String( Files.readAllBytes( Paths.get( imglabel.toURI() )));
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

		final Gson gson = new GsonBuilder()
				.registerTypeAdapter(ImageLabelColors.class, new ImageLabelColorAdapter() )
				.registerTypeAdapter(ImageLabelProperties.class, new ImageLabelPropertiesAdapter() )
				.create();

		final JsonObject jobj = gson.fromJson( attrs, JsonObject.class );
		final ImageLabel imageLabel = gson.fromJson( jobj.get( "image-label" ), ImageLabel.class );

		final int[] color1 = new int[] { 255, 255, 255, 255 };
		final int[] color4 = new int[] { 0, 255, 255, 128 };
		assertArrayEquals( color1, imageLabel.getColor( 1 ) );
		assertArrayEquals( color4, imageLabel.getColor( 4 ) );
		assertEquals( "foo", imageLabel.getProperty( 1, "class", String.class ));

		assertEquals( 1200, imageLabel.getProperty( 1, "area (pixels)", Integer.class ).intValue());
		assertEquals( 1650, imageLabel.getProperty( 4, "area (pixels)", Integer.class ).intValue());

	}

}
