package view.videoGame.evaluation;

import controller.CEvaluationController;
import controller.CVideoGameController;
import model.videoGame.CEvaluation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CEvaluationView extends JFrame {

    /** the main controller of the application */
    private final CEvaluationController evaluationController;

    public CEvaluationView(CEvaluationController evaluationController) {
        this.evaluationController = evaluationController;

        setTitle("Liste des évaluations");
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<CEvaluation> evaluations = evaluationController.getController().getDatabase().getEvaluations();

        JList<CEvaluation> list = new JList<>(evaluations.toArray(new CEvaluation[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton("Voir infos");
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(e -> {
            CEvaluation evaluation = list.getSelectedValue();
            if(evaluation != null) {
                evaluationController.displayEvaluation(evaluation);
            }
        });

        add(panel);
    }
}
