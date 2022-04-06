import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.math.BigInteger;


public class PhotoJar {

    private TreeMap<BigInteger, ArrayList<Photo>> treeMap;

    public PhotoJar() {treeMap = new TreeMap<>();}

    public TreeMap<BigInteger, ArrayList<Photo>> getAll() {return treeMap;}

    public void add(Photo newPhoto) {
        BigInteger hashValue = newPhoto.getHashValue();
        ArrayList<Photo> photos;
        if (isKeyExists(hashValue)) {
            photos = treeMap.get(hashValue);
        }
        else {
            photos = new ArrayList<>();
        }
        photos.add(newPhoto);
        treeMap.put(hashValue, photos);
    }

    public boolean isKeyExists(BigInteger hashValue) {return treeMap.containsKey(hashValue);}

    public ArrayList<Photo> getPhotosWithHash(BigInteger hashValue) {
        return treeMap.get(hashValue);
    }

    public ArrayList<Photo> getDuplicates() {
        ArrayList<Photo> duplicates = new ArrayList<>();
        for (Map.Entry<BigInteger, ArrayList<Photo>> entry: treeMap.entrySet()) {
            ArrayList<Photo> photos = entry.getValue();
            if (photos.size() > 1) {
                duplicates.addAll(photos);
            }
        }
        return duplicates;
    }
}
