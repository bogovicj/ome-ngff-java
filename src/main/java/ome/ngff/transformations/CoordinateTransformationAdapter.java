package ome.ngff.transformations;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class CoordinateTransformationAdapter implements JsonDeserializer<CoordinateTransformation>, JsonSerializer<CoordinateTransformation>
{

	public static final String[] FIELDS_TO_NULL_CHECK = new String[]{ "path", "name" };

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

	@Override
	public JsonElement serialize( CoordinateTransformation src, Type typeOfSrc, JsonSerializationContext context )
	{
		// why do i have to do this!?
		final JsonElement elem;
		if( src instanceof SequenceCoordinateTransform )
		{
			SequenceCoordinateTransform seq = (SequenceCoordinateTransform)src;
			JsonArray xfms = new JsonArray();
			for( CoordinateTransformation t : seq.getTransformations() ) {
				Type type = TypeToken.get(t.getClass()).getType();
				xfms.add(serialize(t, type, context ));
			}
			JsonObject obj =  (JsonObject) context.serialize(src);
			obj.add("transformations", xfms);
			elem = obj;
		}
		else
		{
			elem =  context.serialize(src);
		}

		if( elem instanceof JsonObject )
		{
			final JsonObject obj = (JsonObject)elem;
			for( String f : FIELDS_TO_NULL_CHECK )
			if( obj.has(f) && obj.get(f).isJsonNull())
			{
				obj.remove(f);
			}
		}
		return elem;

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
