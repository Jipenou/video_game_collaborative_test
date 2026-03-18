package controller.load;

/**
 * An enum the field to get in the signalement csv
 */
public enum EnumSignalementLoad {
    REPORTER("reporter",0),
    EVALUATOR("evaluator",1),
    VIDEO_GAME("videoGame", 2),
    PLATFORM("platform", 3),
    DATE("dateEvaluation",4);


    /** name */
    private final String name;

    /** index of the column in the csv */
    private final int index;

    EnumSignalementLoad(String name, int index){
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
