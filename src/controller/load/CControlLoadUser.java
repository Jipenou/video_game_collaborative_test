package controller.load;

import model.data.CDatabase;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CTest;

import java.io.*;

/**
 * This class represent the csv parser for the users
 */
public class CControlLoadUser extends AControlLoad<CPlayer>{

    /** The path for the csv */
    private static final String USER_FILE = "./data/user.csv";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadUser(CDatabase database){
        this.database = database;
    }

    @Override
    protected String getFilePath() {
        return USER_FILE;
    }

    /**
     * load all the users
     */
    @Override
    public void load() {
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
                    int nbToken = Integer.parseInt(values[EnumUserLoad.TOKEN.getIndex()]);
                    boolean isBlocked = Boolean.parseBoolean(values[EnumUserLoad.IS_BLOCKED.getIndex()]);

                    CPlayer user = switch (role) {
                        case CTester.ROLE -> new CTester(pseudo);
                        case CAdmin.ROLE -> new CAdmin(pseudo);
                        default -> new CPlayer(pseudo);
                    };

                    user.setNbJeton(nbToken);

                    if(isBlocked){
                        user.block();
                    }

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
    @Override
    public void save(CPlayer user){
        File file = new File(USER_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(file.length() == 0){
                for (EnumUserLoad enumValue : EnumUserLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            String role = user.getRole();

            bw.write(user.getPseudo() + SEPARATOR + role + SEPARATOR + user.getNbToken() + SEPARATOR + user.isBlocked());
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
