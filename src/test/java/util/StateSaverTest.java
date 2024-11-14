package util;

import org.andersen.util.StateSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StateSaverTest {

    private StateSaver stateSaver;

    @BeforeEach
    void setUp() {
        stateSaver = new StateSaver();
    }

    @Test
    void saveState_Success() throws IOException, ClassNotFoundException {
        SerializableTestObject originalObject = new SerializableTestObject("test");

        byte[] serializedData = stateSaver.saveState(originalObject);

        assertNotNull(serializedData);
        assertTrue(serializedData.length > 0);

        SerializableTestObject deserializedObject = deserialize(serializedData);

        assertEquals(originalObject, deserializedObject);
    }

    @Test
    void saveState_Fail_NonSerializableObject() {
        NotSerializableTestObject nonSerializableObject = new NotSerializableTestObject("test");

        assertThrows(IOException.class, () -> stateSaver.saveState(nonSerializableObject));
    }

    private SerializableTestObject deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data); ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (SerializableTestObject) objectInputStream.readObject();
        }
    }

    record SerializableTestObject(String data) implements java.io.Serializable {
    }

    record NotSerializableTestObject(String data) {
    }
}
