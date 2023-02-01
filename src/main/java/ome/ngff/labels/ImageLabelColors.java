package ome.ngff.labels;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ImageLabelColors
{
	private final long imageLabel;

	private final int[] rgba;

	public ImageLabelColors( long imageLabel, int[] rgba )
	{
		this.imageLabel = imageLabel;
		this.rgba = rgba;
	}

	public long getImageLabel()
	{
		return imageLabel;
	}

	public int[] getRgba()
	{
		return rgba;
	}

	public static class ImageLabelColorAdapter implements JsonDeserializer< ImageLabelColors >, JsonSerializer< ImageLabelColors >
	{

		public JsonElement serialize( ImageLabelColors src, Type typeOfSrc, JsonSerializationContext context )
		{
			final JsonObject out = new JsonObject();
			out.addProperty( "image-label", src.getImageLabel() );
			out.add( "rgba", context.serialize( src.getRgba() ) );
			out.remove( "imageLabel" );
			return out;
		}

		public ImageLabelColors deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException
		{
			final long imageLabel = json.getAsJsonObject().get( "label-value" ).getAsLong();
			final int[] rgba = context.deserialize( json.getAsJsonObject().get( "rgba" ), int[].class );
			return new ImageLabelColors( imageLabel, rgba );
		}

	}
}
