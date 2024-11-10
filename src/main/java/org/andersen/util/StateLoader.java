package org.andersen.util;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class StateLoader {
    public <T> T loadState(String filePath, Class<T> clazz) {
        T state = null;
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
             state = clazz.cast(in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return state;
    }
}

