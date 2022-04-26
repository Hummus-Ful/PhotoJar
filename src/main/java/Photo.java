import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Path;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;

import javax.imageio.ImageIO;


public class Photo implements Serializable {

    private File file;
    private String path;
    private Hash hash;
    private long fileSize;
    private int width;
    private int height;
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

    private void setPhotoDimensions() throws IOException{
        BufferedImage bufferedImage = ImageIO.read(file);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
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
        setPhotoDimensions();
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return path;
    }
}
