package model.user;

/**
 * This class represent a guest in the application
 */
public class CGuest extends AUser {
    private static final String ROLE = "GUEST";
    private static final String PSEUDO = "Invité";

    public CGuest() {
        super(PSEUDO);
    }

    @Override
    public String getRole() {
        return ROLE;
    }
}