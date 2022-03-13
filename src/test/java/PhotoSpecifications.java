import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.io.IOException;
import static org.mockito.Mockito.*;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;


public class PhotoSpecifications {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfNoPathIsProvidedToConstructor() {
        Photo photo = new Photo();
    }

    @Test
    public void shouldCreateNewPhotoObjectUsingConstructor() throws IOException {
        File tempFile = temporaryFolder.newFile("photo.png");
        String path = tempFile.getPath();
        Photo photo = new Photo(path);
        assertEquals(photo.getClass(), Photo.class);
    }

    @Test
    public void shouldReturnCalculatedPhotoHash() throws IOException {
        File tempFile = temporaryFolder.newFile("photo.png");
        String path = tempFile.getPath();
        String hash = "d41d8cd98f00b204e9800998ecf8427e";
        Photo photo = new Photo(path);
        String returnedHash = photo.getHash();
        assertEquals(hash, returnedHash);
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionForNonExistingFile() throws IOException{
        String path = "/this/file/does/not/exists.png";
        Photo photo = new Photo(path);
    }
}
