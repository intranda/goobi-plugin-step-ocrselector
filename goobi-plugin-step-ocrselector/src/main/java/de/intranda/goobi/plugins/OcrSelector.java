package de.intranda.goobi.plugins;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import org.goobi.beans.Step;
import org.goobi.production.enums.PluginGuiType;
import org.goobi.production.enums.PluginType;
import org.goobi.production.enums.StepReturnValue;
import org.goobi.production.plugin.interfaces.IRestGuiPlugin;

import de.intranda.goobi.plugins.ocrselector.Routes;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import spark.Service;

@Log4j
@Data
@PluginImplementation
public class OcrSelector implements IRestGuiPlugin {
    private Step step;
    private String returnPath;
    private String title = "intranda_step_ocrselector";

    @Override
    public void extractAssets(Path assetsDir) {
        String[] paths = new String[] { "css/style.css", "js/app.js", "js/riot.min.js", "js/tags.js", "js/ugh.js" };
        for (String p : paths) {
            extractFile(p, assetsDir);
        }
    }

    private void extractFile(String filePath, Path assetsDir) {
        Path out = assetsDir.resolve("plugins").resolve(title).resolve(filePath);
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("/frontend/" + filePath)) {
            if (!Files.exists(out.getParent())) {
                Files.createDirectories(out.getParent());
            }
            if (is != null) {
                Files.copy(is, out, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e);
        }
    }

    @Override
    public String cancel() {
        return "/uii/" + returnPath;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public String finish() {
        return "/uii/" + returnPath;
    }

    @Override
    public String getPagePath() {
        return "/uii/guiPlugin.xhtml";
    }

    @Override
    public PluginGuiType getPluginGuiType() {
        // TODO Auto-generated method stub
        return PluginGuiType.FULL;
    }

    @Override
    public void initialize(Step step, String returnPath) {
        log.info(returnPath);
        this.step = step;
        this.returnPath = returnPath;

    }

    @Override
    public HashMap<String, StepReturnValue> validate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PluginType getType() {
        return PluginType.Step;
    }

    @Override
    public String[] getJsPaths() {
        return new String[] { "js/riot.min.js", "js/tags.js", "js/app.js", "js/ugh.js" };
    }

    @Override
    public void initRoutes(Service http) {
        Routes.initRoutes(http);

    }
}
