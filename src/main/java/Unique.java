import java.util.HashMap;


public class Unique {

    private HashMap<String, Photo> hashMap;

    public Unique() {this.hashMap = new HashMap<>();}

    public HashMap<String, Photo> getAll() {return this.hashMap;}

    public void add(String hash, Photo photo) {this.hashMap.put(hash, photo);}
}
