package org.andersen.repository;

import org.andersen.config.DatabaseConfig;
import org.andersen.util.StateSaver;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StateRepository {

    private final Connection connection;
    private final StateSaver stateSaver;

    public StateRepository() throws SQLException {
        this.connection = DatabaseConfig.getConnection();
        this.stateSaver = new StateSaver();
    }

    public StateRepository(Connection connection, StateSaver stateSaver) {
        this.connection = connection;
        this.stateSaver = stateSaver;
    }

    public void saveSerializedData(Object object) throws SQLException, IOException {
        String query = "INSERT INTO serialized_data (type, data) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            byte[] serializedData = stateSaver.saveState(object);

            preparedStatement.setString(1, object.getClass().getSimpleName());
            preparedStatement.setBlob(2, new SerialBlob(serializedData));

            preparedStatement.executeUpdate();

            connection.commit();
        }
    }
}
