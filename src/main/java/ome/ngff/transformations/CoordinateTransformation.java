package ome.ngff.transformations;

public interface CoordinateTransformation
{
	public final static String KEY = "coordinateTransformations";

	public String getName();

	public String getType();

}
