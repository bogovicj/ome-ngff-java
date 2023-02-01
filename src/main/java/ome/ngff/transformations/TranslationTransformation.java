package ome.ngff.transformations;

public class TranslationTransformation extends ParametrizedCoordinateTransformation< TranslationTransformation >
{
	public static final String TYPE = "translation";

	protected double[] translation;

	public TranslationTransformation( final double[] translation )
	{
		this( "", translation );
	}

	public TranslationTransformation( final String name, final double[] translation )
	{
		super( TYPE, name, null );
		this.translation = translation;
	}

	public TranslationTransformation( final String name, final String path )
	{
		super( TYPE, name, path );
		translation = null;
	}

	public TranslationTransformation( TranslationTransformation other, final double[] translation )
	{
		super( other );
		this.translation = translation;
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
