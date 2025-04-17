package io.github.darshan744.nebula.Logger.LoggerImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ColorFormatter extends Formatter {

    private final String RESET  = "\u001B[0m";
    private final String BLUE = "\u001B[34m";
    private final String YELLOW = "\u001B[33m";
    private final String RED = "\u001B[31m";
    private final String CYAN  = "\u001B[36m";
    
    @Override
    public String format(LogRecord record) {
        String color;
        if(record.getLevel() == Level.SEVERE) {
            color = RED;
        }
        else if(record.getLevel() == Level.INFO){
            color = BLUE;
        }
        else if(record.getLevel() == Level.WARNING){
            color = YELLOW;
        }
        else {
            color = CYAN;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        return color  + format.format(new Date()) + " " + "["+record.getLevel() + "] "  + formatMessage(record) + RESET +"\n";
    }
    
}
