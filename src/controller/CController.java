package controller;

import model.data.CDatabase;

public class CController {
    /** the database of the video games */
    private final CDatabase database;

    public CController(CDatabase database){
        this.database = database;
    }

    public CDatabase getDatabase() {
        return database;
    }
}
