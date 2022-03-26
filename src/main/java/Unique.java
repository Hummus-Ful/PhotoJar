import dev.brachtendorf.jimagehash.hash.Hash;

import java.util.HashMap;


public class Unique {

    private HashMap<Hash, Photo> hashMap;

    public Unique() {this.hashMap = new HashMap<>();}

    public HashMap<Hash, Photo> getAll() {return this.hashMap;}

    public void add(Photo photo) {
        Hash hash = photo.getChecksum();
        this.hashMap.put(hash, photo);}

    public boolean isKeyExists(Hash hash) {return this.hashMap.containsKey(hash);}

    public Photo getPhotoWithKey(Hash hash) {
        return this.hashMap.get(hash);
    }
}
