import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class Main {

    private static final String logFileName = "photojar.log";
    private static Logger logger;
    private static String rootDir;
    private static ArrayList<Photo> photos;
    private static PhotoJar photoJar;


    private static void setLogger() {
        logger = Logger.getLogger(PhotoJar.class.getName());
        FileHandler handler;
        try {
            handler = new FileHandler(logFileName);
            handler.setFormatter(new SimpleFormatter());
            logger.setUseParentHandlers(false);  // disable console output
            logger.addHandler(handler);
        }
        catch (Exception e) {}
    }

    private static void checkArgs(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();
        rootDir = args[0];
    }

    private static void getAllPhotos() {
        Scan scan = new Scan(rootDir);
        try {
            photos = scan.getAllPhotos();
            logger.info("FOUND TOTAL OF " + photos.size() + " PHOTOS");
        }
        catch (Exception e) {
        // TODO
        }
    }

    private static void populatePhotoJar() {
        photoJar = new PhotoJar();
        for (Photo photo: photos) {
            photoJar.add(photo);
        }
    }

    private static void logAllDuplicates() {
        ArrayList<Photo> duplicates = photoJar.getDuplicates();
        for (Photo photo: duplicates)
            logger.info("Duplicate: " + photo.getHashValue() + ", Path: " + photo.getPath());
            //logger.info("Potential Duplicate: " + photo.getHashValue() + ", Path: file://" + photo.getPath());
    }

    private static void logAllSimilar(double maxDistance) {
        HashMap<String, String> similar = photoJar.getSimilar(maxDistance);
        for (Map.Entry<String, String > entry: similar.entrySet()) {
            logger.info("Similar: " + entry.getKey() + " -> " + entry.getValue());
            //logger.info("Similar: file://" + entry.getKey() + " -> file://" + entry.getValue());
        }
    }

    public static void main(String[] args) {

        setLogger();
        checkArgs(args);
        System.out.println("Starting a scan");
        getAllPhotos();
        populatePhotoJar();
        logAllDuplicates();
        double prePopulatedMaxDistance = 0.05;
        logAllSimilar(prePopulatedMaxDistance);
        System.out.println("Done!");
    }
}

