import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class PhotoJar {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(PhotoJar.class.getName());
        FileHandler handler;
        try {
            handler = new FileHandler("photojar.log");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        }
        catch (Exception e) {}

        if (args.length != 1) throw new IllegalArgumentException();
        String rootDir = args[0];

        Scan scan = new Scan(rootDir);
        try {
            ArrayList<Photo> photos = scan.getAllPhotos();
            Duplicate duplicate = new Duplicate();
            Unique unique = new Unique();

            for (Photo photo : photos) {
                String checksum = photo.getChecksum();
                if (unique.isKeyExists(checksum)) {
                    String originalPhotoPath = unique.getPhotoWithKey(checksum).getPath() ;
                    String duplicatePhotoPath = photo.getPath();
                    logger.info("Duplicated checksum: " + checksum +
                            " Original: " + ANSI_GREEN + originalPhotoPath + ANSI_RESET +
                            " Duplicate: " + ANSI_RED + duplicatePhotoPath + ANSI_RESET);
                    duplicate.add(photo);
                }
                else unique.add(photo);
            }

            int numberOfDuplicates = duplicate.getAll().size();
            logger.info("FOUND TOTAL OF " + numberOfDuplicates + " DUPLICATES");
            //ArrayList<Photo> duplicatedPhotos = duplicate.getAll();
            //TODO: move all dups to a seperated folder
        }
        catch (Exception e) {}
    }

}
    //TODO:
    /*
    1. Move all dups into recycleBin folder
    2. Handle exception
    */

