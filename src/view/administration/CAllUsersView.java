package view.administration;

import controller.CAdminController;
import model.user.AUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CAllUsersView extends JFrame{

    private final CAdminController adminController;

    public CAllUsersView(CAdminController adminController) {
        this.adminController = adminController;

        setTitle("Liste des utilisateurs");
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<AUser> users = adminController.getController().getDatabase().getAllUsers();

        JList<AUser> list = new JList<>(users.toArray(new AUser[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton("Voir infos");
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(e -> {
            AUser user = list.getSelectedValue();
            if(user != null) {
                adminController.getController().getUserController().openProfile(user);
            }
        });

        add(panel);
    }
}
