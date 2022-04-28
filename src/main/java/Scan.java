import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;


/**
 * Scan for photos in a given folder and all of its sub-folders.
 * The class is design to find all files with given photo related extension.
 *
 * @author Hummus-ful
 */
public class Scan {

    private final String[] ext = {"png", "jpg", "jpeg", "tiff", "raw", "nef",
                                  "PNG", "JPG", "JPEG", "TIFF", "RAW", "NEF"};
    private final String path;
    private final ArrayList<Photo> photos = new ArrayList<>();
    private ArrayList<File> files = new ArrayList<>();

    /**
     * Populate the path attribute and initiate a scan for photos in given folder and all sub-folders.
     * @param path Absolute or relative path of the root folder to be scanned.
     */
    public Scan(String path) {
        this.path = path;
        scanDir();
    }

    /**
     * Run the scan and populate ArrayList with all the photos it found.
     */
    private void scanDir() {
        File rootDir = new File(this.path);
        files = (ArrayList<File>) FileUtils.listFiles(rootDir, ext, true);
        System.out.println("Found total of " + files.size() + " files with relevant extension");
    }

    /**
     * Return list of Photo objects that correlate to the found files.
     * @return ArrayList of all photos found by the scan.
     * @throws IOException If an input or output exception occurred during Photo object creation.
     */
    public ArrayList<Photo> getAllPhotos() throws IOException {
        System.out.println("Converting files to 'Photo' objects and calculating hashes, might take a while...");
        // Convert ArrayList<Files> to ArrayList<Photo>
        for (File file : files) {
            Photo photo = new Photo(file.getPath());
            photos.add(photo);
        }
        return photos;
    }
}
