import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ScanSpecifications {

    private String directoryWithPhotos;
    private String directoryWithNoPhotos;

    @Before
    public void setup() {
        directoryWithPhotos = "src/test/resources/photos";
        directoryWithNoPhotos = "src/test/resources/directoryWithNoPhotos";
    }

    @Test
    public void shouldCreateNewObjectUsingConstructor() {
        Scan scan = new Scan(directoryWithPhotos);
        assertEquals(Scan.class, scan.getClass());
    }

    @Test
    public void shouldReturnArrayListOfPhotosWithTwoPhotoObjects() throws IOException {
        Scan scan = new Scan(directoryWithPhotos);
        int numberOfFiles = new File(directoryWithPhotos).list().length;
        ArrayList<Photo> photos = scan.getAllPhotos();
        assertEquals(numberOfFiles, photos.size());
    }

    @Test
    public void shouldNotReturnFilesThatDontHavePhotoExtension() throws IOException {
        Scan scan = new Scan(directoryWithNoPhotos);
        ArrayList<Photo> photos = scan.getAllPhotos();
        assertTrue(photos.isEmpty());
    }
}
