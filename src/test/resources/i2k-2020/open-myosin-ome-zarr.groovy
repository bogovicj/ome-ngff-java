import bdv.util.BdvFunctions
import ome.ngff.ome.zarr.openers.OMEZarrS3Opener

//N5OMEZarrImageLoader.debugLogging = true;
reader = new OMEZarrS3Opener("https://s3.embl.de", "us-west-2", "i2k-2020");
myosin = reader.readKey("prospr-myosin.ome.zarr");
BdvFunctions.show(myosin);