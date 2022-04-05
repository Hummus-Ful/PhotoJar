import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import dev.brachtendorf.jimagehash.hash.Hash;


public class PhotoSpecifications {

    private File tempFile;
    private String path;
    private Hash hash;
    private Photo photo;

    @Before
    public void setup() throws IOException {
        tempFile = new File("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        path = tempFile.getPath();
        hash = new Hash(new BigInteger("52023094704"), 36, 89877222);
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
        Hash returnedChecksum = photo.getHash();
        assertEquals(hash, returnedChecksum);
    }

    @Test
    public void shouldReturnPhotoPath() {
        String returnedPath = photo.getPath();
        assertEquals(path, returnedPath);
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionForNonExistingFile() throws IOException {
        String path = "/this/file/does/not/exists.png";
        Photo photoDoesNotExists = new Photo(path);
    }
}
