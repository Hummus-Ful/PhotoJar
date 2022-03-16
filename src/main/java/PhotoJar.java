import java.io.IOException;
import java.util.ArrayList;

public class PhotoJar {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) throw new IllegalArgumentException();
        String rootDir = args[0];

        Scan scan = new Scan(rootDir);
        ArrayList<Photo> photos = scan.getAllPhotos();

        Duplicate duplicate = new Duplicate();
        Unique unique = new Unique();

        for (Photo photo : photos) {
            if (unique.isExists(photo.getHash())) {
                System.out.println("Duplicated hash:" + photo.getHash());
                duplicate.add(photo);
            }
            else unique.add(photo);
        }

        ArrayList<Photo> duplicatedPhotos = duplicate.getAll();
        //TODO: move all dups to a seperated folder
    }

}
    //TODO:
    /*
    1. Create new Scan object called scan
    2. scan.getAllPhotos() -> return ArrayList<Photo> into photos object
    3. Create new Dup and Unique objects
    4. for each object in photos:
        a. if(unique.isExists(photo)) -> insert into dup
        b. else insert into unique
    5. Move all dups into recycleBin folder
    */

