package model.user;

/**
 * This abstract class represent a user
 */
public abstract class AUser {
    /** pseudo of the user */
    protected String pseudo;

    /** number of token of the user */
    protected int nbJeton;

    /** tell if the user has been blocked by an admin or not */
    protected boolean isBlocked;

    public AUser(String pseudo){
        this.pseudo = pseudo;
        this.nbJeton = 0;
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
     *
     * @return the number of token of the user
     */
    public int getNbToken() {
        return nbJeton;
    }

    /**
     * Add token to a player
     * @param nbJeton the number of token to add
     */
    public void addJeton(int nbJeton){
        this.nbJeton += nbJeton;
    }


    /**
     * Remove token to a player
     * @param nbJeton the number oof token to remove
     */
    public void removeJeton(int nbJeton){
        this.nbJeton -= nbJeton;
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

    public void setNbJeton(int nbJeton) {
        this.nbJeton = nbJeton;
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
        return this.pseudo;
    }
}
