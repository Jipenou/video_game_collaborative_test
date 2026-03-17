package controller.load;

import model.CSignalement;
import model.data.CDatabase;
import model.user.CPlayer;
import model.user.CTester;
import model.videoGame.CEvaluation;
import model.videoGame.CPlatform;
import model.videoGame.CTest;
import model.videoGame.CVideoGame;

import java.io.*;
import java.time.LocalDateTime;

public class CControlLoadSignalement extends AControlLoad<CSignalement>{

    /** The path for the csv */
    private static final String SIGNALEMENT_FILE = "./data/signalements.csv";

    /** The database of the application */
    private final CDatabase database;

    public CControlLoadSignalement(CDatabase database){
        this.database = database;
    }

    @Override
    protected String getFilePath() {
        return SIGNALEMENT_FILE;
    }

    /**
     * load all the signalement
     */
    @Override
    public void load() {
        File signalementFile = new File(SIGNALEMENT_FILE);
        if(!signalementFile.exists()){
            return;
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(signalementFile));
            String line;
            file.readLine();

            while ((line = file.readLine()) != null) {

                String[] values = line.split(SEPARATOR);

                if(values.length == EnumSignalementLoad.values().length){
                    String pseudoReporter = values[EnumSignalementLoad.REPORTER.getIndex()];
                    String pseudoEvaluator = values[EnumSignalementLoad.EVALUATOR.getIndex()];
                    String videoGameName = values[EnumSignalementLoad.VIDEO_GAME.getIndex()];
                    String platformName = values[EnumSignalementLoad.PLATFORM.getIndex()];
                    LocalDateTime dateEvaluation = LocalDateTime.parse(values[EnumSignalementLoad.DATE.getIndex()]);

                    CTester reporter = (CTester) database.getUser(pseudoReporter);
                    CPlayer evaluator = (CPlayer) database.getUser(pseudoEvaluator);
                    CVideoGame videoGame = database.getVideoGame(videoGameName);
                    CPlatform platform = videoGame.getPlatform(platformName);

                    CEvaluation eval = database.getEvaluationByPseudoVideoGameAndPlatform(dateEvaluation, evaluator, videoGame, platform);
                    CSignalement signalement = new CSignalement(reporter, eval);

                    database.addSignalement(signalement);
                    reporter.signalEvaluation(eval);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Save a signalement in the signalement.csv
     * @param signalement the signalement to save
     */
    @Override
    public void save(CSignalement signalement){
        File file = new File(SIGNALEMENT_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {

            if(file.length() == 0){
                for (EnumSignalementLoad enumValue : EnumSignalementLoad.values()){
                    bw.write(enumValue.getName()+SEPARATOR);
                }
                bw.newLine();
            }

            CTester reporter = signalement.getReporter();
            CEvaluation evaluation = signalement.getEvaluation();

            bw.write(reporter.getPseudo() + SEPARATOR + evaluation.getPlayer().getPseudo() + SEPARATOR + evaluation.getVideoGame().getName()
                     + SEPARATOR + evaluation.getPlatform().getName() + SEPARATOR + evaluation.getDate());
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
