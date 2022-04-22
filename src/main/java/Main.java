import java.nio.file.Path;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class Main {

    private static final String logFileName = "photojar.log";
    private static Logger logger;
    private static String[] dirs;
    private static ArrayList<Photo> photos;
    private static PhotoJar photoJar;
    private static HashMap<Photo, Photo> similar;
    private static HashMap<Photo, Photo> duplicates;


    private static void setLogger() {
        logger = Logger.getLogger(PhotoJar.class.getName());
        FileHandler handler;
        try {
            handler = new FileHandler(logFileName);
            handler.setFormatter(new SimpleFormatter());
            logger.setUseParentHandlers(false);  // disable outputting log to console
            logger.addHandler(handler);
        }
        catch (Exception e) {
            //TODO
        }
    }

    private static void checkArgs(String[] args) {
        if (args.length < 1) throw new IllegalArgumentException();
        dirs = args;
    }

    private static void getAllPhotos(String dir) {
        Scan scan = new Scan(dir);
        try {
            photos = scan.getAllPhotos();
            logger.info("FOUND TOTAL OF " + photos.size() + " PHOTOS IN ROOT DIR " + dir);
        }
        catch (Exception e) {
        // TODO
        }
    }

    private static void outputToFiles() {
        Output.toHtml(similar, "similar.html");
        Output.toHtml(duplicates, "duplicates.html");
        Output.toPlainText(similar, "similar.log");
        Output.toPlainText(duplicates, "duplicates.log");
    }

    public static void main(String[] args) {
        setLogger();
        checkArgs(args);
        System.out.println("Starting a scan");
        photoJar = new PhotoJar();
        for (String dir : dirs) {
            String absolutePath = Path.of(dir).toAbsolutePath().normalize().toString();
            getAllPhotos(absolutePath);
            photoJar.add(photos);
        }
        double prePopulatedMaxDistance = 0.05;
        duplicates = photoJar.getDuplicates();
        similar = photoJar.getSimilar(prePopulatedMaxDistance);
        outputToFiles();
        System.out.println("Done!");
    }
}
