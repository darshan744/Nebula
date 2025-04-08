package com.github.darshan744.nebula.Logger;

import java.util.logging.Logger;

/**
 * Custom logger implements NebulaLogger
 * Wrapper around JUL
 * So that we can also change the logger whenever necessary
 */
class JDKLogger implements NebulaLogger {
    private Logger logger = null;
    JDKLogger(Class<?> clazz) {
        this.logger = Logger.getLogger(clazz.getName());
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
