package io.github.darshan744.nebula.Logger.LoggerImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import io.github.darshan744.nebula.Logger.NebulaLogger;

/**
 * Custom logger implements NebulaLogger
 * Wrapper around JUL
 * So that we can also change the logger whenever necessary
 */
public class JDKLogger implements NebulaLogger {
    private Logger logger = null;

    public JDKLogger(Class<?> clazz) {
        this.logger = Logger.getLogger(clazz.getName());
        this.logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new ColorFormatter());
        handler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);
    }

    /**
     * To configure the file Directory for logs
     * @param fileName
     */
    public void userFileHandler(String fileName) {
        try {
            // log directory
            String fileDir = "logs";
            // create if not exist
            Files.createDirectories(Paths.get(fileDir));
            // custom stamp name for non conflicts
            String stampName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            //full File Name
            String fullFileName = fileDir + File.separator + fileName + "_" + stampName + "_%g.log";
            // logger handler
            FileHandler fileHandler = new FileHandler(fullFileName , 1024 * 1024 , 5 , true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            severe(e.getMessage());
        }

    }

    @Override
    public void config(String message) {
        logger.config(message);
    }

    @Override
    public void fine(String message) {
        logger.fine(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void severe(String message) {
        logger.severe(message);
    }

    @Override
    public void warn(String message) {
        logger.warning(message);
    }

}
