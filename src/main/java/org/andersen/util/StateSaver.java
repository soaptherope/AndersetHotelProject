package org.andersen.util;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class StateSaver {
    public void saveState(Object state, String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

