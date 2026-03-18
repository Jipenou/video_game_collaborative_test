package controller.load;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This abstract class represent the controler for the loading of data
 * @param <T> the object to load/save
 */
public abstract class AControlLoad<T> {

    protected static final String SEPARATOR = ",";

    /**
     *
     * @return the path of the file where data are stocked/has to be stocked
     */
    protected abstract String getFilePath();

    /**
     * Save the object in the file
     * @param entity the object to save
     */
    protected abstract void save(T entity);

    /**
     * Load the object stocked in the file
     */
    protected abstract void load();

    /**
     * Clear the file where data were stocked
     */
    public void clearCSV() {
        try (FileWriter writer = new FileWriter(getFilePath(), false)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Error while clearing CSV file : " + getFilePath(), e);
        }
    }
}