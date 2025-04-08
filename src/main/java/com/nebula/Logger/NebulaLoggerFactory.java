package com.nebula.Logger;

public class NebulaLoggerFactory {
    
    public static NebulaLogger getLogger(Class<?> clazz) {
        return new JDKLogger(clazz);
    }

}
