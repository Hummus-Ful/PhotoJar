import java.util.TreeMap;
import java.math.BigInteger;


public class PhotoJar {

    private TreeMap<BigInteger, Photo> treeMap;

    public PhotoJar() {this.treeMap = new TreeMap<>();}

    public TreeMap<BigInteger, Photo> getAll() {return this.treeMap;}

    public void add(Photo photo) {
        BigInteger hashValue = photo.getHashValue();
        this.treeMap.put(hashValue, photo);}

    public boolean isKeyExists(BigInteger hashValue) {return this.treeMap.containsKey(hashValue);}

    public Photo getPhotoWithKey(BigInteger hashValue) {
        return this.treeMap.get(hashValue);
    }
}
