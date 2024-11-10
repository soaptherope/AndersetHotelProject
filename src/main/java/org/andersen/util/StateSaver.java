    package org.andersen.util;

    import org.andersen.config.StateConfig;

    import java.io.FileOutputStream;
    import java.io.ObjectOutputStream;
    import java.io.IOException;

    public class StateSaver {

        private final FileOutputStream fileOutputStream;
        private final ObjectOutputStream objectOutputStream;

        public StateSaver(FileOutputStream fileOutputStream, ObjectOutputStream objectOutputStream) {
            this.fileOutputStream = fileOutputStream;
            this.objectOutputStream = objectOutputStream;
        }

        public StateSaver() throws IOException {
            this.fileOutputStream = new FileOutputStream(StateConfig.getStateFilePath())    ;
            this.objectOutputStream = new ObjectOutputStream(fileOutputStream);
        }

        public void saveState(Object state, String filePath) {
            try {
                objectOutputStream.writeObject(state);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

