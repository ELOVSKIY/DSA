package application;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;

public class Controller {

    @FXML
    TextField pValue;

    @FXML
    TextField qValue;

    @FXML
    TextField hValue;

    @FXML
    TextField xValue;

    @FXML
    TextField kValue;

    @FXML
    TextField rValue;

    @FXML
    TextField sValue;

    @FXML
    TextField gValue;

    @FXML
    TextField yValue;

    @FXML
    TextField hashValue;

    @FXML
    Text filePathText;

    private String filePath;

    @FXML
    void choseFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(filePathText.getScene().getWindow());
        if (file != null) {
            filePath = file.getPath();
            filePathText.setText(file.getName());
        }
    }
}
