import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;


public class DuplicateSpecifications {

    Duplicate duplicate;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setup() {duplicate = new Duplicate();}

    @Test
    public void shouldCreateNewEmptyObject() {
        int numberOfEntries = duplicate.getAll().size();
        assertEquals(numberOfEntries, 0);
    }

    @Test
    public void shouldAddNewEntry() throws IOException {
        File tempFile = temporaryFolder.newFile("photo.png");
        String path = tempFile.getPath();
        Photo photo = new Photo(path);
        duplicate.add(photo);
        int numberOfEntries = duplicate.getAll().size();
        assertEquals(numberOfEntries, 1);
    }

    @Test
    public void shouldReturnStoredEntries() throws IOException {
        File tempFile = temporaryFolder.newFile("photo.png");
        String path = tempFile.getPath();
        String hash = "d41d8cd98f00b204e9800998ecf8427e";
        Photo photo = new Photo(path);
        duplicate.add(photo);
        ArrayList<Photo> arrayList = duplicate.getAll();
        Photo storedPhoto = arrayList.get(0);
        assertEquals(photo, storedPhoto);
    }
}
