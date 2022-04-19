import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;


public class Photo {

    private File file;
    private String path;
    private Hash hash;
    private long fileSize;
    private BigInteger hashValue;
    private HashingAlgorithm hashingAlgorithm;
    private int keyLength = 128;

    private void setPath(String path) {
        this.path = Path.of(path).toAbsolutePath().normalize().toString();
    }

    private void setHash() throws IOException {
        hashingAlgorithm = new AverageHash(keyLength);
        Hash calculatedHash = hashingAlgorithm.hash(file);
        this.hash = calculatedHash;
    }

    private void setHashValue() {
        this.hashValue = hash.getHashValue();
    }

    private void setFileSize() {
        fileSize = file.length();
    }

    public Photo() {
        throw new IllegalArgumentException();
    }

    public Photo(String path) throws IOException {
        setPath(path);
        file = new File(this.path);
        setHash();
        setHashValue();
        setFileSize();
    }

    public Hash getHash() {
        return hash;
    }

    public BigInteger getHashValue() {
        return hashValue;
    }

    public String getPath() {
        return path;
    }

    public long getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return path;
    }
}
