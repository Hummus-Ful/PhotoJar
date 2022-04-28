import java.util.*;
import java.math.BigInteger;
import java.io.Serializable;


/**
 * As the name suggests, simple Jar of photos where you can search for duplicates or similar ones and get
 * all the relevant data for each (path, size on disk, dimensions)
 *
 * @author Hummus-ful
 */
public class PhotoJar implements Serializable {

    private TreeMap<BigInteger, Photo> jar;
    private HashMap<Photo, Photo> duplicates;
    private HashMap<Photo, Photo> similar;

    /**
     * Create new jar, duplicates and similar objects
     */
    public PhotoJar() {
        jar = new TreeMap<>();
        duplicates = new HashMap<>();
        similar = new HashMap<>();
    }

    /**
     * Return map of all photos in jar.
     * @return TreeMap of all photos in jar.
     */
    public TreeMap<BigInteger, Photo> getAllPhotos() {
        return jar;
    }

    /**
     * Returns the number of photos in jar.
     * @return number of photos in jar.
     */
    public int size() {
        return jar.size();
    }

    /**
     * Add multiple new photos to jar.
     * @param photos Photos to be added.
     */
    public void add(ArrayList<Photo> photos) {
        for (Photo photo: photos) {
            this.add(photo);
        }
    }

    /**
     * Add single new photo to the jar.
     * @param newPhoto The photo to be added.
     */
    public void add(Photo newPhoto) {
        BigInteger hashValue = newPhoto.getHashValue();
        if (isKeyExists(hashValue)) {
            Photo existingPhoto = jar.get(hashValue);
            if (existingPhoto.getFileSize() != newPhoto.getFileSize()) {
                System.out.println("These two photos have the exact same hash but different file size. " +
                        "It is unusual and should be manually validated");
                System.out.println("Photo1: " + existingPhoto.getPath());
                System.out.println("Photo2: " + newPhoto.getPath());
            }
            else duplicates.put(newPhoto, existingPhoto);  //in this order as key must be unique
        }
        else {
            jar.put(hashValue, newPhoto);
        }
    }

    /**
     * Test if given hashValue is found in the jar.
     * @param hashValue Photo hash value.
     * @return True if hashValue exists in the jar.
     */
    public boolean isKeyExists(BigInteger hashValue) {
        return jar.containsKey(hashValue);
    }

    public Photo getPhotoWithHash(BigInteger hashValue) {
        return jar.get(hashValue);
    }

    public HashMap<Photo, Photo> getDuplicates() {
        return duplicates;
    }

    public HashMap<Photo, Photo > getSimilar(double maxDistance) {
        ArrayList<Photo> photos = new ArrayList<>(jar.values());
        for (int iterator = 0; iterator < (photos.size()-1); iterator++) {
            Photo photoA = photos.get(iterator);
            Photo photoB = photos.get(iterator+1);
            double distanceBetweenPhotos = photoA.getHash().normalizedHammingDistance(photoB.getHash());
            if (distanceBetweenPhotos < maxDistance) {
                // key = small photo, value = bigger photo
                if (photoA.getFileSize() > photoB.getFileSize()) similar.put(photoB, photoA);
                else similar.put(photoA, photoB);
            }
        }
        return similar;
    }

    public boolean excludeFromDuplicates(String path) {
        for(Photo photo : duplicates.keySet()) {
            if(photo.getPath().equals(path)){
                duplicates.remove(photo);
                return true;
            }
        }
        return false;
    }

    public boolean excludeFromSimilar(String path) {
        for (Photo photo : jar.values()) {
            if(path.equals(photo.getPath())) {
                jar.remove(photo.getHashValue());
                return true;
            }
        }
        return false;
    }

}

//TODO:
// split the getSimilar method into smaller private methods