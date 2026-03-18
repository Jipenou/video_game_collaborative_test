package model;

import model.user.CTester;
import model.videoGame.CEvaluation;

/**
 * This class represent a signalement of an evaluation
 */
public class CSignalement {

    /** The tester who signaled the evaluation */
    private CTester reporter;

    /** The evaluation signaled */
    private final CEvaluation evaluation;

    public CSignalement(CTester tester, CEvaluation evaluation) {
        this.reporter = tester;
        this.evaluation = evaluation;
    }

    /*
    ===================== GETTER =========================
     */

    /**
     *
     * @return the reporter of this signalement
     */
    public CTester getReporter() {
        return reporter;
    }

    /**
     *
     * @return the evaluation concerned
     */
    public CEvaluation getEvaluation() {
        return evaluation;
    }

    /*
    ===================== SETTER =========================
     */

    /**
     * set a new reporter of this signalement
     * @param reporter the new reporter
     */
    public void setReporter(CTester reporter) {
        this.reporter = reporter;
    }

    /*
    ===================== TO STRING =========================
     */

    public String toString(){
        return "User : " + reporter.getPseudo() + " sur : " + evaluation.toStringSimple();
    }
}
