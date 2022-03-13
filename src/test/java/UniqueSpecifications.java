import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Collection;
import static org.mockito.Mockito.*;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


public class UniqueSpecifications {

    Unique unique;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setup() {unique = new Unique();}

    @Test
    public void shouldCreateNewEmptyObject() {
        int numberOfEntries = unique.getAll().size();
        assertEquals(numberOfEntries, 0);
    }

    @Test
    public void shouldAddNewEntry() throws IOException {
        File tempFile = temporaryFolder.newFile("photo.png");
        String path = tempFile.getPath();
        Photo photo = new Photo(path);
        unique.add(photo);
        int numberOfEntries = unique.getAll().size();
        assertEquals(numberOfEntries, 1);
    }

    @Test
    public void shouldReturnHashMapWithAllEntries() throws IOException {
        File tempFile = temporaryFolder.newFile("photo.png");
        String path = tempFile.getPath();
        String hash = "d41d8cd98f00b204e9800998ecf8427e";
        Photo photo = new Photo(path);
        unique.add(photo);
        HashMap<String, Photo> entries = unique.getAll();
        Collection<String> keys = entries.keySet();
        Collection<Photo> values = entries.values();
        assertTrue(keys.contains(hash));
        assertTrue(values.contains(photo));
    }

    @Test
    public void shouldReturnTrueIfEntryAlreadyExists() throws IOException {
        File tempFile = temporaryFolder.newFile("photo.png");
        String path = tempFile.getPath();
        String hash = "d41d8cd98f00b204e9800998ecf8427e";
        Photo photo = new Photo(path);
        unique.add(photo);
        assertTrue(unique.isExists(hash));
    }

    @Test
    public void shouldReturnFalseIfEntryDoesNotExists() {
        String hash = "ThisHashDoesNotExistsInUnique";
        assertFalse(unique.isExists(hash));
    }
}
