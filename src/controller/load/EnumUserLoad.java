package controller.load;

/**
 * An enum that list the field to get in the user csv data
 */
public enum EnumUserLoad {
    PSEUDO("pseudo",0),
    ROLE("role",1),
    TOKEN("jetons",2);


    /** name */
    private final String name;

    /** index of the column in the csv */
    private final int index;

    EnumUserLoad(String name, int index){
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
