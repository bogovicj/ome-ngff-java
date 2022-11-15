package ome.ngff.transformations;

import java.lang.reflect.Constructor;
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
		
		String[] inputAxes = null;
		if( jobj.has( "input" ) && jobj.get( "input" ).isJsonArray())
		{
			inputAxes = toStringArray( jobj, "input" );
			jobj.addProperty( "input", "" );
		}

		String[] outputAxes = null;
		if( jobj.has( "output" ) && jobj.get( "output" ).isJsonArray())
		{
			outputAxes = toStringArray( jobj, "output" );
			jobj.addProperty( "output", "" );
		}

		Class clz;
		AbstractCoordinateTransformation< ? > out = null;
		switch( jobj.get("type").getAsString() )
		{

		case(IdentityTransformation.TYPE):
			clz = IdentityTransformation.class;
//			out = context.deserialize( jobj, IdentityTransformation.class );
//			if( inputAxes != null )
//				out = new IdentityTransformation( (IdentityTransformation)out, inputAxes, outputAxes );
			break;
		case(ScaleTransformation.TYPE):
			out = context.deserialize( jobj, ScaleTransformation.class );
			if( inputAxes != null )
				out = new ScaleTransformation( (ScaleTransformation)out, inputAxes, outputAxes );
			break;
		case(TranslationTransformation.TYPE):
			out = context.deserialize( jobj, TranslationTransformation.class );
			if( inputAxes != null )
				out = new TranslationTransformation( (TranslationTransformation)out, inputAxes, outputAxes );
			break;
		case(AffineTransformation.TYPE):
			out = context.deserialize( jobj, AffineTransformation.class );
			if( inputAxes != null )
				out = new AffineTransformation( (AffineTransformation)out, inputAxes, outputAxes );
			break;
		case(DisplacementsTransformation.TYPE):
			out = context.deserialize( jobj, DisplacementsTransformation.class );
			if( inputAxes != null )
				out = new DisplacementsTransformation( (DisplacementsTransformation)out, inputAxes, outputAxes );
			break;
		case(BijectionTransformation.TYPE):
			final JsonObject btmp = context.deserialize( jobj, JsonObject.class );
			final String name = btmp.has( "name" ) ? btmp.get( "name" ).getAsString() : null;
			final String input = btmp.has( "input" ) ? btmp.get( "input" ).getAsString() : null;
			final String output = btmp.has( "output" ) ? btmp.get( "output" ).getAsString() : null;
			if( !btmp.has("forward") && !btmp.has("inverse")) {
				out = null;
			}
			else {
				CoordinateTransformation fwd = context.deserialize( btmp.get("forward"), CoordinateTransformation.class );
				CoordinateTransformation inv = context.deserialize( btmp.get("inverse"), CoordinateTransformation.class );
				out = new BijectionTransformation( name, input, output, fwd, inv );
				if( inputAxes != null )
					out = new BijectionTransformation( (BijectionTransformation)out, inputAxes, outputAxes );
			}
			break;
		case(SequenceTransformation.TYPE):
			// don't like that this is necessary
			// in the future, look into RuntimeTypeAdapterFactory in gson-extras
			// when it is more officially maintained
			final IdentityTransformation id = context.deserialize( jobj, IdentityTransformation.class );
			if( jobj.has("transformations"))
			{
				final JsonArray ja = jobj.get("transformations").getAsJsonArray();
				final CoordinateTransformation[] transforms = new CoordinateTransformation[ ja.size() ];
				for( int i=0; i < ja.size(); i++) {
					JsonElement e = ja.get(i).getAsJsonObject();
					transforms[i] = context.deserialize( e, CoordinateTransformation.class );
				}
				out = new SequenceTransformation(id.getName(), id.getInput(), id.getOutput(), transforms);

				if( inputAxes != null )
					out = new SequenceTransformation( (SequenceTransformation)out, inputAxes, outputAxes );
			}
			else {
				out = null;
			}
			break;
		case(ByDimensionTransformation.TYPE):
			final IdentityTransformation idbd = context.deserialize( jobj, IdentityTransformation.class );
			if( jobj.has("transformations"))
			{
				final JsonArray ja = jobj.get("transformations").getAsJsonArray();
				final CoordinateTransformation[] transforms = new CoordinateTransformation[ ja.size() ];
				for( int i=0; i < ja.size(); i++) {
					JsonElement e = ja.get(i).getAsJsonObject();
					transforms[i] = context.deserialize( e, CoordinateTransformation.class );
				}
				out = new ByDimensionTransformation(idbd.getName(), idbd.getInput(), idbd.getOutput(), transforms);

				if( inputAxes != null )
					out = new ByDimensionTransformation( (ByDimensionTransformation)out, inputAxes, outputAxes );
			}
			break;
		default:
			return null;
		}
		
//		if( inputAxes != null )
//		{
//			try
//			{
//				Constructor cons = clz.getConstructor( clz, String.class, String.class );
//
//			}
//			catch ( NoSuchMethodException e )
//			{
//				e.printStackTrace();
//			}
//			catch ( SecurityException e )
//			{
//				e.printStackTrace();
//			}
//		}


		
		return out;
	}

	@Override
	public JsonElement serialize( CoordinateTransformation src, Type typeOfSrc, JsonSerializationContext context )
	{
		// why do i have to do this!?
		final JsonElement elem;
		if( src instanceof SequenceTransformation )
		{
			SequenceTransformation seq = (SequenceTransformation)src;
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
			typesToClasses.put( AffineTransformation.TYPE, AffineTransformation.class );
			typesToClasses.put( SequenceTransformation.TYPE, SequenceTransformation.class );
			typesToClasses.put( CoordinatesTransformation.TYPE, CoordinatesTransformation.class );
			typesToClasses.put( DisplacementsTransformation.TYPE, DisplacementsTransformation.class );
			typesToClasses.put( BijectionTransformation.TYPE, BijectionTransformation.class );
		}

		return typesToClasses;
	}
	
	private static String[] toStringArray( JsonObject jobj, String key )
	{
		JsonArray arr = jobj.get( key ).getAsJsonArray();
		String[] out = new String[ arr.size() ];
		for( int i = 0; i < arr.size(); i++ )
			out[i] = arr.get( i ).getAsString();
		
		return out;
	}
	
	protected static class AxisPlaceholder extends AbstractCoordinateTransformation< AxisPlaceholder >
	{
		protected String[] input;
		protected String[] output;
		public AxisPlaceholder( String type )
		{
			super( type );
		}
		
		@Override
		public String[] getInputAxes()
		{
			return input;
		}

		@Override
		public String[] getOutputAxes()
		{
			return output;
		}
		
		
	}

}
