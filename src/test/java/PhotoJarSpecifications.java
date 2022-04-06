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
    public void shouldAddEntriesUnderTheSameHash() throws IOException {
        Photo samePhoto = new Photo("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        photoJar.add(photo);
        photoJar.add(samePhoto);
        int numberOfEntriesUnderSameKey = photoJar.getPhotosWithHash(hashValue).size();
        assertEquals(2, numberOfEntriesUnderSameKey);
    }

    @Test
    public void shouldAddEntriesUnderDifferentHashes() throws IOException {
        Photo differentPhoto = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        photoJar.add(photo);
        photoJar.add(differentPhoto);
        int numberOfEntriesUnderSameKey = photoJar.getPhotosWithHash(hashValue).size();
        int numberOfTotalEntries = photoJar.getAll().size();
        assertEquals(1, numberOfEntriesUnderSameKey);
        assertEquals(2, numberOfTotalEntries);
    }

    @Test
    public void shouldReturnHashMapWithAllEntries() {
        photoJar.add(photo);
        TreeMap<BigInteger, ArrayList<Photo>> entries = photoJar.getAll();
        Collection<BigInteger> keys = entries.keySet();
        Collection<ArrayList<Photo>> values = entries.values();
        assertTrue(keys.contains(hashValue));
        //TODO: Need to be fixed as I changed from Photo to ArrayList<Photo>
        // assertTrue(values.contains(photo));
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
        Photo returnedPhoto = photoJar.getPhotosWithHash(hashValue).get(0);
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
    public void shouldReturnArrayListWithTwoDuplicatedPhotos() throws IOException {
        Photo anotherPhoto = new Photo("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        photoJar.add(photo);
        photoJar.add(anotherPhoto);
        ArrayList<Photo> expectedArrayListDuplicates = new ArrayList<>();
        expectedArrayListDuplicates.add(photo);
        expectedArrayListDuplicates.add(anotherPhoto);
        ArrayList<Photo> duplicates = photoJar.getDuplicates();
        assertEquals(expectedArrayListDuplicates, duplicates);
    }
}
