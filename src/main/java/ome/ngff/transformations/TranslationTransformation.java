package ome.ngff.transformations;

public class TranslationTransformation extends CoordinateTransformation
{
	public static final String TYPE = "translation";

	protected final double[] translation;

	public TranslationTransformation( final double[] translation )
	{
		this( "", translation );
	}

	public TranslationTransformation( String name, final double[] translation )
	{
		super( TYPE, name );
		this.translation = translation;
	}

	public double[] getTranslation()
	{
		return translation;
	}
}
