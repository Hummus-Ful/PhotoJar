import java.io.StringWriter;
import java.io.Writer;
import java.io.FileWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


public class Main {

    private static final String logFileName = "photojar.log";
    private static Logger logger;
    private static String[] dirs;
    private static ArrayList<Photo> photos;
    private static PhotoJar photoJar;
    private static HashMap<String, String> similar;


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

    private static void populatePhotoJar() {
        for (Photo photo: photos) {
            photoJar.add(photo);
        }
    }

    private static void logAllDuplicates() {
        ArrayList<Photo> duplicates = photoJar.getDuplicates();
        for (Photo photo: duplicates)
            logger.info(photo.getPath());
            //logger.info("Duplicate: " + photo.getHashValue() + ", Path: " + photo.getPath());
    }

    private static void logAllSimilar(double maxDistance) {
        similar = photoJar.getSimilar(maxDistance);
        for (Map.Entry<String, String > entry: similar.entrySet()) {
            logger.info(entry.getValue());
            //logger.info("Similar: " + entry.getKey() + " -> " + entry.getValue());
        }
    }

    private static void createHtmlOutput() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("runtime.log.logsystem.class", NullLogChute.class.getName());
        velocityEngine.init();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("similar", similar);

        Template template = velocityEngine.getTemplate("index.vm");

        try {
            Writer writer = new FileWriter("index.html");
            template.merge(velocityContext, writer);
            writer.flush();
            writer.close();
        }
        catch (Exception e) {
            //TODO
        }
    }

    public static void main(String[] args) {
        setLogger();
        checkArgs(args);
        System.out.println("Starting a scan");
        photoJar = new PhotoJar();  //setup only once for the whole app
        for (String dir : dirs) {
            getAllPhotos(dir);
            populatePhotoJar();
        }
        logAllDuplicates();
        double prePopulatedMaxDistance = 0.05;
        logAllSimilar(prePopulatedMaxDistance);
        createHtmlOutput();
        System.out.println("Done!");
    }
}

