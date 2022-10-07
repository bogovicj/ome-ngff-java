package ome.ngff.transformations;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class CoordinateTransformationAdapter
	implements JsonDeserializer<CoordinateTransformation> {

	@Override
	public CoordinateTransformation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		if( !json.isJsonObject() )
			return null;
		
		final JsonObject jobj = json.getAsJsonObject();
		if( !jobj.has("type") )
			return null;

		CoordinateTransformation out = null;
		switch( jobj.get("type").getAsString() )
		{
		case("identity"):
			out = context.deserialize( jobj, IdentityTransformation.class );
			break;
		case("scale"):
			out = context.deserialize( jobj, ScaleTransformation.class );
			break;
		case("translation"):
			out = context.deserialize( jobj, TranslationTransformation.class );
			break;
		}

		return out;
	}

}
