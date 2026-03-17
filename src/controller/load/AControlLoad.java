package controller.load;

import java.io.FileWriter;
import java.io.IOException;

public abstract class AControlLoad<T> {

    protected static final String SEPARATOR = ",";

    protected abstract String getFilePath();
    protected abstract void save(T entity);
    protected abstract void load();

    public void clearCSV() {
        try (FileWriter writer = new FileWriter(getFilePath(), false)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Error while clearing CSV file : " + getFilePath(), e);
        }
    }
}