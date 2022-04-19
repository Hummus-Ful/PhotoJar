import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

public class Output {

    public static void toPlainText(ArrayList<Photo> photos, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        for (Photo photo : photos) {
            fileWriter.write(photo.getPath() + "\n");
        }
        fileWriter.close();
    }

    public static void toHtml(HashMap<String, String> photoHashMap, String fileName) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("runtime.log.logsystem.class", NullLogChute.class.getName());
        velocityEngine.init();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("similar", photoHashMap);

        Template template = velocityEngine.getTemplate("index.vm");

        try {
            Writer writer = new FileWriter(fileName);
            template.merge(velocityContext, writer);
            writer.flush();
            writer.close();
        }
        catch (Exception e) {
            //TODO
        }
    }
}