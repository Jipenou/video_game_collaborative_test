package controller.load;

import model.data.CDatabase;
import model.user.CPlayer;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import java.io.*;
import java.time.LocalDateTime;

/**
 * This class represent the CSV parser for the evaluations
 */
public class CControlLoadEvaluation extends AControlLoad<CEvaluation>{

    /** path to the csv */
    private static final String EVALUATION_FILE = "./data/evaluations.csv";
    private static final String EVALUATION_USER_VOTE_FILE = "./data/evaluations_vote.csv";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadEvaluation(CDatabase database){
        this.database = database;
    }

    /** load All the evaluations */
    @Override
    public void load() {
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
                    int nbOui = Integer.parseInt(values[EnumEvaluationLoad.NBOUI.getIndex()]);
                    int nbNon = Integer.parseInt(values[EnumEvaluationLoad.NBNON.getIndex()]);
                    LocalDateTime date = LocalDateTime.parse(values[EnumEvaluationLoad.DATE.getIndex()]);

                    CPlayer user = (CPlayer) database.getUser(pseudo);
                    CVideoGame videoGame = database.getVideoGame(videoGameName);
                    CPlatform platform = videoGame.getPlatform(platformName);

                    CEvaluation eval = new CEvaluation(date, user, videoGame, platform, text, numVersion, score);

                    eval.setUtiliteNon(nbNon);
                    eval.setUtiliteOui(nbOui);

                    database.addEvaluation(eval);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected String getFilePath() {
        return EVALUATION_FILE;
    }

    /**
     * Save an evaluation in the csv
     * @param evaluation the evaluation to save
     */
    @Override
    public void save(CEvaluation evaluation){
        File file = new File(EVALUATION_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(file.length() == 0){
                for (EnumEvaluationLoad enumValue : EnumEvaluationLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            bw.write(evaluation.getPlayer().getPseudo() + SEPARATOR + evaluation.getVideoGame().getName()
                    + SEPARATOR + evaluation.getPlatform().getName() + SEPARATOR + evaluation.getText() + SEPARATOR
                    + evaluation.getNumVersion() + SEPARATOR + evaluation.getGlobalScore() + SEPARATOR + evaluation.getUtiliteOui()
                    + SEPARATOR + evaluation.getUtiliteNon() + SEPARATOR + evaluation.getDate());
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }


    /** load All the evaluations */
    public void loadEvaluationsUsers() {
        File evalFile = new File(EVALUATION_USER_VOTE_FILE);
        if(!evalFile.exists()){
            return;
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(evalFile));
            String line;
            file.readLine();

            while ((line = file.readLine()) != null) {

                String[] values = line.split(SEPARATOR);

                if(values.length == EnumEvaluationUserLoad.values().length){
                    String pseudoVotant = values[EnumEvaluationUserLoad.PSEUDO_VOTANT.getIndex()];
                    int value = Integer.parseInt(values[EnumEvaluationUserLoad.VALUE.getIndex()]);
                    String pseudoCreateur = values[EnumEvaluationUserLoad.PSEUDO_CREATEUR.getIndex()];
                    String videoGameName = values[EnumEvaluationUserLoad.VIDEO_GAME.getIndex()];
                    String platformName = values[EnumEvaluationUserLoad.PLATFORM.getIndex()];
                    LocalDateTime date = LocalDateTime.parse(values[EnumEvaluationUserLoad.DATE.getIndex()]);

                    CPlayer userVotant = (CPlayer) database.getUser(pseudoVotant);
                    CPlayer userCreateur = (CPlayer) database.getUser(pseudoCreateur);
                    CVideoGame videoGame = database.getVideoGame(videoGameName);
                    CPlatform platform = videoGame.getPlatform(platformName);

                    CEvaluation eval = database.getEvaluationByPseudoVideoGameAndPlatform(date, userCreateur, videoGame, platform);

                    if(eval != null){
                        eval.getVotants().put(userVotant, value);
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void saveEvaluationUser(CEvaluation evaluation){
        File fileSaveUserVote = new File(EVALUATION_USER_VOTE_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileSaveUserVote,true))) {

            if(fileSaveUserVote.length() == 0){
                for (EnumEvaluationUserLoad enumValue : EnumEvaluationUserLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            for (CPlayer player : evaluation.getVotants().keySet()){
                bw.write(player.getPseudo() + SEPARATOR + evaluation.getVotants().get(player) + SEPARATOR + evaluation.getPlayer().getPseudo()
                + SEPARATOR + evaluation.getVideoGame().getName() + SEPARATOR + evaluation.getPlatform().getName() + SEPARATOR + evaluation.getDate());
                bw.newLine();
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void clearCSV(){
        try (FileWriter writer = new FileWriter(EVALUATION_FILE, false)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Error while clearing CSV file : " + EVALUATION_FILE, e);
        }
        try (FileWriter writer = new FileWriter(EVALUATION_USER_VOTE_FILE, false)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException("Error while clearing CSV file : " + EVALUATION_USER_VOTE_FILE, e);
        }
    }
}
