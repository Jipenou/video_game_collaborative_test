package controller.load;

import model.data.CDatabase;
import model.user.AUser;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;

import java.io.*;

/**
 * This class represent the csv parser for the users
 */
public class CControlLoadUser {

    /** The path for the csv */
    private static final String USER_FILE = "./data/user.csv";

    /** the separator */
    private static final String SEPARATOR = ",";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadUser(CDatabase database){
        this.database = database;
    }

    /**
     * load all the users
     */
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

                if(values.length == EnumUserLoad.values().length){
                    String pseudo = values[EnumUserLoad.PSEUDO.getIndex()];
                    String role = values[EnumUserLoad.ROLE.getIndex()];

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

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(file.length() == 0){
                for (EnumUserLoad enumValue : EnumUserLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            String role;
            if(user instanceof CTester) role = CTester.ROLE;
            else if(user instanceof CAdmin) role = CAdmin.ROLE;
            else role = CPlayer.ROLE;

            bw.write(user.getPseudo() + SEPARATOR + role);
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void clearCSV(){
        try (FileWriter writer = new FileWriter(USER_FILE, false)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Error while clearing CSV file : " + USER_FILE, e);
        }
    }
}
