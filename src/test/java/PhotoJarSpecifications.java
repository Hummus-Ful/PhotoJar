import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


public class PhotoJarSpecifications {

    private PhotoJar photoJar;
    private String path;
    private BigInteger hashValue;
    private Photo photo;

    @Before
    public void setup() throws IOException {
        photoJar = new PhotoJar();
        path = "src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg";
        hashValue = new BigInteger("3255819928745283004853448844850788827905");
        photo = new Photo(path);
    }

    @Test
    public void shouldCreateNewEmptyObject() {
        int photoJarSize = photoJar.size();
        assertEquals(0, photoJarSize);
    }

    @Test
    public void shouldAddNewEntry() {
        photoJar.add(photo);
        int numberOfEntries = photoJar.size();
        assertEquals(1, numberOfEntries);
    }

    @Test
    public void shouldAddEntriesUnderDifferentHashes() throws IOException {
        Photo differentPhoto = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        photoJar.add(photo);
        photoJar.add(differentPhoto);
        int numberOfTotalEntries = photoJar.size();
        assertEquals(2, numberOfTotalEntries);
    }

    @Test
    public void shouldReturnHashMapWithAllEntries() {
        photoJar.add(photo);
        TreeMap<BigInteger, Photo> entries = photoJar.getAllPhotos();
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

    @Test
    public void shouldReturnHashMapOfVerySimilarPhotos() throws IOException {
        Photo similarPhoto = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        Photo robinPhoto = new Photo("src/test/resources/photos/Small_Robin_by_abdul-rehman-khalid.jpg");
        Photo similarRobinPhoto = new Photo("src/test/resources/photos/Large_Robin_by_abdul-rehman-khalid.jpg");
        photoJar.add(photo);
        photoJar.add(similarPhoto);
        photoJar.add(robinPhoto);
        photoJar.add(similarRobinPhoto);
        double similarityMaxDistance = 0.05;
        HashMap<Photo, Photo> similarMap = photoJar.getSimilar(similarityMaxDistance);
        int numberOfSimilarPairs = similarMap.size();
        assertEquals(2, numberOfSimilarPairs);
    }

    @Test
    public void shouldReturnBiggerPhotoAsMapKeyAndSmallerAsValue() throws IOException {
        Photo biggerPhoto = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        photoJar.add(photo);
        photoJar.add(biggerPhoto);
        double similarityMaxDistance = 0.05;
        HashMap<Photo, Photo> similarMap = photoJar.getSimilar(similarityMaxDistance);
        boolean biggerPhotoIsTheKey = similarMap.containsKey(biggerPhoto);
        assertTrue(biggerPhotoIsTheKey);
    }

    @Test
    public void shouldPopulatePhotoJarGivenArrayListOfPhotos() throws IOException {
        Photo anotherPhoto = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(photo);
        photos.add(anotherPhoto);
        photoJar.add(photos);
        int expectedNumberOfPhotos = 2;
        int numberOfPhotos = photoJar.size();
        assertEquals(expectedNumberOfPhotos, numberOfPhotos);
    }

    @Test
    public void shouldReturnZeroAsPhotoJarIsEmpty() {
        int size = photoJar.size();
        assertEquals(0, size);
    }

    @Test
    public void shouldRemovePhotoFromDuplicatesList() {
        String path = Path.of(this.path).toAbsolutePath().normalize().toString();
        photoJar.add(photo);
        photoJar.add(photo);
        int sizeBeforeExclusion = photoJar.getDuplicates().size();
        boolean trueIfSuccessful = photoJar.excludeFromDuplicates(path);
        int sizeAfterExclusion = photoJar.getDuplicates().size();
        assertTrue(trueIfSuccessful);
        assertEquals(sizeAfterExclusion, sizeBeforeExclusion-1);
    }

    @Test
    public void shouldRemovePhotoFromSimilarList() throws IOException {
        String path = Path.of(this.path).toAbsolutePath().normalize().toString();
        Photo similarLargerPhoto = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        photoJar.add(photo);
        photoJar.add(similarLargerPhoto);
        int sizeBeforeExclusion = photoJar.size();
        photoJar.getSimilar(0.5);
        boolean trueIfSuccessful = photoJar.excludeFromSimilar(path);
        int sizeAfterExclusion = photoJar.size();
        assertTrue(trueIfSuccessful);
        assertEquals(sizeAfterExclusion, sizeBeforeExclusion-1);
    }
}
