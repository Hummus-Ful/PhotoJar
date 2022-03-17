import java.util.HashMap;


public class Unique {

    private HashMap<String, Photo> hashMap;

    public Unique() {this.hashMap = new HashMap<>();}

    public HashMap<String, Photo> getAll() {return this.hashMap;}

    public void add(Photo photo) {
        String hash = photo.getChecksum();
        this.hashMap.put(hash, photo);}

    public boolean isKeyExists(String hash) {return this.hashMap.containsKey(hash);}

    public Photo getPhotoWithKey(String hash) {
        return this.hashMap.get(hash);
    }
}
