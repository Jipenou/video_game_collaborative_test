package controller.load;

/**
 * An enum that list the field to get in the evaluations_vote csv data
 */
public enum EnumEvaluationUserLoad {
    PSEUDO_VOTANT("pseudo_votant",0),
    VALUE("value",1),
    PSEUDO_CREATEUR("pseudo_createur", 2),
    VIDEO_GAME("video_game", 3),
    PLATFORM("platform", 4),
    DATE("date", 5);

    /** name */
    private final String name;

    /** index of the column in the csv */
    private final int index;

    EnumEvaluationUserLoad(String name, int index){
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
