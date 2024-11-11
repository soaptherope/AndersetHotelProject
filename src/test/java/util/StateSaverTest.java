package util;

import org.andersen.config.StateConfig;
import org.andersen.util.StateSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StateSaverTest {

    @Mock
    private FileOutputStream mockFileOutputStream;

    @Mock
    private ObjectOutputStream mockObjectOutputStream;

    @InjectMocks
    private StateSaver stateSaver;

    private final String testFilePath = "path/to/test/file";

    @Test
    void saveState_Success() throws IOException {
        TestObject testObject = new TestObject("test");

        stateSaver.saveState(testObject, testFilePath);

        verify(mockObjectOutputStream, times(1)).writeObject(testObject);
    }

    @Test
    void saveState_IOException() throws IOException {
        TestObject testObject = new TestObject("test");

        doThrow(new IOException()).when(mockObjectOutputStream).writeObject(any());

        assertDoesNotThrow(() -> stateSaver.saveState(testObject, testFilePath));

        verify(mockObjectOutputStream, times(1)).writeObject(testObject);
    }


    @Test
    void constructor_InitializesOutputStreams() throws IOException {
        try (FileOutputStream realFileOut = new FileOutputStream(StateConfig.getStateFilePath());
             ObjectOutputStream realObjectOut = new ObjectOutputStream(realFileOut)) {
            StateSaver stateSaver = new StateSaver();
            assertNotNull(stateSaver);
        }
    }

    record TestObject(String data) implements Serializable {}
}
