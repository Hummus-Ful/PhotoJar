import java.io.Writer;
import java.util.HashMap;
import java.io.FileWriter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


/**
 * Output List of photos into log or html files.
 *
 * @author Hummus-ful
 */
public class Output {

    private static VelocityEngine velocityEngine;
    private static Template template;

    /**
     * Choose the correrct Velocity template for the expected photo map.
     * @param fileName File name to write into.
     */
    private static void chooseTemplate(String fileName) {
        if (fileName.toLowerCase().startsWith("similar")) {
            template = velocityEngine.getTemplate("Similar.vm");
        }
        else {
            template = velocityEngine.getTemplate("Duplicate.vm");
        }
    }

    /**
     * Set and initiate Velocity Engine.
     */
    private static void initVelocityEngine() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("runtime.log.logsystem.class", NullLogChute.class.getName());
        velocityEngine.init();
    }

    /**
     * Validate the file name include html extension, otherwise add .html suffix to the file name.
     * @param fileName Given file name to validate.
     * @return File name with html suffix.
     */
    private static String validateHtmlExtension(String fileName) {
        if(fileName.toLowerCase().endsWith(".html")) return fileName;
        else return fileName + ".html";
    }

    /**
     * Validate the file name include log extension, otherwise add .log suffix to the file name.
     * @param fileName Given file name validate.
     * @return File name with log suffix.
     */
    private static String validateLogExtension(String fileName) {
        if(fileName.endsWith(".log")) return fileName;
        else return fileName + ".log";
    }

    /**
     * Output map into log file.
     * @param photoHashMap Map of photos to log.
     * @param fileName File name to write into.
     */
    public static void toPlainText(HashMap<Photo, Photo> photoHashMap, String fileName) {
        fileName = validateLogExtension(fileName);
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            for (Photo photo : photoHashMap.keySet()) {
                fileWriter.write(photo.getPath() + "\n");
            }
            fileWriter.close();
            System.out.println("Output to " + fileName + " Done");
        }
        catch (Exception e) {
            //TODO
        }

    }

    /**
     * Output map into html file.
     * @param photoHashMap Map of photos to log.
     * @param fileName File name to write into.
     */
    public static void toHtml(HashMap<Photo, Photo> photoHashMap, String fileName) {
        fileName = validateHtmlExtension(fileName);
        initVelocityEngine();
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("photoHashMap", photoHashMap);
        chooseTemplate(fileName);
        try {
            Writer writer = new FileWriter(fileName);
            template.merge(velocityContext, writer);
            writer.flush();
            writer.close();
            System.out.println("Output to " + fileName + " Done");
        }
        catch (Exception e) {
            //TODO
        }
    }
}
