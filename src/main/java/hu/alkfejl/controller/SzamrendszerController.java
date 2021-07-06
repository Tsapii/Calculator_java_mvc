package hu.alkfejl.controller;

import hu.alkfejl.model.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import javax.swing.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SzamrendszerController implements Initializable {
    private boolean uj = false;             // Uj szam figyelese
    private Model model = new Model();
    private String mibol = "DEC", ertek;    // Ez lenyeges, hisz ez alapjan dontjuk el, hogy milyen Szamrendszerbol fogunk valtani

    @FXML
    private Text mainDisplay;       // Kijelzett szam, ezzel szamolunk
    @FXML
    private Text littleDisplay;     // Kicsi kijelzo, ebben az esetben az eppen aktualis szamrendszert irja ki

    private void kiir(ActionEvent event){       // Mint a Calculator resznel, csak itt kulon kell ellenorizni a bemeneteket, ezert effektivebb volt kiszervezni egy fgv-be
        if(uj){
            mainDisplay.setText("");
            uj = false;
        }
        if("0".equals(mainDisplay.getText())){
            mainDisplay.setText("");
        }
        String value = ((Button)event.getSource()).getText();
        mainDisplay.setText(mainDisplay.getText() + value);
    }

    @FXML
    private void pressedBin(ActionEvent event){     // Binaris szamok 0-1
        kiir(event);
    }
    @FXML
    private void pressedOct(ActionEvent event){     // Oktalis szamok, a lenyege, hogy BIN modban ne lehessen 1-tol nagyobb szamokat irni 2-7
        if(mibol.equals("BIN")) return;
        kiir(event);
    }
    @FXML
    private void pressedSzam(ActionEvent event){    // Elozo logika alapjan ezeket csak akkor lehet lenyomni, ha DEC, vagy HEX modban vagyunk 8-9
        if(mibol.equals("BIN") || mibol.equals("OCT")) return;
        kiir(event);
    }

    @FXML
    private void pressedBetu(ActionEvent event){    // Betut csak HEX uzemmonban tudunk hasznalni, egyebkent felesleges A-F
        if(!mibol.equals("HEX")) return;
        kiir(event);
    }

    @FXML                                       // Operator lenyomas, itt tortenik az atvaltas a Szamrendszerek kozott
    private void pressedOperator(ActionEvent event){
        String mibe = ((Button)event.getSource()).getText();
        littleDisplay.setText(mibe);
        ertek = mainDisplay.getText();
        uj = true;
        try{
            mainDisplay.setText(model.atValt(mibol, mibe, ertek));

            System.out.println(mibol +" "+mibe+" "+ ertek); // Self check
        }catch (NumberFormatException e){
            System.out.println("Hiba: rossz formatumu szam kerult a rendszerbe.");
            return;
        }
        mibol = mibe;
    }

    @FXML                                       // Kepernyo RESET
    private void clear(ActionEvent event){
        littleDisplay.setText("DEC");
        mainDisplay.setText("0");
        mibol="DEC";
    }

    @FXML
    private void Back(ActionEvent event){
        if(!uj){
            String temp = mainDisplay.getText();
            if(temp.length()-1 > 0){
                mainDisplay.setText(temp.substring(0,temp.length()-1));
            }else
                mainDisplay.setText("0");
        } else {
            return;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainDisplay.setText("0");
        littleDisplay.setText("DEC");
    }
}
