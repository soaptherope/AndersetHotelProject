package org.andersen.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class StateSaver {

    public byte[] saveState(Object object) throws IOException {
        byte[] serializedData;

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            serializedData = byteArrayOutputStream.toByteArray();
        }
        return serializedData;
    }
}
