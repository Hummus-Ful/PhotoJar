import org.junit.Test;
import org.junit.Before;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import dev.brachtendorf.jimagehash.hash.Hash;


public class PhotoSpecifications {

    private String path;
    private Hash hash;
    private Photo photo;

    @Before
    public void setup() throws IOException {
        path = "src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg";
        // Using hardcoded checksum as it takes 50% less time instead of recalculating it.
        hash = new Hash(new BigInteger("3255819928745283004853448844850788827905"), 128, 90031943);
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
        String expectedAbsolutePath = Path.of(path).toAbsolutePath().normalize().toString();
        assertEquals(expectedAbsolutePath, returnedPath);
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionForNonExistingFile() throws IOException {
        String path = "/this/file/does/not/exists.png";
        Photo photoDoesNotExists = new Photo(path);
    }

    @Test
    public void shouldReturnFileSizeInBytes() {
        long fileSize = photo.getFileSize();
        assertEquals(86950, fileSize);
    }
}
