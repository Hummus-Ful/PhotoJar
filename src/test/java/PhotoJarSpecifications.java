import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.math.BigInteger;
import java.util.TreeMap;
import java.io.IOException;
import java.util.Collection;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


public class PhotoJarSpecifications {

    private PhotoJar photoJar;
    private File tempFile;
    private String path;
    private BigInteger hashValue;
    private Photo photo;

    @Before
    public void setup() throws IOException {
        photoJar = new PhotoJar();
        tempFile = new File("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        path = tempFile.getPath();
        hashValue = new BigInteger("52023094704");
        photo = new Photo(path);
    }

    @Test
    public void shouldCreateNewEmptyObject() {
        assertTrue(photoJar.getAll().isEmpty());
    }

    @Test
    public void shouldAddNewEntry() {
        photoJar.add(photo);
        int numberOfEntries = photoJar.getAll().size();
        assertEquals(1, numberOfEntries);
    }

    @Test
    public void shouldReturnHashMapWithAllEntries() {
        photoJar.add(photo);
        TreeMap<BigInteger, Photo> entries = photoJar.getAll();
        Collection<BigInteger> keys = entries.keySet();
        Collection<Photo> values = entries.values();
        assertTrue(keys.contains(hashValue));
        assertTrue(values.contains(photo));
    }

    @Test
    public void shouldReturnTrueIfEntryAlreadyExists() {
        photoJar.add(photo);
        assertTrue(photoJar.isKeyExists(hashValue));
    }

    @Test
    public void shouldReturnFalseIfEntryDoesNotExists() {
        BigInteger dummyHashValue = new BigInteger("0");
        assertFalse(photoJar.isKeyExists(dummyHashValue));
    }

    @Test
    public void shouldReturnPhotoObjectForGivenHash() {
        photoJar.add(photo);
        Photo returnedPhoto = photoJar.getPhotoWithKey(hashValue);
        assertEquals(photo, returnedPhoto);
    }
}
