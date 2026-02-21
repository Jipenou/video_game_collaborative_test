package model.videoGame;

import java.util.ArrayList;

/**
 * This class represent a video game
 */
public class CVideoGame {

    /** name of the video game */
    private final String name;

    /** the category of the video game */
    private final String category;

    /** the editor of the video game */
    private final String editor;

    /** the overall rating of the video game */
    private final Character rating;

    /** All the platforms that handle the video game */
    private final ArrayList<CPlatform> platforms;

    public CVideoGame(String name, String category, String editor, Character rating){
        this.name = name;
        this.category = category;
        this.editor = editor;
        this.rating = rating;
        this.platforms = new ArrayList<>();
    }

    public void addPlatform(CPlatform platform){
        platforms.add(platform);
    }

    /**
     *
     * @return the category of the video game
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @return the name of the video game
     */
    public String getName() {
        return name;
    }


    /**
     *
     * @return the editor of the video game
     */
    public String getEditor() {
        return editor;
    }

    /**
     *
     * @return the rating of the video game
     */
    public char getRating() {
        return rating;
    }

    /**
     *
     * @return all the platform that contain this video game
     */
    public ArrayList<CPlatform> getCPlatform() {
        return platforms;
    }
}
