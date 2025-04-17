package io.github.darshan744.nebula.Logger;

import io.github.darshan744.nebula.Logger.LoggerImpl.*;;

/**
 * Logger factory for NebulaLogger
 * 
 */
public class NebulaLoggerFactory {
    
    public static NebulaLogger getLogger(Class<?> clazz) {
        return new JDKLogger(clazz);
    }

}
