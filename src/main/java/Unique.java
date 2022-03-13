import java.util.HashMap;


public class Unique {

    private HashMap<String, Photo> hashMap;

    public Unique() {this.hashMap = new HashMap<>();}

    public HashMap<String, Photo> getAll() {return this.hashMap;}

    public void add(Photo photo) {
        String hash = photo.getHash();
        this.hashMap.put(hash, photo);}

    public boolean isExists(String hash) {return this.hashMap.containsKey(hash);}
}
