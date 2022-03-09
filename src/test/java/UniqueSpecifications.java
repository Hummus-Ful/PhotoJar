import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UniqueSpecifications {

    Unique unique;

    @Before
    public void setup() {unique = new Unique();}

    @Test
    public void shouldCreateNewEmptyInstance() {
        int numberOfEntries = unique.getAll().size();
        assertEquals(numberOfEntries, 0);
    }

    @Test
    public void shouldAddNewEntry(){
        String hash = "CryptographicHash";
        Photo photo = new Photo();

        unique.add(hash, photo);
        int numberOfEntries = unique.getAll().size();

        assertEquals(numberOfEntries, 1);
    }

    @Test
    public void shouldReturnHashMapThatIncludesNewEntry() {
        String hash = "CryptographicHash";
        Photo photo = new Photo();

        unique.add(hash, photo);
        HashMap<String, Photo> entries = unique.getAll();
        Collection<String> keys = entries.keySet();
        Collection<Photo> values = entries.values();

        assertTrue(keys.contains(hash));
        assertTrue(values.contains(photo));
    }
}
