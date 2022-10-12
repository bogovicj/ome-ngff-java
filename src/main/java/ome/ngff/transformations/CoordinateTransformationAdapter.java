package ome.ngff.transformations;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class CoordinateTransformationAdapter implements JsonDeserializer<CoordinateTransformation> {

	@Override
	public CoordinateTransformation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		if( !json.isJsonObject() )
			return null;
		
		final JsonObject jobj = json.getAsJsonObject();
		if( !jobj.has("type") )
			return null;

		final String type = jobj.get("type").getAsString();
		return context.deserialize( jobj, getTypesToClasses().get( type ) );
	}

	// singleton
	public static HashMap< String, Class<?> > typesToClasses;

	public static HashMap<String,Class<?>> getTypesToClasses()
	{
		if( typesToClasses == null )
		{
			typesToClasses = new HashMap<>();
			typesToClasses.put( IdentityTransformation.TYPE, IdentityTransformation.class );
			typesToClasses.put( ScaleTransformation.TYPE, ScaleTransformation.class );
			typesToClasses.put( TranslationTransformation.TYPE, TranslationTransformation.class );
			typesToClasses.put( SequenceCoordinateTransform.TYPE, SequenceCoordinateTransform.class );
			typesToClasses.put( CoordinatesTransformation.TYPE, CoordinatesTransformation.class );
			typesToClasses.put( DisplacementsTransformation.TYPE, DisplacementsTransformation.class );
		}

		return typesToClasses;
	}

}
