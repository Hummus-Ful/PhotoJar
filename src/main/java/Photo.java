import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;


public class Photo {

    private String path;
    private Hash hash;
    private BigInteger hashValue;
    HashingAlgorithm hashingAlgorithm;
    int keyLength = 64;

    private void setPath(String path) {
        this.path = path;
    }

    private void setHash() throws IOException {
        File file = new File(this.path);
        hashingAlgorithm = new AverageHash(keyLength);
        Hash calculatedHash = hashingAlgorithm.hash(file);
        this.hash = calculatedHash;
    }

    private void setHashValue() {
        this.hashValue = hash.getHashValue();
    }

    public Photo() {
        throw new IllegalArgumentException();
    }

    public Photo(String path) throws IOException {
        setPath(path);
        setHash();
        setHashValue();
    }

    public Hash getHash() {
        return this.hash;
    }

    public BigInteger getHashValue() {
        return this.hashValue;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        return path;
    }
}
