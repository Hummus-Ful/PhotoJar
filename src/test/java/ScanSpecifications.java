import org.junit.Rule;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ScanSpecifications {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldCreateNewObjectUsingConstructor() {
        String path = temporaryFolder.getRoot().getPath();
        Scan scan = new Scan(path);
        assertEquals(Scan.class, scan.getClass());
    }

    @Test
    public void shouldReturnEmptyArrayListOfPhotosIfPathIsHasNoPhotos() throws IOException {
        String path = temporaryFolder.getRoot().getPath();
        Scan scan = new Scan(path);
        ArrayList<Photo> photos = scan.getAllPhotos();
        assertTrue(photos.isEmpty());
    }

    @Test
    public void shouldReturnArrayListOfPhotosWithTwoPhotoObjects() throws IOException {
        File tempFile1 = temporaryFolder.newFile("photo1.png");
        File tempFile2 = temporaryFolder.newFile("photo2.JPEG");
        String path = temporaryFolder.getRoot().getPath();
        Scan scan = new Scan(path);
        ArrayList<Photo> photos = scan.getAllPhotos();
        assertEquals(photos.get(0).getPath(), tempFile2.getPath());
        assertEquals(photos.get(1).getPath(), tempFile1.getPath());
    }

    @Test
    public void shouldNotReturnFilesThatDontHavePhotoExtension() throws IOException {
        File tempFile1 = temporaryFolder.newFile("notPhoto.exe");
        String path = temporaryFolder.getRoot().getPath();
        Scan scan = new Scan(path);
        ArrayList<Photo> photos = scan.getAllPhotos();
        assertTrue(photos.isEmpty());
    }
}
