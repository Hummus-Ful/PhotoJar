import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class PhotoJar {

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
                String hash = photo.getHash();
                if (unique.isExists(hash)) {
                    String originalPhotoPath = unique.getPhoto(hash).getPath() ;
                    String duplicatePhotoPath = photo.getPath();
                    logger.info("Duplicated hash:" + hash +
                            " Original: " + originalPhotoPath +
                            " Duplicate: " + duplicatePhotoPath);
                    duplicate.add(photo);
                }
                else unique.add(photo);
            }

            ArrayList<Photo> duplicatedPhotos = duplicate.getAll();
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

