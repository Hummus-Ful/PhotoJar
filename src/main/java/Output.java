import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;


public class Output {

    private static VelocityEngine velocityEngine;
    private static Template template;

    private static void chooseTemplate(String fileName) {
        if (fileName.toLowerCase().startsWith("similar")) {
            template = velocityEngine.getTemplate("Similar.vm");
        }
        else {
            template = velocityEngine.getTemplate("Duplicate.vm");
        }
    }

    private static void initVelocityEngine() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("runtime.log.logsystem.class", NullLogChute.class.getName());
        velocityEngine.init();
    }

    private static String validateHtmlExtension(String fileName) {
        if(fileName.toLowerCase().endsWith(".html")) return fileName;
        else return fileName + ".html";
    }

    private static String validateLogExtension(String fileName) {
        if(fileName.endsWith(".log")) return fileName;
        else return fileName + ".log";
    }

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
