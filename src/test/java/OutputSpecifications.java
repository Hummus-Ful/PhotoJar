import dev.brachtendorf.jimagehash.hash.Hash;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.io.IOException;
import java.math.BigInteger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


public class OutputSpecifications {

    private Photo photoA;
    private Photo photoB;
    private Photo photoC;
    private Photo photoD;
    private ArrayList<Photo> photos;
    private String duplicatesFileName = "duplicates.txt";
    private String similarFileName = "similar.html";
    File duplicatesFile;
    File similarFile;

    @Before
    public void setup() throws IOException {
        photoA = new Photo("src/test/resources/photos/Small_Robin_by_Chris-Smith.jpg");
        photoB = new Photo("src/test/resources/photos/Large_Robin_by_Chris-Smith.jpg");
        photoC = new Photo("src/test/resources/photos/Small_Robin_by_abdul-rehman-khalid.jpg");
        photoD = new Photo("src/test/resources/photos/Large_Robin_by_abdul-rehman-khalid.jpg");
        photos = new ArrayList<>();
        photos.add(photoA);
        photos.add(photoB);
        photos.add(photoC);
        duplicatesFile = new File(duplicatesFileName);
        similarFile = new File(similarFileName);
    }

    @After
    public void teardown() {
        duplicatesFile.delete();
        similarFile.delete();
    }

    @Test
    public void shouldOutputArrayListToNewPlainTxtFile() throws Exception{
        List<String> paths = new ArrayList<>();
        for (Photo photo : photos) {
            paths.add(photo.getPath());
        }
        Output.toPlainText(photos, duplicatesFileName);
        List<String> lines = Files.readAllLines(duplicatesFile.toPath());
        assertEquals(paths, lines);
    }

    @Test
    public void shouldAppendOutputArrayListToExistingPlainTxtFile() throws Exception{
        Output.toPlainText(photos, duplicatesFileName);
        ArrayList<Photo> additionalPhotos = new ArrayList<>();
        additionalPhotos.add(photoD);
        List<String> paths = new ArrayList<>();
        for (Photo photo : photos) {
            paths.add(photo.getPath());
        }
        paths.add(photoD.getPath());
        Output.toPlainText(additionalPhotos, duplicatesFileName);
        List<String> lines = Files.readAllLines(duplicatesFile.toPath());
        assertEquals(paths, lines);
    }

    @Test
    public void shouldOutputHashMapToHtmlFile() {
        HashMap<String, String> photosHashMap = new HashMap<>();
        photosHashMap.put(photoA.getPath(), photoB.getPath());
        photosHashMap.put(photoC.getPath(), photoD.getPath());
        Output.toHtml(photosHashMap, similarFileName);
        File ref = new File("src/test/resources/reference.html");
        assertEquals(ref.getTotalSpace(), similarFile.getTotalSpace());  //TODO
    }

}

//TODO:
// 4. Should handle ArrayList or Map objects as input, should be transparent to the caller