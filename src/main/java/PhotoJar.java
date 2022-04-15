import dev.brachtendorf.jimagehash.hash.Hash;

import java.util.*;
import java.math.BigInteger;


public class PhotoJar {

    private TreeMap<BigInteger, Photo> treeMap;
    private ArrayList<Photo> duplicates;

    public PhotoJar() {
        treeMap = new TreeMap<>();
        duplicates = new ArrayList<>();
    }

    public TreeMap<BigInteger, Photo> getAll() {
        return treeMap;
    }

    public void add(Photo newPhoto) {
        BigInteger hashValue = newPhoto.getHashValue();
        if (isKeyExists(hashValue)) {
            if (treeMap.get(hashValue).getFileSize() != newPhoto.getFileSize()) {
                System.out.println("Something is wrong, same hash calculated for different photos");
                System.exit(1);
            }
            duplicates.add(newPhoto);
        }
        else {
            treeMap.put(hashValue, newPhoto);
        }
    }

    public boolean isKeyExists(BigInteger hashValue) {
        return treeMap.containsKey(hashValue);
    }

    public Photo getPhotoWithHash(BigInteger hashValue) {
        return treeMap.get(hashValue);
    }

    public ArrayList<Photo> getDuplicates() {
        return duplicates;
    }

    public HashMap<String, String > getSimilar(double maxDistance) {
        ArrayList<Photo> photos = new ArrayList<>(treeMap.values());
        HashMap<String, String> similar = new HashMap<>();
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
}

//TODO:
// split the getSimilar method into smaller private methods