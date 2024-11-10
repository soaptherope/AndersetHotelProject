package org.andersen.util;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class StateLoader {

    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;

    public StateLoader(FileInputStream fileInputStream, ObjectInputStream objectInputStream) {
        this.fileInputStream = fileInputStream;
        this.objectInputStream = objectInputStream;
    }

    public StateLoader() {}

    public <T> T loadState(String filePath, Class<T> clazz) {
        T state = null;
        try {
            state = clazz.cast(objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return state;
    }
}

