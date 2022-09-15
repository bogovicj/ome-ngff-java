package ui;

import java.io.IOException;

import ome.ngff.ome.zarr.openers.OMEZarrOpener;

import bdv.util.BdvFunctions;
import mpicbg.spim.data.SpimData;

public class OmeZarrV4FSOpener {
    public static void main(String[] args) throws IOException {
        showV4();
    }

    public static void showV4() throws IOException {
        SpimData image = OMEZarrOpener.openFile("g/kreshuk/pape/Work/mobie/ngff/ome-ngff-prototypes/single_image/v0.4/tcyx.ome.zarr");
        BdvFunctions.show(image);
    }
}
