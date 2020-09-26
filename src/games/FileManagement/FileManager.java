package games.FileManagement;

import java.io.*;

public class FileManager {

    //Attributes
    private FileWriter writer;
    private FileReader reader;

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
        StringBuilder returnSt = new StringBuilder();

        try {
            while ((score = reader.read()) != -1) {
                returnSt.append((char) score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnSt.toString();
    }
}
