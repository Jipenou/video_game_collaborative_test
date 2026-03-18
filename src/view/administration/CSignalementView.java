package view.administration;

import controller.CAdminController;
import model.CSignalement;
import model.utils.CTextPlaceHolder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represent the frame to see all the signalements
 */
public class CSignalementView extends JFrame{

    public CSignalementView(CAdminController adminController) {

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.SIGNALEMENT_S));
        setSize(800,700);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel(CTextPlaceHolder.capitalize(CTextPlaceHolder.MENU) + " des " + CTextPlaceHolder.SIGNALEMENT_S));

        List<CSignalement> signalements = adminController.getController().getDatabase().getSignaledEvaluations();

        JList<CSignalement> list = new JList<>(signalements.toArray(new CSignalement[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VOIR + " " + CTextPlaceHolder.INFORMATION_S));
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(_ -> {
            CSignalement signalement = list.getSelectedValue();
            if(signalement != null) {
                adminController.viewInfoSignalement(signalement);
            }
        });

        add(panel);
    }
}
