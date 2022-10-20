package ome.ngff.transformations;

public class TranslationTransformation extends ParametrizedCoordinateTransformation<TranslationTransformation>
{
	public static final String TYPE = "translation";

	protected double[] translation;

	public TranslationTransformation( final double[] translation )
	{
		this( null, null, null, translation );
	}

	public TranslationTransformation( final String input, final String output, final double[] translation )
	{
		this( null, input, output, translation );
	}

	public TranslationTransformation( final String name, final String input, final String output, final double[] translation )
	{
		super( TYPE, name, input, output, null );
		this.translation = translation;
	}

	public TranslationTransformation( final String input, final String output, final String path )
	{
		this( null, input, output, path );
	}

	public TranslationTransformation( final String name, final String input, final String output, final String path )
	{
		super( TYPE, name, input, output, path );
		translation = null;
	}

	public TranslationTransformation( TranslationTransformation other )
	{
		super( other );
		this.translation = other.translation;
	}

	public double[] getTranslation()
	{
		return translation;
	}
}
