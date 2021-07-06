package hu.alkfejl.controller;

import hu.alkfejl.model.Model;
import hu.alkfejl.model.MuveletDAO;
import hu.alkfejl.model.MuveletDAODBImp;
import hu.alkfejl.model.MuveletDAOMemImp;
import hu.alkfejl.model.bean.Muvelet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {
    private String operator = "";          // Operator
    private boolean uj = false;            // Uj szam beirasat figyeli
    private double number1 = 0, output = 0, memoria=0;
    private Model model = new Model();
    //   ---DAO---
    private MuveletDAO dao = new MuveletDAOMemImp();
    private MuveletDAO daodb = new MuveletDAODBImp();
    // ---DAODone---

    @FXML
    private Text mainDisplay;       // Kijelzett szam, ezzel szamolunk
    @FXML
    private Text littleDisplay;     // Kicsi kijelzo, mellekadatok

    @FXML                           // Szamot tartalmazo billentyu lenyomasa
    private void pressedNumber(ActionEvent event){
        if(uj){
            mainDisplay.setText("");
            uj = false;

            if(operator.isEmpty()){
                littleDisplay.setText("");
                // = utan, ha szamot nyomunk resetelodik, ez van itt

            }
        }
        if("0".equals(mainDisplay.getText())){
            mainDisplay.setText("");
        }
        String value = ((Button)event.getSource()).getText();
        mainDisplay.setText(mainDisplay.getText() + value);
    }

    @FXML                                       // Operator lenyomas
    private void pressedOperator(ActionEvent event){
        String value = ((Button)event.getSource()).getText();
        littleDisplay.setText(littleDisplay.getText() + mainDisplay.getText() + " " + value + " ");
        uj = true;

        if(!"=".equals(value)){                 // Ha megnyomtunk egy operatort, es az nem "=", akkor allitsa be az operatort a lenyomottra

            operator = value;
            number1 = Double.parseDouble(mainDisplay.getText());
        } else {
            if(operator.isEmpty())              // Ha megnyomtuk az "="-t, de nincs operator, akkor terjen vissza. Ne ertekelje meg ki nekunk.
                return;
                                                // Kiertekeles
            output = model.Calculate(number1, Double.parseDouble(mainDisplay.getText()), operator); // number1 number2 operator

                  //Itt donti el, hogy Int vagy Double alakban irja-e ki, a parse metodussal, ez azert lenyeges, hogy ha egesz a szam, akkor ne legyen ilyen: 420.0
            // ADATBAZISBA MENTES
            System.out.println(number1+operator+Double.parseDouble(mainDisplay.getText())+" = "+output);
            dao.addMuvelet(new Muvelet(number1,Double.parseDouble(mainDisplay.getText()),operator,output));
            daodb.addMuvelet(new Muvelet(number1,Double.parseDouble(mainDisplay.getText()),operator,output));
            mainDisplay.setText(model.parse(output));
            operator = "";
            output=0;
        }
    }
    @FXML
    private void pressedPoint(ActionEvent event){       // Tizedespont
        String value = ((Button)event.getSource()).getText();
        int szabad = 0;
        for(String s : mainDisplay.getText().split("")){
            if(".".equals(s)){                          // Vegignezi a Display-t és nézi hogy, van-e mar benne ".", ha van akkor ignoralja a lenyomast
                szabad ++;
            }
        }
        if(szabad < 1) {
            mainDisplay.setText(mainDisplay.getText() + value);
            szabad = 0;
            return;
        }
        else
            return;
    }
    // ---1 operandusu muveletek kezelese---
    @FXML
    private void elojelCsere(ActionEvent event){
        double temp = Double.parseDouble(mainDisplay.getText())*-1;
        number1 = Double.parseDouble(mainDisplay.getText());
        mainDisplay.setText(model.parse(temp));
        dao.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
        daodb.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
    }
    @FXML
    private void pressedSqrt(ActionEvent event){
        double temp = Double.parseDouble(mainDisplay.getText());
        number1 = Double.parseDouble(mainDisplay.getText());
        mainDisplay.setText(model.parse(model.sqrt(temp)));
        dao.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
        daodb.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
    }
    @FXML
    private void pressedSquare(ActionEvent event){
        double temp = Double.parseDouble(mainDisplay.getText());
        number1 = Double.parseDouble(mainDisplay.getText());
        mainDisplay.setText(model.parse(model.square(temp)));
        dao.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
        daodb.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
    }
    @FXML
    private void pressedReciprok(ActionEvent event){
        double temp = Double.parseDouble(mainDisplay.getText());
        number1 = Double.parseDouble(mainDisplay.getText());
        mainDisplay.setText(model.parse(model.reciprok(temp)));
        dao.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
        daodb.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
    }

    @FXML
    private void pressedTrig(ActionEvent event){
        number1 = Double.parseDouble(mainDisplay.getText());
        String method = ((Button)event.getSource()).getText();       // sin,cos,tan
        double value = Double.parseDouble(mainDisplay.getText());    // Az ertek fokban, ez lenyeges mert a sin,cos.. fgv-hez radianban kell megadni, de azt a Model-ban megcsinalja nekunk
        mainDisplay.setText(String.valueOf(model.parse(model.trig(value, method))));
        dao.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
        daodb.addMuvelet(new Muvelet(number1,((Button)event.getSource()).getText(),Double.parseDouble(mainDisplay.getText())));
    }

    @FXML
    private void pressedBack(ActionEvent event){                    // Ha rosszul irunk be valamit, akkor ezzel tudjuk javitani
        if(!uj){
            String temp = mainDisplay.getText();
            if(temp.length()-1 > 0){                                // Fontos, hogy ne legyen 0-tol kisebb a Stringunk
                mainDisplay.setText(temp.substring(0,temp.length()-1));
            }else
                mainDisplay.setText("0");
        } else {
            return;
        }
    }

    @FXML                                       // Kepernyo RESET
    private void clear(ActionEvent event){
        littleDisplay.setText("");
        mainDisplay.setText("0");
    }

    @Override                                   // Inicializalas
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Orvosolni, hogy a sok szamot ne egymas ala irja
        mainDisplay.setText("0");
        littleDisplay.setText("");
    }
    // ---Menu elemek---

    @FXML                                       // Uj Stage-t nyit a Szamrendszeres atvaltasokhoz
    public void menuSzamrendszer(){
        Stage szrendszerStage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/Szamrendszer.fxml"));

            Scene scene = new Scene(root);
            szrendszerStage.setScene(scene);
            szrendszerStage.setTitle("Számrendszer váltás");
            szrendszerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML                                       // Uj Stage-t nyit a Valuta/Mertekegyseg atvaltasokhoz
    public void menuValuta(){
        Stage valutaStage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/Valuta.fxml"));

            Scene scene = new Scene(root);
            valutaStage.setScene(scene);
            valutaStage.setTitle("Valuta/Mértékegység váltás");
            valutaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ---Muveletek tarolasa memoriaba
    @FXML
    public void fromStart(){
        System.out.println(dao.listMuvelet());
    }
    // ---Adatbazisba
    @FXML
    public void fromAll(){
        System.out.println("-----");
        System.out.println(daodb.listMuvelet());
        System.out.println("-----");
    }
    @FXML
    public void deleteMuveletek(){
        dao.deleteDB();
        daodb.deleteDB();
    }
    @FXML
    public void load(ActionEvent event){
        int temp = 0;
        Muvelet m = new Muvelet();
        try{
            temp = Integer.parseInt(mainDisplay.getText());
            m=daodb.loadByID(temp);
            mainDisplay.setText(String.valueOf(m.getResult()));
            littleDisplay.setText(String.valueOf(m.getNumber1())+" "+m.getOperator()+" "+String.valueOf(m.getNumber2())+" =");
            System.out.println(m);
        }catch(NumberFormatException e){
            System.out.println("Nem egesz szamot adott meg");
            mainDisplay.setText("0");
            littleDisplay.setText("");
        }


    }
    // -----------------------

    // ---Eredmeny tarolasa memoriaba---
    @FXML
    public void resultSave(){
        memoria = Double.parseDouble(mainDisplay.getText());
    }
    @FXML
    public void resultLoad(){
        mainDisplay.setText(String.valueOf(memoria));
    }
    // ------------------------------
}
// font="$billFont", ezt majd ra kell kuldeni a betukre