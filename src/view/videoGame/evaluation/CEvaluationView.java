package view.videoGame.evaluation;

import controller.CEvaluationController;
import model.utils.CTextPlaceHolder;
import model.videoGame.CEvaluation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represent the frame that displayed all the evaluations
 */
public class CEvaluationView extends JFrame {

    public CEvaluationView(CEvaluationController evaluationController) {

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.LISTE) + " des " + CTextPlaceHolder.EVALUATION_S);
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<CEvaluation> evaluations = evaluationController.getController().getDatabase().getEvaluations();

        JList<CEvaluation> list = new JList<>(evaluations.toArray(new CEvaluation[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VOIR) + " " + CTextPlaceHolder.INFORMATION_S);
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(_ -> {
            CEvaluation evaluation = list.getSelectedValue();
            if(evaluation != null) {
                evaluationController.displayEvaluation(evaluation);
            }
        });

        add(panel);
    }
}
