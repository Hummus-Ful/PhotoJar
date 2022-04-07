import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
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
        hashValue = new BigInteger("12979489063543234784");
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
    public void shouldAddEntriesUnderDifferentHashes() throws IOException {
        Photo differentPhoto = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        photoJar.add(photo);
        photoJar.add(differentPhoto);
        int numberOfTotalEntries = photoJar.getAll().size();
        assertEquals(2, numberOfTotalEntries);
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
        Photo returnedPhoto = photoJar.getPhotoWithHash(hashValue);
        assertEquals(photo, returnedPhoto);
    }

    @Test
    public void shouldReturnEmptyArrayListIfNoDuplicatesWereFound() throws IOException {
        Photo anotherPhoto = new Photo("src/test/resources/photos/Large_Robin_by_abdul-rehman-khalid.jpg");
        photoJar.add(photo);
        photoJar.add(anotherPhoto);
        int numberOfDuplicates = photoJar.getDuplicates().size();
        assertEquals(0, numberOfDuplicates);
    }

    @Test
    public void shouldReturnArrayListWithOneDuplicatedPhoto() throws IOException {
        Photo anotherPhoto = new Photo("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        photoJar.add(photo);
        photoJar.add(anotherPhoto);
        int numberOfDuplicates = photoJar.getDuplicates().size();
        assertEquals(1, numberOfDuplicates);
    }

    //TODO:
    // Implement getSimilar method
    // Implement deleteDuplicatesAndSimilar (should I split this? MUST keep the order to delete dups first)
    //
}
