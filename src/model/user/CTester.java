package model.user;

import model.videoGame.CTest;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a tester (a tester is a player that give test)
 */
public class CTester extends CPlayer{
    /**
     * The list of test of the tester
     */
    protected List<CTest> tests;

    /** The role of the tester */
    public static final String ROLE = "TESTER";

    public CTester(String pseudo) {
        super(pseudo);
        tests = new ArrayList<>();
    }

    /**
     * Add a test to the list of test
     * @param test the test to add
     */
    public void addTest(CTest test) {
        tests.add(test);
        addJeton(5);
    }

    /**
     *
     * @return the number of tests realized
     */
    public int getNbTest(){
        return tests.size();
    }

    /**
     * @return the role of this tester
     */
    @Override
    public String getRole() {
        return ROLE;
    }
}
