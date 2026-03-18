package controller.load;

/**
 * An enum the field to get in the token csv
 */
public enum EnumTokenOnGameLoad {
    VIDEO_GAME("video_game",0),
    PSEUDO("pseudo",1),
    TOKEN("jetons",2);

    /** name */
    private final String name;

    /** index of the column in the csv */
    private final int index;

    EnumTokenOnGameLoad(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
