package controller.load;

import model.data.CDatabase;
import model.user.CAdmin;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CVideoGame;

import java.io.*;

public class CControlLoadTokenOnGame {

    /** The path for the csv */
    private static final String TOKEN_ON_FILE = "./data/tokenOnGame.csv";

    /** the separator */
    private static final String SEPARATOR = ",";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadTokenOnGame(CDatabase database){
        this.database = database;
    }

    /**
     * load all the users
     */
    public void loadTokenOnGame() {
        File tokenOnGameFile = new File(TOKEN_ON_FILE);
        if(!tokenOnGameFile.exists()){
            return;
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(tokenOnGameFile));
            String line;
            file.readLine();

            while ((line = file.readLine()) != null) {

                String[] values = line.split(SEPARATOR);

                if(values.length == EnumTokenOnGameLoad.values().length){
                    String videoGameName = values[EnumTokenOnGameLoad.VIDEO_GAME.getIndex()];
                    String pseudo = values[EnumTokenOnGameLoad.PSEUDO.getIndex()];
                    int nbToken = Integer.parseInt(values[EnumTokenOnGameLoad.TOKEN.getIndex()]);

                    CPlayer player = (CPlayer) database.getUser(pseudo);
                    CVideoGame videoGame = database.getVideoGame(videoGameName);

                    videoGame.addTokenToGame(player, nbToken);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Save the token placed in a game in the csv
     *
     */
    public void saveTokenOnGame(CVideoGame videoGame, CPlayer player, int nbToken){
        File file = new File(TOKEN_ON_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(file.length() == 0){
                for (EnumTokenOnGameLoad enumValue : EnumTokenOnGameLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            bw.write(videoGame.getName() + SEPARATOR + player.getPseudo() + SEPARATOR + nbToken);
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void clearCSV(){
        try (FileWriter writer = new FileWriter(TOKEN_ON_FILE, false)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Error while clearing CSV file : " + TOKEN_ON_FILE, e);
        }
    }
}
