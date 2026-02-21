package controller.load;

/**
 * An enum that list the field to get in the video games csv data
 */
public enum EnumCSVFieldToGet{
    NAME("Name"),
    CATEGORY("Genre"),
    EDITOR("Publisher"),
    RATING("Rating"),
    PLATFORM("Platform"),
    YEAR_OF_RELEASE("Year_of_Release"),
    DEVELOPER("Developer"),
    GLOBAL_SALES("Global_Sales"),
    CRITIC_COUNT("Critic_Count"),
    CRITIC_SCORE("Critic_Score"),
    USER_COUNT("User_Count"),
    USER_SCORE("User_Score");

    private final String name;

    EnumCSVFieldToGet(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
