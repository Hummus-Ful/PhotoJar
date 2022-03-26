import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.io.IOException;
import java.util.Collection;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import dev.brachtendorf.jimagehash.hash.Hash;


public class UniqueSpecifications {

    private Unique unique;
    private File tempFile;
    private String path;
    private Hash checksum;
    private Photo photo;

    @Before
    public void setup() throws IOException {
        unique = new Unique();
        tempFile = new File("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        path = tempFile.getPath();
        checksum = new Hash(new BigInteger("52023094704"), 36, 89877222);
        photo = new Photo(path);
    }

    @Test
    public void shouldCreateNewEmptyObject() {
        assertTrue(unique.getAll().isEmpty());
    }

    @Test
    public void shouldAddNewEntry() {
        unique.add(photo);
        int numberOfEntries = unique.getAll().size();
        assertEquals(1, numberOfEntries);
    }

    @Test
    public void shouldReturnHashMapWithAllEntries() {
        unique.add(photo);
        HashMap<Hash, Photo> entries = unique.getAll();
        Collection<Hash> keys = entries.keySet();
        Collection<Photo> values = entries.values();
        assertTrue(keys.contains(checksum));
        assertTrue(values.contains(photo));
    }

    @Test
    public void shouldReturnTrueIfEntryAlreadyExists() {
        unique.add(photo);
        assertTrue(unique.isKeyExists(checksum));
    }

    @Test
    public void shouldReturnFalseIfEntryDoesNotExists() {
        Hash checksumDummy = new Hash(new BigInteger("0"), 0, 0);
        assertFalse(unique.isKeyExists(checksumDummy));
    }

    @Test
    public void shouldReturnPhotoObjectForGivenHash() {
        unique.add(photo);
        Photo returnedPhoto = unique.getPhotoWithKey(checksum);
        assertEquals(photo, returnedPhoto);
    }
}
