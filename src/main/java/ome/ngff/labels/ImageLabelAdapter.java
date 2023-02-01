package ome.ngff.labels;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class ImageLabelAdapter implements JsonDeserializer<ImageLabel>, JsonSerializer<ImageLabel>
{

	public JsonElement serialize( ImageLabel src, Type typeOfSrc, JsonSerializationContext context )
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ImageLabel deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
