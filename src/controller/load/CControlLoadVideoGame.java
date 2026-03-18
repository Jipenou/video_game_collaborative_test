package controller.load;

import model.data.CDatabase;
import model.videoGame.CPlatform;
import model.videoGame.CVideoGame;
import model.videoGame.rating.CPlayerRating;
import model.videoGame.rating.CTesterRating;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent the CSV parser that get video games
 */
public class CControlLoadVideoGame {

    public static final String PATH_TO_VIDEO_GAME_DATA = "./data/vg_data.csv";

    /** the separator between each value in the csv */
    public static final String SEPARATOR = ",";

    /** the file to read */
    private final String file;

    private final CDatabase database;

    /** A map with the format : <name of the column, index of the column> that repertory
     * the column names with their index
     */
    private final Map<String, Integer> columnName;

    public CControlLoadVideoGame(CDatabase database){
        this.database = database;
        this.file = PATH_TO_VIDEO_GAME_DATA;
        columnName = new HashMap<>();
    }

    /**
     * Read a csv file to get all the video games inside
     */
    public void loadVideoGames(){
        try{
            BufferedReader file = new BufferedReader(new FileReader(this.file));
            String line;

            // line 1 : colum names
            line = file.readLine();
            if(line == null){
                return;
            }
            readFirstLine(line);

            // read other lines
            while((line = file.readLine()) != null){
                if(line.isEmpty()){
                    continue;
                }
                String[] values = parseCsvLine(line);
                CVideoGame actualVideoGame = createVideoGame(values);

                if (actualVideoGame != null && actualVideoGame.getName() != null){

                    // if video game already present in database, we only update his platforms by adding one
                    if(database.isVideoGameCreated(actualVideoGame.getName())){
                        // the actual platform
                        CPlatform platform = actualVideoGame.getPlatforms().getFirst();

                        //the video game in the database
                        CVideoGame databaseVideoGame = database.getVideoGame(actualVideoGame.getName());
                        databaseVideoGame.addPlatform(platform);

                        database.addVideoGame(databaseVideoGame);
                    }
                    else{
                        //we add normally our game to the database
                        database.addVideoGame(actualVideoGame);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading CSV file : " + file, e);
        }
    }

    /**
     * Read the first line of the csv that correspond to the name of the column in order
     * @param line the first line to parse
     */
    public void readFirstLine(String line){
        String[] values = line.split(SEPARATOR);
        for(int i = 0; i< values.length; i++){
            if(!values[i].isEmpty()){
                columnName.put(values[i].trim(), i);
            }
        }
    }

    /**
     * create a video game with the values of a line
     * @param lineValues the values of a line
     */
    public CVideoGame createVideoGame(String[] lineValues){
        Map<EnumVideoGameLoad, String> columnValues = new HashMap<>();

        // get all the value that we need
        for(EnumVideoGameLoad enumValue : EnumVideoGameLoad.values()){
            int index = columnName.get(enumValue.getName());
            String value = null;
            if(index < lineValues.length){
                value = lineValues[index];
            }
            columnValues.put(enumValue, value);
        }

        //Create the video game
        String name;
        String category = null;
        String editor = null;
        Character rating = null;

        String nameStr = columnValues.get(EnumVideoGameLoad.NAME);
        String categoryStr = columnValues.get(EnumVideoGameLoad.CATEGORY);
        String editorStr = columnValues.get(EnumVideoGameLoad.EDITOR);
        String ratingStr = columnValues.get(EnumVideoGameLoad.RATING);

        if(nameStr == null || nameStr.trim().isEmpty()){
            return null;
        }
        name = nameStr;

        if(categoryStr != null && !categoryStr.trim().isEmpty()){
            category = categoryStr;
        }

        if(editorStr != null && !editorStr.trim().isEmpty()){
            editor = editorStr;
        }

        if(ratingStr != null && !ratingStr.trim().isEmpty()){
            rating = ratingStr.charAt(0);
        }

        CVideoGame videoGame = new CVideoGame(name, category, editor, rating);

        //Creation of the platform
        CPlatform platform = createPlatform(columnValues);

        if(platform != null){
            videoGame.addPlatform(platform);
        }

        return videoGame;
    }

    /**
     * Create the platform linked to the video game
     * @param columnValues values of the line
     * @return a platform
     */
    public CPlatform createPlatform(Map<EnumVideoGameLoad, String> columnValues){
        Integer releaseYear = null;
        String developer = null;
        Float globalSales = null;
        String platformName = null;

        String releaseYearStr = columnValues.get(EnumVideoGameLoad.YEAR_OF_RELEASE);
        String developerStr = columnValues.get(EnumVideoGameLoad.DEVELOPER);
        String globalSalesStr = columnValues.get(EnumVideoGameLoad.GLOBAL_SALES);
        String platformStr = columnValues.get(EnumVideoGameLoad.PLATFORM);

        if(releaseYearStr != null && !releaseYearStr.trim().isEmpty()){
            try{
                releaseYear = Integer.valueOf(releaseYearStr);
            }catch (NumberFormatException ignore){}
        }

        if(developerStr != null && !developerStr.trim().isEmpty()){
            developer = developerStr;
        }

        if(globalSalesStr != null && !globalSalesStr.trim().isEmpty()){
            try{
                globalSales = Float.parseFloat(globalSalesStr);
            }catch (NumberFormatException ignore){}
        }

        if(platformStr != null && !platformStr.trim().isEmpty()){
            platformName = platformStr;
        }

        CTesterRating testerRating = createTesterRating(columnValues);
        CPlayerRating playerRating = createPlayerRating(columnValues);

        return new CPlatform(platformName, releaseYear, developer, globalSales, testerRating, playerRating);
    }

    /**
     *
     * @param columnValue the value of the line
     * @return a playerRating
     */
    public CPlayerRating createPlayerRating(Map<EnumVideoGameLoad, String> columnValue){
        Integer nbPlayerRating = null;
        Float playerRatingValue = null;

        String userCount = columnValue.get(EnumVideoGameLoad.USER_COUNT);
        String userScore = columnValue.get(EnumVideoGameLoad.USER_SCORE);

        if(userCount != null && !userCount.trim().isEmpty()){
            try{
                nbPlayerRating = Integer.valueOf(userCount);
            }catch (NumberFormatException ignore) {}
        }

        if(userScore != null && !userScore.trim().isEmpty()){
            try{
                playerRatingValue = Float.parseFloat(userScore);
            }catch (NumberFormatException ignore) {}
        }

        if(nbPlayerRating != null && playerRatingValue != null){
            return new CPlayerRating(nbPlayerRating, playerRatingValue);
        }
        return null;
    }

    /**
     *
     * @param columnValue the value of the line
     * @return a testerRating
     */
    public CTesterRating createTesterRating(Map<EnumVideoGameLoad, String> columnValue){
        Integer nbTesterRating = null;
        Float testerRatingValue = null;

        String criticCount = columnValue.get(EnumVideoGameLoad.CRITIC_COUNT);
        String criticScore = columnValue.get(EnumVideoGameLoad.CRITIC_SCORE);

        if(criticCount != null && !criticCount.trim().isEmpty()){
            try{
                nbTesterRating = Integer.valueOf(criticCount);
            } catch (NumberFormatException ignore) {}
        }

        if(criticScore != null && !criticScore.trim().isEmpty()){
            try{
                testerRatingValue = Float.parseFloat(criticScore);
            } catch (NumberFormatException ignore) {}
        }

        if(nbTesterRating != null && testerRatingValue != null){
            return new CTesterRating(nbTesterRating, testerRatingValue);
        }
        return null;
    }

    private String[] parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        values.add(current.toString().trim());

        return values.toArray(new String[0]);
    }
}