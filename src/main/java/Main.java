import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class Main {

    private static String logFileName = "photojar.log";

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(PhotoJar.class.getName());
        FileHandler handler;
        try {
            handler = new FileHandler(logFileName);
            handler.setFormatter(new SimpleFormatter());
            logger.setUseParentHandlers(false);  // disable console output
            logger.addHandler(handler);
        }
        catch (Exception e) {}

        if (args.length != 1) throw new IllegalArgumentException();
        String rootDir = args[0];

        System.out.println("Starting a scan");
        Scan scan = new Scan(rootDir);
        try {
            ArrayList<Photo> photos = scan.getAllPhotos();
            logger.info("FOUND TOTAL OF " + photos.size() + " PHOTOS");
            Duplicate duplicate = new Duplicate();
            Unique unique = new Unique();

            for (Photo photo : photos) {
                BigInteger hashValue = photo.getHashValue();
                if (unique.isKeyExists(hashValue)) {
                    String originalPhotoPath = unique.getPhotoWithKey(hashValue).getPath() ;
                    String duplicatePhotoPath = photo.getPath();
                    logger.info("Duplicated hash: " + hashValue +
                            " Original: " + originalPhotoPath +
                            " Duplicate: " + duplicatePhotoPath);
                    duplicate.add(photo);
                }
                else unique.add(photo);
            }

            int numberOfDuplicates = duplicate.getAll().size();
            logger.info("FOUND TOTAL OF " + numberOfDuplicates + " DUPLICATES");
            System.out.println("Found total of " + numberOfDuplicates + " duplicates");
            System.out.println("List of originals and duplicates is stored in '" + logFileName + "'");
            System.out.println("Done!");

            //TODO: move all dups to a seperated folder
        }
        catch (Exception e) {}
    }

}
