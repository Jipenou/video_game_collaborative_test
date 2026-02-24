package controller.load;

import model.data.CDatabase;
import model.user.CPlayer;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;

import java.io.*;

/**
 * This class represent the CSV parser for the evaluations
 */
public class CControlLoadEvaluation {

    /** path to the csv */
    private static final String EVALUATION_FILE = "./data/evaluations.csv";

    /** separator for the csv */
    private static final String SEPARATOR = ",";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadEvaluation(CDatabase database){
        this.database = database;
    }

    /** load All the evaluations */
    public void loadEvaluations() {
        File evalFile = new File(EVALUATION_FILE);
        if(!evalFile.exists()){
            return;
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(evalFile));
            String line;
            file.readLine();

            while ((line = file.readLine()) != null) {

                String[] values = line.split(SEPARATOR);

                if(values.length == EnumEvaluationLoad.values().length){
                    String pseudo = values[EnumEvaluationLoad.PSEUDO.getIndex()];
                    String videoGameName = values[EnumEvaluationLoad.VIDEO_GAME.getIndex()];
                    String platformName = values[EnumEvaluationLoad.PLATFORM.getIndex()];
                    String text = values[EnumEvaluationLoad.TEXT.getIndex()];
                    String numVersion = values[EnumEvaluationLoad.VERSION.getIndex()];
                    double score = Double.parseDouble(values[EnumEvaluationLoad.SCORE.getIndex()]);

                    CPlayer user = (CPlayer) database.getUser(pseudo);
                    CVideoGame videoGame = database.getVideoGame(videoGameName);
                    CPlatform platform = videoGame.getPlatform(platformName);

                    CEvaluation eval = new CEvaluation(user, videoGame, platform, text, numVersion, score);

                    database.addEvaluation(eval);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Save an evaluation in the csv
     * @param evaluation the evaluation to save
     */
    public void saveEvaluation(CEvaluation evaluation){
        File file = new File(EVALUATION_FILE);
        boolean exists = file.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(!exists){
                for (EnumEvaluationLoad enumValue : EnumEvaluationLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            bw.write(evaluation.getPlayer().getPseudo() + SEPARATOR + evaluation.getVideoGame().getName()
                    + SEPARATOR + evaluation.getPlatform().getName() + SEPARATOR + evaluation.getText() + SEPARATOR
                    + evaluation.getNumVersion() + SEPARATOR + evaluation.getGlobalScore());
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
