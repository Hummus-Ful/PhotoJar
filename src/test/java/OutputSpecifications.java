import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.io.IOException;
import static org.junit.Assert.assertEquals;


public class OutputSpecifications {

    private Photo photoA;
    private Photo photoB;
    private Photo photoC;
    private Photo photoD;
    private HashMap<Photo, Photo> photos;
    private String duplicatesFileName = "duplicates.log";
    private String similarFileName = "similar.html";
    File duplicatesFile;
    File similarFile;

    @Before
    public void setup() throws IOException {
        photoA = new Photo("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        photoB = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        photoC = new Photo("src/test/resources/photos/Small_Robin_by_abdul-rehman-khalid.jpg");
        photoD = new Photo("src/test/resources/photos/Large_Robin_by_abdul-rehman-khalid.jpg");
        photos = new HashMap<>();
        photos.put(photoA, photoB);
        photos.put(photoC, photoD);
        duplicatesFile = new File(duplicatesFileName);
        similarFile = new File(similarFileName);
    }

    @After
    public void teardown() {
        duplicatesFile.delete();
        similarFile.delete();
    }

    @Test
    public void shouldOutputEntriesToNewPlainTxtFile() throws IOException {
        List<String> paths = new ArrayList<>();
        for (Photo photo : photos.keySet()) {
            paths.add(photo.getPath());
        }
        Output.toPlainText(photos, duplicatesFileName);
        List<String> lines = Files.readAllLines(duplicatesFile.toPath());
        assertEquals(paths, lines);
    }

    @Test
    public void shouldAppendNewEntriesToExistingPlainTxtFile() throws IOException {
        Output.toPlainText(photos, duplicatesFileName);
        HashMap<Photo, Photo> additionalPhotos = new HashMap<>();
        additionalPhotos.put(photoB, photoA);
        List<String> paths = new ArrayList<>();
        for (Photo photo : photos.keySet()) {
            paths.add(photo.getPath());
        }
        paths.add(photoB.getPath());
        Output.toPlainText(additionalPhotos, duplicatesFileName);
        List<String> lines = Files.readAllLines(duplicatesFile.toPath());
        assertEquals(paths, lines);
    }

    @Test
    public void shouldOutputHashMapToHtmlFile() {
        HashMap<Photo, Photo> photosHashMap = new HashMap<>();
        photosHashMap.put(photoA, photoB);
        photosHashMap.put(photoC, photoD);
        Output.toHtml(photosHashMap, similarFileName);
        File ref = new File("src/test/resources/reference.html");
        assertEquals(ref.getTotalSpace(), similarFile.getTotalSpace());  //TODO
    }
}

//TODO:
// 4. Should handle ArrayList or Map objects as input, should be transparent to the caller
