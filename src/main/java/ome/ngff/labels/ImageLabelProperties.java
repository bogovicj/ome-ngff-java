package ome.ngff.labels;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ImageLabelProperties
{
	private final long imageLabel;

	private final Map< String, Object > properties;

	private transient final JsonObject parsedProperties;
	
	private transient Gson gson;

	private JsonDeserializationContext dCtxt;

	public ImageLabelProperties( final Gson gson, long imageLabel, JsonObject parsedProperties )
	{
		this.gson = gson;
		this.imageLabel = imageLabel;
		this.parsedProperties = parsedProperties;
		this.properties = null;
	}

	public ImageLabelProperties( final GsonBuilder gsonBuilder, long imageLabel, JsonObject parsedProperties )
	{
		this( gsonBuilder.create(), imageLabel, parsedProperties );
	}

	public long getImageLabel()
	{
		return imageLabel;
	}

	public JsonObject getParsedProperties()
	{
		return parsedProperties;
	}

	public < T > T get( String key, Class< T > clazz )
	{
		return gson.fromJson( parsedProperties.get( key ), clazz );
	}

	public static class ImageLabelPropertiesAdapter implements JsonDeserializer< ImageLabelProperties >, JsonSerializer< ImageLabelProperties >
	{

		public JsonElement serialize( ImageLabelProperties src, Type typeOfSrc, JsonSerializationContext context )
		{
			final JsonObject out = context.serialize( src.getParsedProperties() ).getAsJsonObject();
			out.addProperty( "image-label", src.getImageLabel() );
			out.remove( "imageLabel" );
			return out;
		}

		public ImageLabelProperties deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException
		{
			final long imageLabel = json.getAsJsonObject().get( "label-value" ).getAsLong();
			final JsonObject props = context.deserialize( json, JsonObject.class );
			return new ImageLabelProperties( new Gson(), imageLabel, props );
		}

	}
}
