package repository;

import org.andersen.repository.StateRepository;
import org.andersen.util.StateSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StateRepositoryTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private StateSaver mockStateSaver;

    @InjectMocks
    private StateRepository stateRepository;

    @BeforeEach
    void setUp() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void saveSerializedData_Successful() throws SQLException, IOException {
        String mockType = "SerializableTestObject";
        byte[] mockData = "test".getBytes();

        when(mockStateSaver.saveState(any())).thenReturn(mockData);

        assertDoesNotThrow(() -> stateRepository.saveSerializedData(new SerializableTestObject("test")));

        verify(mockConnection).setAutoCommit(false);
        verify(mockPreparedStatement).setString(1, mockType);
        verify(mockPreparedStatement).setBlob(2, new SerialBlob(mockData));
        verify(mockPreparedStatement).executeUpdate();
        verify(mockConnection).commit();
    }

    record SerializableTestObject(String data) implements java.io.Serializable {
    }
}
