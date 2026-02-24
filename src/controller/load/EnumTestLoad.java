package controller.load;

/**
 * An enum that list the field to get in the test csv data
 */
public enum EnumTestLoad {
    PSEUDO("pseudo",0),
    VIDEO_GAME("videoGame",1),
    PLATFORM("Platform",2),
    TEXT("text",3),
    VERSION("version",4),
    CONDITION("conditions",5);

    /** name */
    private final String name;

    /** index of the column in the csv */
    private final int index;

    EnumTestLoad(String name, int index){
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
