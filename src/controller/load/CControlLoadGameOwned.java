package controller.load;

import model.data.CDatabase;
import model.user.*;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import java.io.*;

public class CControlLoadGameOwned {

    /** The path for the csv */
    private static final String GAME_OWNED_FILE = "./data/gameOwned.csv";

    /** the separator */
    private static final String SEPARATOR = ",";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadGameOwned(CDatabase database){
        this.database = database;
    }

    /**
     * load all the csv
     */
    public void loadPlayerGame() {
        File userFile = new File(GAME_OWNED_FILE);
        if(!userFile.exists()){
            return;
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(userFile));
            String line;
            file.readLine();

            while ((line = file.readLine()) != null) {

                String[] values = line.split(SEPARATOR);

                if(values.length == EnumGameOwnedLoad.values().length){
                    String pseudo = values[EnumGameOwnedLoad.PSEUDO.getIndex()];
                    String platformName = values[EnumGameOwnedLoad.PLATFORM.getIndex()];
                    String videoGameName = values[EnumGameOwnedLoad.VIDEO_GAME.getIndex()];
                    float hoursPlayed = Float.parseFloat(values[EnumGameOwnedLoad.HOURS_PLAYED.getIndex()]);

                    CPlayer player = (CPlayer) database.getUser(pseudo);
                    CVideoGame videoGame = database.getVideoGame(videoGameName);
                    CPlatform platform = videoGame.getPlatform(platformName);

                    CPlayerGame playerGame = new CPlayerGame(player, videoGame, platform, hoursPlayed);
                    database.addPlayerGame(playerGame);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Save a player game in the csv
     * @param playerGame the player game to save
     */
    public void savePlayerGame(CPlayerGame playerGame){
        File file = new File(GAME_OWNED_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(file.length() == 0){
                for (EnumGameOwnedLoad enumValue : EnumGameOwnedLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            bw.write(playerGame.getPlayer().getPseudo() + SEPARATOR + playerGame.getPlatform().getName()
                    + SEPARATOR + playerGame.getVideoGame().getName() + SEPARATOR + playerGame.getHoursPlayed());
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * clear the csv
     */
    public void clearCSV(){
        try (FileWriter writer = new FileWriter(GAME_OWNED_FILE, false)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Error while clearing CSV file : " + GAME_OWNED_FILE, e);
        }
    }
}
