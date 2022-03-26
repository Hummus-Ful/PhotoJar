import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;


public class DuplicateSpecifications {

    private Duplicate duplicate;
    private File tempfile;
    private String path;
    private Photo photo;

    @Before
    public void setup() throws IOException {
        duplicate = new Duplicate();
        tempfile = new File("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        path = tempfile.getPath();
        photo = new Photo(path);
    }

    @Test
    public void shouldCreateNewEmptyObject() {
        int numberOfEntries = duplicate.getAll().size();
        assertEquals(numberOfEntries, 0);
    }

    @Test
    public void shouldAddNewEntry() {
        duplicate.add(photo);
        int numberOfEntries = duplicate.getAll().size();
        assertEquals(numberOfEntries, 1);
    }

    @Test
    public void shouldReturnStoredEntries() {
        duplicate.add(photo);
        ArrayList<Photo> arrayList = duplicate.getAll();
        Photo storedPhoto = arrayList.get(0);
        assertEquals(photo, storedPhoto);
    }
}
