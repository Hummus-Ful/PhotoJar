import java.util.ArrayList;


public class Duplicate {

    ArrayList<Photo> arrayList;

    public Duplicate() {
        this.arrayList = new ArrayList<>();
    }

    public ArrayList<Photo> getAll() {
        return this.arrayList;
    }

    public void add(Photo photo) {
        this.arrayList.add(photo);
    }
}
