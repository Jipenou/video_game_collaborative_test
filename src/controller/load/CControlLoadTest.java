package controller.load;

import model.data.CDatabase;
import model.user.CTester;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import java.io.*;

public class CControlLoadTest {
    /** path to the csv */
    private static final String TEST_FILE = "./data/tests.csv";

    /** separator for the csv */
    private static final String SEPARATOR = ",";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadTest(CDatabase database){
        this.database = database;
    }

    /** load All the evaluations */
    public void loadTests() {
        File testFile = new File(TEST_FILE);
        if(!testFile.exists()){
            return;
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(testFile));
            String line;
            file.readLine();

            while ((line = file.readLine()) != null) {

                String[] values = line.split(SEPARATOR);

                if(values.length == EnumTestLoad.values().length){
                    String pseudo = values[EnumTestLoad.PSEUDO.getIndex()];
                    String videoGameName = values[EnumTestLoad.VIDEO_GAME.getIndex()];
                    String platformName = values[EnumTestLoad.PLATFORM.getIndex()];
                    String text = values[EnumTestLoad.TEXT.getIndex()];
                    String numVersion = values[EnumTestLoad.VERSION.getIndex()];
                    String conditions = values[EnumTestLoad.CONDITION.getIndex()];

                    CTester user = (CTester) database.getUser(pseudo);
                    CVideoGame videoGame = database.getVideoGame(videoGameName);
                    CPlatform platform = videoGame.getPlatform(platformName);

                    CTest test = new CTest(user, videoGame, platform, text, numVersion, conditions);

                    database.addTest(test);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Save an evaluation in the csv
     * @param test the evaluation to save
     */
    public void saveTest(CTest test){
        File file = new File(TEST_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(file.length() == 0){
                for (EnumTestLoad enumValue : EnumTestLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            bw.write(test.getTester().getPseudo() + SEPARATOR + test.getVideoGame().getName()
                    + SEPARATOR + test.getPlatform().getName() + SEPARATOR + test.getText() + SEPARATOR
                    + test.getNumVersion() + SEPARATOR + test.getConditions());
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void clearCSV(){
        try (FileWriter writer = new FileWriter(TEST_FILE, false)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Error while clearing CSV file : " + TEST_FILE, e);
        }
    }
}
