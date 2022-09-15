package spimdata;

import java.io.IOException;

import ome.ngff.ome.zarr.openers.OMEZarrS3Opener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import mpicbg.spim.data.SpimData;

@Slf4j
public class OmeZarrMultiScaleV4SpimDataTest {
    public static final String FILE_KEY = "https://s3.embl.de/i2k-2020/ngff-example-data/v0.4/multi-image.ome.zarr";
    public static final int MULTISCALES_SIZE = 4;


    @Test
    public void multiScalesSize() {
        try {
            OMEZarrS3Opener.setLogging(true);
            SpimData spimData = OMEZarrS3Opener.readURL(FILE_KEY);
            int multiscalesSize = spimData.getViewRegistrations().getViewRegistrations().size();
            Assertions.assertEquals(MULTISCALES_SIZE, multiscalesSize);
        } catch (IOException e) {
            Assertions.fail("SpimData loading error: " + e.getMessage());
        }
    }
}
