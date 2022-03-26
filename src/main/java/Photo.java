import java.io.File;
import java.io.IOException;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.digest.DigestUtils;


public class Photo {

    private String path;
    private Hash hash;
    HashingAlgorithm hashingAlgorithm;
    int keyLength = 32;

    private void setPath(String path) {
        this.path = path;
    }

    private void setHash() throws IOException {
        File file = new File(this.path);
        //String calculatedHash = DigestUtils.md5Hex(FileUtils.readFileToByteArray(file));
        hashingAlgorithm = new AverageHash(keyLength);
        Hash calculatedHash = hashingAlgorithm.hash(file);
        this.hash = calculatedHash;
    }

    public Photo() {
        throw new IllegalArgumentException();
    }

    public Photo(String path) throws IOException {
        setPath(path);
        setHash();
    }

    public Hash getChecksum() {
        return this.hash;
    }

    public String getPath() {
        return this.path;
    }

}
