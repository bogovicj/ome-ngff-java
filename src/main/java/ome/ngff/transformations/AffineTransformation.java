package ome.ngff.transformations;

public class AffineTransformation extends ParametrizedCoordinateTransformation<AffineTransformation>
{
	public static final String TYPE = "affine";
	
	private double[] affine;

	public AffineTransformation( final double[] affine )
	{
		this( null, null, null, affine );
	}

	public AffineTransformation( final String input, final String output, final double[] affine )
	{
		this( null, input, output, affine );
	}

	public AffineTransformation( final String name, final String input, final String output, final double[] affine )
	{
		super( TYPE, name, input, output, null );
		this.affine = affine;
	}

	public AffineTransformation( final String input, final String output, final String path )
	{
		this( null, input, output, path );
	}

	public AffineTransformation( final String name, final String input, final String output, final String path )
	{
		super( TYPE, name, input, output, path );
		affine = null;
	}

	public AffineTransformation( AffineTransformation other, final double[] affine )
	{
		super( other );
		this.affine = affine;
	}

	public AffineTransformation( AffineTransformation other )
	{
		super( other );
		this.affine = other.affine;
	}

	public AffineTransformation( AffineTransformation other, String[] inputAxes, String[] outputAxes  )
	{
		super( other, inputAxes, outputAxes );
		this.affine = other.affine;
	}

	public double[] getAffine()
	{
		return affine;
	}

}
