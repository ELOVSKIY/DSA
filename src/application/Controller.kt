package application

import application.model.DSA
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.TextField
import javafx.scene.text.Text
import javafx.stage.FileChooser

import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths

class Controller {

    @FXML
    lateinit var pValue: TextField

    @FXML
    lateinit var qValue: TextField

    @FXML
    lateinit var hValue: TextField

    @FXML
    lateinit var xValue: TextField

    @FXML
    lateinit var kValue: TextField

    @FXML
    lateinit var rValue: TextField

    @FXML
    lateinit var sValue: TextField

    @FXML
    lateinit var gValue: TextField

    @FXML
    lateinit var yValue: TextField

    @FXML
    lateinit var hashValue: TextField


    @FXML
    lateinit var wValue: TextField

    @FXML
    lateinit var u1Value: TextField

    @FXML
    lateinit var u2Value: TextField

    @FXML
    lateinit var vValue: TextField

    @FXML
    lateinit var filePathText: Text

    private var filePath: String? = null

    @FXML
    fun signFile() {
        if (filePath != null) {
            try {
                val fileBytes = Files.readAllBytes(Paths.get(filePath)).toMutableList()
                val P = pValue.text.toString().toBigInteger()
                val Q = qValue.text.toString().toBigInteger()
                val h = hValue.text.toString().toBigInteger()
                val X = xValue.text.toString().toBigInteger()
                val K = kValue.text.toString().toBigInteger()
                val dsa = DSA(P, Q, fileBytes)
                hashValue.text = dsa.hash.toString()
                dsa.sign(X, K, h)

                val signFile = File(File(filePath).parent + "\\sign.txt")
                val writer = FileWriter(signFile)
                writer.append("R = ")
                writer.append(dsa.R.toString())
                writer.append("\nS = ")
                writer.append(dsa.S.toString())
                writer.append("\nG = ")
                writer.append(dsa.G.toString())
                writer.append("\nY = ")
                writer.append(dsa.Y.toString())
                writer.close()
            }catch (e: Exception){
                alert(e.toString())
            }
        }else{
            alert("Choose file.")
        }
    }

    @FXML
    fun checkSign(){
        if (filePath != null) {
            try {
                val fileBytes = Files.readAllBytes(Paths.get(filePath)).toMutableList()
                val P = pValue.text.toString().toBigInteger()
                val Q = qValue.text.toString().toBigInteger()
                val R = rValue.text.toString().toBigInteger()
                val S = sValue.text.toString().toBigInteger()
                val G = gValue.text.toString().toBigInteger()
                val Y = yValue.text.toString().toBigInteger()
                val dsa = DSA(P, Q, fileBytes)
                hashValue.text = dsa.hash.toString()
                val answer = dsa.checkSign(R, S, G, Y)

                wValue.text = dsa.W.toString()
                u1Value.text = dsa.U1.toString()
                u2Value.text = dsa.U2.toString()
                vValue.text = dsa.V.toString()



                if (answer){
                    alert("Valid signature.")
                }else{
                    alert("Invalid signature.")
                }


            }catch (e: Exception){
                alert(e.toString())
            }
        }else{
            alert("Choose file.")
        }
    }

    @FXML
    fun chooseFile() {
        val fileChooser = FileChooser()
        val file = fileChooser.showOpenDialog(filePathText!!.scene.window)
        if (file != null) {
            filePath = file.path
            filePathText!!.text = file.name
        }
    }

    fun alert(msg: String){
        val alert = Alert(Alert.AlertType.WARNING)
        alert.headerText = msg;
        alert.showAndWait();
    }
}
