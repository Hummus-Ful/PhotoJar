import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
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
}
