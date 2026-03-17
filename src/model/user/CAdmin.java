package model.user;

/**
 * The role of the admin (the admin is a tester with privilege
 */
public class CAdmin extends CTester{
    /** the role of the admin */
    public static final String ROLE = "ADMIN";

    public CAdmin(String pseudo) {
        super(pseudo);
    }

    /**
     * @return the role of the admin
     */
    @Override
    public String getRole() {
        return ROLE;
    }
}
