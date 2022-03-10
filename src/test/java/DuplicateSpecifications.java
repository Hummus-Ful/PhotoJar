import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;


public class DuplicateSpecifications {

    Duplicate duplicate;

    @Before
    public void setup() {duplicate = new Duplicate();}

    @Test
    public void shouldCreateNewEmptyInstance() {
        int numberOfEntries = duplicate.getAll().size();
        assertEquals(numberOfEntries, 0);
    }

    @Test
    public void shouldAddNewEntry(){
        Photo photo = new Photo();
        duplicate.add(photo);
        int numberOfEntries = duplicate.getAll().size();
        assertEquals(numberOfEntries, 1);
    }

    @Test
    public void shouldReturnValuesOfStoredEntries() {
        Photo photo = new Photo();
        Photo testPhoto = new Photo();
        duplicate.add(photo);
        ArrayList<Photo> arrayList = duplicate.getAll();
        Photo storedPhoto = arrayList.get(0);
        assertEquals(photo, storedPhoto);
    }
}
