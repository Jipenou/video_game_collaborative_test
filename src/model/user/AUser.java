package model.user;

/**
 * This abstract class represent a user
 */
public abstract class AUser {
    /** pseudo of the user */
    protected String pseudo;

    /** tell if the user has been blocked by an admin or not */
    protected boolean isBlocked;

    public AUser(String pseudo){
        this.pseudo = pseudo;
        this.isBlocked = false;
    }

    /**
     *
     * @return the pseudo of the user
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * block a user
     */
    public void block(){
        this.isBlocked = true;
    }

    /**
     * unblock a user
     */
    public void unblock(){
        this.isBlocked = false;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * The role of the user
     * @return the role
     */
    public abstract String getRole();

    public String toString(){
        return this.pseudo + " (" + getRole() + ")";
    }
}
