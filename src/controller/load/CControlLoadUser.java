package controller.load;

import model.data.CDatabase;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;

import java.io.*;

public class CControlLoadUser {
    private static final String USER_FILE = "./data/user.csv";
    private static final String SEPARATOR = ",";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadUser(CDatabase database){
        this.database = database;
    }

    public void loadUsers() {
        File userFile = new File(USER_FILE);
        if(!userFile.exists()){
            return;
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(userFile));
            String line;
            file.readLine();

            while ((line = file.readLine()) != null) {

                String[] values = line.split(SEPARATOR);

                if(values.length == 2){
                    String pseudo = values[0];
                    String role = values[1];

                    AUser user = switch (role) {
                        case CTester.ROLE -> new CTester(pseudo);
                        case CAdmin.ROLE -> new CAdmin(pseudo);
                        default -> new CPlayer(pseudo);
                    };

                    database.addUser(user);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Save a user in the user.csv
     * @param user the user to save
     */
    public void saveUser(AUser user){
        File file = new File(USER_FILE);
        boolean exists = file.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(!exists){
                bw.write("pseudo,role");
                bw.newLine();
            }

            String role;
            if(user instanceof CTester) role = CTester.ROLE;
            else if(user instanceof CAdmin) role = CAdmin.ROLE;
            else role = CPlayer.ROLE;

            bw.write(user.getPseudo() + "," + role);
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
