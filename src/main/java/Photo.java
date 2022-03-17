import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.digest.DigestUtils;


public class Photo {

    private String path;
    private String hash;

    private void setPath(String path) {
        this.path = path;
    }

    private void setHash() throws IOException {
        File file = new File(this.path);
        String calculatedHash = DigestUtils.md5Hex(FileUtils.readFileToByteArray(file));
        this.hash = calculatedHash;
    }

    public Photo() {
        throw new IllegalArgumentException();
    }

    public Photo(String path) throws IOException {
        setPath(path);
        setHash();
    }

    public String getChecksum() {
        return this.hash;
    }

    public String getPath() {
        return this.path;
    }

}
