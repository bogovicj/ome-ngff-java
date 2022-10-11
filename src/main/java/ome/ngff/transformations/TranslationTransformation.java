package ome.ngff.transformations;

public class TranslationTransformation extends CoordinateTransformation
{
	public static final String TYPE = "translation";

	protected final double[] translation;

	public TranslationTransformation( final double[] translation )
	{
		this( "", null, null, translation );
	}

	public TranslationTransformation( final String input, final String output, final double[] translation )
	{
		this( "", input, output, translation );
	}

	public TranslationTransformation( final String name, final String input, final String output, final double[] translation )
	{
		super( TYPE, name, input, output );
		this.translation = translation;
	}

	public double[] getTranslation()
	{
		return translation;
	}
}
