package util;

import org.andersen.util.StateLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StateLoaderTest {

    @Mock
    private FileInputStream mockFileInputStream;

    @Mock
    private ObjectInputStream mockObjectInputStream;

    @InjectMocks
    private StateLoader stateLoader;

    private final String testFilePath = "path/to/test/file";

    @BeforeEach
    void setup() {
        stateLoader = new StateLoader(mockFileInputStream, mockObjectInputStream);
    }

    @Test
    void loadState_Success() throws IOException, ClassNotFoundException {
        TestObject expectedObject = new TestObject("test");

        when(mockObjectInputStream.readObject()).thenReturn(expectedObject);

        TestObject result = stateLoader.loadState(testFilePath, TestObject.class);

        verify(mockObjectInputStream, times(1)).readObject();
        assertNotNull(result);
        assertEquals(expectedObject, result);
        assertEquals("test", result.data());
    }

    @Test
    void loadState_IOException() throws IOException, ClassNotFoundException {
        when(mockObjectInputStream.readObject()).thenThrow(new IOException());

        TestObject result = stateLoader.loadState(testFilePath, TestObject.class);

        assertNull(result);
        verify(mockObjectInputStream, times(1)).readObject();
    }

    @Test
    void loadState_ClassNotFoundException() throws IOException, ClassNotFoundException {
        when(mockObjectInputStream.readObject()).thenThrow(new ClassNotFoundException());

        TestObject result = stateLoader.loadState(testFilePath, TestObject.class);

        assertNull(result);
        verify(mockObjectInputStream, times(1)).readObject();
    }

    @Test
    void loadState_NullObject() throws IOException, ClassNotFoundException {
        when(mockObjectInputStream.readObject()).thenReturn(null);

        TestObject result = stateLoader.loadState(testFilePath, TestObject.class);

        assertNull(result);
        verify(mockObjectInputStream, times(1)).readObject();
    }

    @Test
    void loadState_NotSerializable() throws IOException, ClassNotFoundException {
        when(mockObjectInputStream.readObject()).thenThrow(new ClassNotFoundException());

        TestObject result = stateLoader.loadState(testFilePath, TestObject.class);

        assertNull(result);
        verify(mockObjectInputStream, times(1)).readObject();
    }

    record TestObject(String data) implements Serializable {}
}
