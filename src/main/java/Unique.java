import dev.brachtendorf.jimagehash.hash.Hash;

import java.math.BigInteger;
import java.util.TreeMap;


public class Unique {

    private TreeMap<BigInteger, Photo> treeMap;

    public Unique() {this.treeMap = new TreeMap<>();}

    public TreeMap<BigInteger, Photo> getAll() {return this.treeMap;}

    public void add(Photo photo) {
        BigInteger hashValue = photo.getHashValue();
        this.treeMap.put(hashValue, photo);}

    public boolean isKeyExists(BigInteger hashValue) {return this.treeMap.containsKey(hashValue);}

    public Photo getPhotoWithKey(BigInteger hashValue) {
        return this.treeMap.get(hashValue);
    }
}
