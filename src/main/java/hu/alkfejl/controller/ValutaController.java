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

public class ValutaController implements Initializable {
    private boolean uj = false;            // Uj szam
    private Model model = new Model();
    private String mibol = "", ertek;

    @FXML
    private Text mainDisplay;       // Kijelzett szam, ezzel szamolunk
    @FXML
    private Text littleDisplay;     // Aktualis Valuta/Mertekegyseg

    private void kiir(ActionEvent event){
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
    private void pressedSzam(ActionEvent event){
        kiir(event);
    }


    @FXML                                       // Operator lenyomas, ilyenkor tortenik az atvaltas meghivasa
    private void pressedOperator(ActionEvent event){
        String mibe = ((Button)event.getSource()).getText();
        littleDisplay.setText(mibe);
        ertek = mainDisplay.getText();
        uj = true;
        try{
            mainDisplay.setText(model.valutaAtValt(mibol, mibe, ertek));

            System.out.println(mibol +" "+mibe+" "+ ertek);
        }catch (NumberFormatException e){
            System.out.println("Hiba");
            return;
        }

        mibol = mibe;
    }

    @FXML                                       // Kepernyo RESET
    private void clear(ActionEvent event){
        littleDisplay.setText("Válasszon alapegységet");
        mainDisplay.setText("0");
        mibol="";
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
        littleDisplay.setText("Válasszon alapegységet");
    }
}
