package view.administration;

import controller.CAdminController;
import model.CSignalement;
import model.user.AUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CSignalementView extends JFrame{

    /** the admin controller of the application */
    private final CAdminController adminController;

    public CSignalementView(CAdminController adminController) {

        this.adminController = adminController;

        setTitle("Signalements");
        setSize(800,700);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        AUser user = adminController.getController().getCurrentUser();

        JPanel panel = new JPanel(new GridLayout(0,1));

        panel.add(new JLabel("Menu des signalements"));

        List<CSignalement> signalements = adminController.getController().getDatabase().getSignaledEvaluations();

        JList<CSignalement> list = new JList<>(signalements.toArray(new CSignalement[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton("Voir infos");
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(e -> {
            CSignalement signalement = list.getSelectedValue();
            if(signalement != null) {
                adminController.viewInfoSignalement(signalement);
            }
        });

        add(panel);
    }
}
