package sample.FileManagement;

import java.io.*;
import java.nio.Buffer;

public class FileManager {

    //Attributes

    private String SCORE_TXT_DIR;
    private static FileManager fileManager;
    private String fileName;
    private FileWriter writer;
    private FileReader reader;
    private BufferedWriter bf;

    //Constructor

    public FileManager(String filename) {
        try {
            writer = new FileWriter(filename, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.fileName = filename;
        //createFileManager();
    }


    //Methods

    public void writeToFile (String highscoreList) {
        try{

            //bf = new BufferedWriter(writer);
            writer.write(highscoreList + "\n");
            //bf.newLine();
            //fbw.close();
            //writer.close();
            if(fileName.equals("Scores.txt"))
                writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        //writer.append(highscoreList);
        //writer.println(highscoreList);

    }
    public void closeWriter(){
        try{
            //bf.close();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String readFromFile() {
        int score;
        String returnSt ="";

        try {
            while ((score = reader.read()) != -1) {
                returnSt += (char)score;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnSt;

    }

    /*public static FileManager createFileManager() {
        if(fileManager == null)
            fileManager = new FileManager();
        return fileManager;
    }*/

    public FileManager getFileManager() {
        return fileManager;
    }

}
