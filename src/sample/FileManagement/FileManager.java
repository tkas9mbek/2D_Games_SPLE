package sample.FileManagement;

import java.io.*;

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
    }

    public void closeWriter(){
        try{
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

    public FileManager getFileManager() {
        return fileManager;
    }

}
