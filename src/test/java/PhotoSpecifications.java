import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.io.IOException;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;


public class PhotoSpecifications {

    File tempFile;
    String path;
    String checksum;
    Photo photo;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setup() throws IOException {
        tempFile = temporaryFolder.newFile("photo.png");
        path = tempFile.getPath();
        checksum = "d41d8cd98f00b204e9800998ecf8427e";
        photo = new Photo(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfNoPathIsProvidedToConstructor() {
        Photo photo = new Photo();
    }

    @Test
    public void shouldCreateNewPhotoObjectUsingConstructor() {
        assertEquals(photo.getClass(), Photo.class);
    }

    @Test
    public void shouldReturnCalculatedPhotoHash() {
        String returnedChecksum = photo.getChecksum();
        assertEquals(checksum, returnedChecksum);
    }

    @Test
    public void shouldReturnPhotoPath() {
        String returnedPath = photo.getPath();
        assertEquals(path, returnedPath);
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionForNonExistingFile() throws IOException {
        String path = "/this/file/does/not/exists.png";
        Photo photo = new Photo(path);
    }
}
