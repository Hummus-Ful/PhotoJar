import java.util.*;
import java.math.BigInteger;


public class PhotoJar {

    private TreeMap<BigInteger, Photo> jar;
    private HashMap<Photo, Photo> duplicates;

    private HashMap<Photo, Photo> similar;

    public PhotoJar() {
        jar = new TreeMap<>();
        duplicates = new HashMap<>();
        similar = new HashMap<>();
    }

    public TreeMap<BigInteger, Photo> getAllPhotos() {
        return jar;
    }

    public int size() {
        return jar.size();
    }

    public void add(ArrayList<Photo> photos) {
        for (Photo photo: photos) {
            this.add(photo);
        }
    }

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