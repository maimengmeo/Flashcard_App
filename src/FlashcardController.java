import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class FlashcardController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button flashCard;

    @FXML
    private Button yourFlashCardDisplay;
    
    @FXML
    private Button library;
    
    @FXML
    private Button makeYourOwn;
    
    @FXML
    private Button next;
    
    @FXML
    private Button previous;
    
    @FXML
    private Label questionNumber;

    @FXML
    private Button yourFlashcard;

    @FXML
    private TextField inputAnswer;

    @FXML
    private TextField inputQuestion;

    @FXML
    private Button submitQuestion;

    @FXML
    private TextField showFilePathArea;

    @FXML
    private Button addToExistSetButton;

    @FXML
    private Button makeSet;

    @FXML
    private Button selectStudySet;

    @FXML
    private Button newStudySet;

    @FXML
    private Button makeYourFlashcard;

    @FXML
    private Button yourNextQuestion;

    @FXML
    private Button yourPreviousQuestion;
    
    
    QuestionFileManager fileManager = QuestionFileManager.getInstance();
    int index = 0;
    private final String QUESTIONLIBRARY = "src/JavaQuestion.txt";

    public void initialize() throws FileNotFoundException {
        if (flashCard != null) {
            fileManager.loadQuestionFromFile(QUESTIONLIBRARY);
            flashCard.setText(fileManager.getQuestions().get(index).getQuestion());
            questionNumber.setText("1/" + fileManager.getQuestions().size());
        }
        if (yourFlashCardDisplay != null) {
            yourFlashCardDisplay.setText(fileManager.getQuestions().get(index).getQuestion());
            questionNumber.setText("1/" + fileManager.getQuestions().size());
        }
    }

    public void flashcardDisplay(Button button) throws FileNotFoundException {
        String answer = fileManager.getQuestions().get(index).getAnswer();
        String question = fileManager.getQuestions().get(index).getQuestion();
        if (button.getText().equals(answer)) {
            button.setText(question);
        } else {
            button.setText(answer);
        }
    }

    public void nextQuestion (Button button) {
        if (index + 1 >= fileManager.getQuestions().size()) {
            index = 0;
        }
        else {
            index++;
        }
        button.setText(fileManager.getQuestions().get(index).getQuestion());
        int size = fileManager.getQuestions().size();
        questionNumber.setText(index+1 + "/" + size);

    }

    public void previousQuestion (Button button) {
        if (index - 1 < 0) {
            index = fileManager.getQuestions().size() -1;
        }
        else {
            index--;
        }
        button.setText(fileManager.getQuestions().get(index).getQuestion());
        int size = fileManager.getQuestions().size();
        questionNumber.setText(index+1 + "/" + size);
    }

    //Library scene---------------------------------------------------------------
    
    @FXML
    void handleFlashcard(ActionEvent event) throws FileNotFoundException {
        flashcardDisplay(flashCard);
        
    }
    
    @FXML
    void handleNextQuestionLibrary(ActionEvent event) {
        nextQuestion(flashCard);
    }
    
    @FXML
    void handlePreviousQuestionLibrary(ActionEvent event) {
        previousQuestion(flashCard);
    }
       
    //Adding question/ Make Your Own scene----------------------------------------------------------------

    @FXML
    void submitQuestionClicked(ActionEvent event) throws IOException {
        File file = new File(fileManager.getCustomFilePath());
        fileManager.writeQuestionToFile(file, inputQuestion.getText(), inputAnswer.getText());
        inputQuestion.setText("");
        inputAnswer.setText("");
    }

    //Select file to write/ File location scene----------------------------------------------------------------------
    
    @FXML
    void handleFileLocation(ActionEvent event) throws IOException {
        fileManager.addToExistSet(addToExistSetButton);
        showFilePathArea.setText(fileManager.getCustomFilePath());
    }
    
    @FXML
    void createNewStudySet(ActionEvent event) throws IOException {
        fileManager.createNewSet(newStudySet);
        showFilePathArea.setText(fileManager.getCustomFilePath());
    }
    
    //Select file to read from/ Select Study Set scene----------------------------------------------------------------------
    
    @FXML
    void selectStudySetClicked(ActionEvent event) throws FileNotFoundException {
        fileManager.chooseFileToLoad(selectStudySet);       
        showFilePathArea.setText(fileManager.getCustomFilePath());
        fileManager.loadQuestionFromFile(fileManager.getCustomFilePath());
    }
  
    //Your Flashcard scene----------------------------------------------------------------------

    @FXML
    void handleYourFlashcard(ActionEvent event) throws FileNotFoundException {
        flashcardDisplay(yourFlashCardDisplay);
    }
    
    @FXML
    void handleYourNextQuestion(ActionEvent event) {
        nextQuestion(yourFlashCardDisplay);
    }
    
    @FXML
    void handleYourPreviousQuestion(ActionEvent event) {
        previousQuestion(yourFlashCardDisplay);
    }
    
    //Switch scene----------------------------------------------------------------------

    @FXML
    void makeSetClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MakeYourOwnBuild.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void switchToMakeYourOwn(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("FileLocationBuild.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToLibrary(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("FlashcardBuild.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToSelectStudySet(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("SelectStudySetBuild.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToYourFlashcard(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("YourFlashcardBuild.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
