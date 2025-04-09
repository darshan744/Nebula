package io.github.darshan744.nebula.Logger;

public interface NebulaLogger {
    void severe(String message);
    void warn(String message);
    void info(String message);
    void config(String message);
    void fine(String message);
}
