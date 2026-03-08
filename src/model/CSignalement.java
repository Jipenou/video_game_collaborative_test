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

    public CTester getReporter() {
        return reporter;
    }

    public CEvaluation getEvaluation() {
        return evaluation;
    }

    public void setReporter(CTester reporter) {
        this.reporter = reporter;
    }

    public String toString(){
        return "User : " + reporter.getPseudo() + " sur : " + evaluation.toStringSimple();
    }
}
