package Client.GUI.Action;

import java.io.*;

@FunctionalInterface
public interface ThrowableFunction {
    void run() throws IllegalStateException, IOException;
}