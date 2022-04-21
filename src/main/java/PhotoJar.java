import java.util.*;
import java.math.BigInteger;


public class PhotoJar {

    private TreeMap<BigInteger, Photo> jar;
    private ArrayList<Photo> duplicates;

    private HashMap<String, String> similar;

    public PhotoJar() {
        jar = new TreeMap<>();
        duplicates = new ArrayList<>();
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
            if (jar.get(hashValue).getFileSize() != newPhoto.getFileSize()) {
                System.out.println("These two photos has the exact same hash but different file size." +
                        "It is unusual and should be manually validated");
                System.out.println("Photo1: " + jar.get(newPhoto.getHashValue()));
                System.out.println("Photo2: " + newPhoto.getPath());
            }
            else duplicates.add(newPhoto);
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

    public ArrayList<Photo> getDuplicates() {
        return duplicates;
    }

    public HashMap<String, String > getSimilar(double maxDistance) {
        ArrayList<Photo> photos = new ArrayList<>(jar.values());
        for (int iterator = 0; iterator < (photos.size()-1); iterator++) {
            Photo photoA = photos.get(iterator);
            Photo photoB = photos.get(iterator+1);
            double distanceBetweenPhotos = photoA.getHash().normalizedHammingDistance(photoB.getHash());
            if (distanceBetweenPhotos < maxDistance) {
                // ensure the bigger photo is saved as Map key
                if (photoA.getFileSize() > photoB.getFileSize()) similar.put(photoA.getPath(), photoB.getPath());
                else similar.put(photoB.getPath(), photoA.getPath());
            }
        }
        return similar;
    }

    public boolean excludeFromDuplicates(String path) {
        for(int index=0; index<duplicates.size(); index++) {
            if(duplicates.get(index).getPath().equals(path)) {
                duplicates.remove(index);
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