import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class QuestionFileManager {
    
    private String customFilePath = "";
    private ArrayList <Question> questions = new ArrayList<Question>();
    private int index = 0;
    
    private static QuestionFileManager fileManager = null;
    private QuestionFileManager(){}
    public static QuestionFileManager getInstance() {
        if(fileManager == null) {
            fileManager = new QuestionFileManager();
        }

        return fileManager;
    }
    
    public void loadQuestionFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        questions = new ArrayList<Question>();
        try(Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                System.out.println("Read from file: " + line);
                String [] questionString = line.split("###");
                Question javaQuestion = new Question(questionString[0], questionString[1]);
                questions.add(javaQuestion);
            }
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            throw e;
        }
    }

    public ArrayList<Question> getQuestions() {        
        return questions;
    }


    public void writeQuestionToFile (File file, String questionToSave, String answerToSave) {
        if (file.exists()) {
            try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));) {            
                printWriter.println(questionToSave + "###" + answerToSave);
            } catch (IOException e) {
                System.out.println("File input fail");
            }
        } else {
            try (PrintWriter printWriter = new PrintWriter(file)) {            
                printWriter.println(questionToSave + "###" + answerToSave);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }
    }

    // set file location
    public void addToExistSet(Button button) throws IOException {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog((button.getScene().getWindow()));
        customFilePath = file.getPath();
        System.out.println(customFilePath);
    }
    
    public void createNewSet (Button button) throws IOException {
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(button.getScene().getWindow());
        file.createNewFile();
        customFilePath = file.getPath();
    }

    //choose file to load

    public void chooseFileToLoad(Button button) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(button.getScene().getWindow());
        customFilePath = file.getPath();
    }
    
    public String getCustomFilePath() {
        return customFilePath;
    }
    public void setCustomFilePath(String customFilePath) {
        this.customFilePath = customFilePath;
    }
}
