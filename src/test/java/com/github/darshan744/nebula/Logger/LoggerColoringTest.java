package com.github.darshan744.nebula.Logger;

import org.junit.jupiter.api.Test;

import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;

public class LoggerColoringTest {

    private NebulaLogger logger = NebulaLoggerFactory.getLogger(getClass());

    @Test
    void testColor() {
        logger.userFileHandler("Test_LOG");
        logger.info("INFO MESSAGE");
        logger.severe("SEVERE MESSAGE");
        logger.warn("WARNING MESSAGE");
        logger.fine("FINE");
    }
}
