package com.nebula.Logger;

/**
 * Logger factory for NebulaLogger
 * 
 */
public class NebulaLoggerFactory {
    
    public static NebulaLogger getLogger(Class<?> clazz) {
        return new JDKLogger(clazz);
    }

}
