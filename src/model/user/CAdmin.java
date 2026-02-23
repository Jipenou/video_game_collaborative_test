package model.user;

import model.videoGame.CEvaluation;

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
     * Delete an evaluation
     * @param eval the evaluation to delete
     */
    public void deleteEvaluation(CEvaluation eval) {
    }

    /**
     * Block a user
     * @param user the user to block
     */
    public void blockUser(AUser user) {
        user.block();
    }

    /**
     * @return the role of the admin
     */
    @Override
    public String getRole() {
        return ROLE;
    }
}
