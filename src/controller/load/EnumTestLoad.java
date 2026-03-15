package controller.load;

import model.videoGame.CTest;

/**
 * An enum that list the field to get in the test csv data
 */
public enum EnumTestLoad {
    PSEUDO("pseudo",0),
    VIDEO_GAME("videoGame",1),
    PLATFORM("Platform",2),
    TEXT("text",3),
    VERSION("version",4),
    CONDITION("conditions",5),
    DATE("date", 6),
    INTERFACE(CTest.ECategory.INTERFACE.name(), 7),
    GAMEPLAY(CTest.ECategory.GAMEPLAY.name(), 8),
    OPTIMISATION(CTest.ECategory.OPTIMISATION.name(), 9),
    GRAPHICS(CTest.ECategory.GRAPHICS.name(), 10),
    SOUND(CTest.ECategory.SOUND.name(), 11);

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
