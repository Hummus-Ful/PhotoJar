import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;


public class Scan {

    private final String[] ext = {"png", "jpg", "jpeg", "tiff", "raw", "nef",
                                  "PNG", "JPG", "JPEG", "TIFF", "RAW", "NEF"};
    private final String path;
    private final ArrayList<Photo> photos = new ArrayList<>();
    private ArrayList<File> files = new ArrayList<>();

    private void scanDir() {
        File rootDir = new File(this.path);
        files = (ArrayList<File>) FileUtils.listFiles(rootDir, ext, true);
        System.out.println("Found total of " + files.size() + " files with relevant extension");
    }

    public Scan(String path) {
        this.path = path;
        scanDir();
    }

    public ArrayList<Photo> getAllPhotos() throws IOException {
        System.out.println("Converting files to Photo objects and calculating hashes");
        // Convert ArrayList<Files> to ArrayList<Photo>
        for (File file : files) {
            Photo photo = new Photo(file.getPath());
            photos.add(photo);
        }
        return photos;
    }
}
