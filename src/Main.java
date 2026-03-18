import controller.CController;
import model.data.CDatabase;
import view.user.CLoginView;

/**
 * The main class of this application
 */
public class Main {
    public static void main(String[] args){

        // initialisation of the database
        CDatabase database = new CDatabase();

        // initialisation of the main controller of the application
        CController controller = new CController(database);

        javax.swing.SwingUtilities.invokeLater(() -> {
            new CLoginView(controller).setVisible(true);
        });
    }
}
