package controller.load;

public enum EnumGameOwnedLoad {
    PSEUDO("pseudo",0),
    PLATFORM("platform", 1),
    VIDEO_GAME("video_game", 2),
    HOURS_PLAYED("hours_played", 3);

    /** name */
    private final String name;

    /** index of the column in the csv */
    private final int index;

    EnumGameOwnedLoad(String name, int index){
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
