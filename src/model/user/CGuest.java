package model.user;

public class CGuest extends AUser {
    public static final String ROLE = "GUEST";
    public static final String PSEUDO = "Invité";

    public CGuest() {
        super(PSEUDO);
    }

    @Override
    public String getRole() {
        return ROLE;
    }
}