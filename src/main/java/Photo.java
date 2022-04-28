import java.io.File;
import java.nio.file.Path;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;


/**
 * Holds all relevant details of a given photo
 *
 * @author Hummus-ful
 */
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

    /**
     * Set photo absolute path.
     * @param path Absolute or relative photo path
     */
    private void setPath(String path) {
        this.path = Path.of(path).toAbsolutePath().normalize().toString();
    }

    /**
     * Calculate the hash of the photo.
     * @throws IOException If error occurs during photo load.
     */
    private void setHash() throws IOException {
        hashingAlgorithm = new AverageHash(keyLength);
        Hash calculatedHash = hashingAlgorithm.hash(file);
        this.hash = calculatedHash;
    }

    /**
     * Set the value of the photo hash.
     */
    private void setHashValue() {
        this.hashValue = hash.getHashValue();
    }

    /**
     * Set photo size in bytes.
     */
    private void setFileSize() {
        fileSize = file.length();
    }

    /**
     * Set photo height and width in pixels.
     * @throws IOException If error occurs during photo read.
     */
    private void setPhotoDimensions() throws IOException{
        BufferedImage bufferedImage = ImageIO.read(file);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    /**
     * Illegal constructor used to deny any photo creation without providing path to photo.
     */
    public Photo() {
        throw new IllegalArgumentException();
    }

    /**
     * Create a photo object using constructor.
     * @param path Absolute or relative path to photo.
     * @throws IOException If file unable to calculate photo hash.
     */
    public Photo(String path) throws IOException {
        setPath(path);
        file = new File(this.path);
        setHash();
        setHashValue();
        setFileSize();
        setPhotoDimensions();
    }

    /**
     * Return calculated hash of the photo.
     * @return Hash of photo.
     */
    public Hash getHash() {
        return hash;
    }

    /**
     * Return the value of the photo hash.
     * @return Hash value the photo.
     */
    public BigInteger getHashValue() {
        return hashValue;
    }

    /**
     * Return photo absolute path.
     * @return photo absolute path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Return photo size in bytes.
     * @return photo size in bytes.
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * Return photo width in pixels.
     * @return photo width in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return photo height in pixels.
     * @return photo height in pixels.
     */
    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return path;
    }
}
