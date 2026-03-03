package model.user;

import model.videoGame.CEvaluation;
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
    protected final List<CTest> tests;

    /** The evaluation signaled by the tester */
    protected final List<CEvaluation> signaledEvaluation;


    /** The role of the tester */
    public static final String ROLE = "TESTER";

    /** The number of token give per test */
    public static final int NB_JETONS_PER_TEST = 5;

    public CTester(String pseudo) {
        super(pseudo);
        tests = new ArrayList<>();
        signaledEvaluation = new ArrayList<>();
    }

    /**
     *
     * @param evaluation the evaluation concerned
     * @return true if the evaluation is signaled, else false
     */
    public boolean isEvaluationSignaled(CEvaluation evaluation){
        return signaledEvaluation.contains(evaluation);
    }

    /**
     * Add a signalement for an evaluation
     * @param evaluation the evaluation concerned
     */
    public void signalEvaluation(CEvaluation evaluation){
        signaledEvaluation.add(evaluation);
    }

    public void removeSignaledEvaluation(CEvaluation evaluation){
        signaledEvaluation.remove(evaluation);
    }

    /**
     * Add a test to the list of test
     * @param test the test to add
     */
    public void addTest(CTest test) {
        tests.add(test);
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
