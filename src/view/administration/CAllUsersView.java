package view.administration;

import controller.CAdminController;
import model.user.AUser;
import model.utils.CTextPlaceHolder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represent the frame to see all the users of the application
 */
public class CAllUsersView extends JFrame{

    public CAllUsersView(CAdminController adminController) {

        setTitle(CTextPlaceHolder.capitalize(CTextPlaceHolder.LISTE) + " des " + CTextPlaceHolder.UTILISATEUR_S);
        setSize(800,700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        List<AUser> users = adminController.getController().getDatabase().getAllUsers();

        JList<AUser> list = new JList<>(users.toArray(new AUser[0]));

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton viewButton = new JButton(CTextPlaceHolder.capitalize(CTextPlaceHolder.VOIR + " " + CTextPlaceHolder.INFORMATION_S));
        panel.add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(_ -> {
            AUser user = list.getSelectedValue();
            if(user != null) {
                adminController.getController().getUserController().openProfile(user);
            }
        });

        add(panel);
    }
}
