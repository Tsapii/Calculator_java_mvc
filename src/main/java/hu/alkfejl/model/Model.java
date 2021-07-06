package hu.alkfejl.model;

import java.net.StandardSocketOptions;

import static java.lang.Math.addExact;
import static java.lang.Math.pow;

public class Model {
    // ----------------------- Szamologep ----------------------
// Alapmuveletek + x^y
    public double Calculate(double number1, double number2, String operator){
        switch (operator){
            case"+":
                return number1 + number2;
            case"-":
                return number1 - number2;
            case"*":
                return number1 * number2;
            case"/":
                if(number2 == 0){
                    return 0;
                }
                return number1 / number2;
            case"%":
                return number1 % number2;
            case"x^y":
                return pow(number1,number2);
        }
        System.out.println("Ismeretlen operator: "+ operator);
        return 0;
    }

// Trigonometriai muveletek
    public double trig(double value, String method){
        double radian = value * Math.PI/180;
        switch (method){
            case"sin":
                return Math.sin(radian);
            case"cos":
                return Math.cos(radian);
            case"tan":
                return Math.tan(radian);
        }
        return 0;
    }

// Egyszerubb muveletek
    public double sqrt(double number1){
        return pow(number1, 0.5);
    }
    public double square(double number1){
        return pow(number1, 2);
    }
    public double reciprok(double number1){
        return 1/number1;
    }


// Ellenorzi, hogy egesz-e a szamunk, ha az akkor int-kent, ha nem akkor pedig double-kent ter vissza
    public static String parse(double num) {
        if((int) num == num) return Integer.toString((int) num);
        return String.valueOf((double)Math.round(num * 10000000d) / 10000000d);
    }

    // ----------------------- Szamrendszer ----------------------
// A legegyszerubb modja a szamrendszerek kozti atvaltasnak, ha eloszor DECimalisba valtunk
    private String valamiToDEC(String mibol, String ertek){
        switch(mibol){
            case"BIN":
                return String.valueOf(Integer.parseInt(ertek,2));
            case"HEX":
                return String.valueOf(Integer.parseInt(ertek,16));
            case"OCT":
                return String.valueOf(Integer.parseInt(ertek,8));
        }
        return ertek;
    }
// Majd pedig onnan konnyen birunk valtani barmelyikbe
    private String DECtoValami(String mibe, String ertek){
        switch(mibe){
            case"BIN":
                return Integer.toBinaryString(Integer.parseInt(ertek));
            case"HEX":
                return Integer.toHexString(Integer.parseInt(ertek)).toUpperCase();
            case"OCT":
                return Integer.toOctalString(Integer.parseInt(ertek));
        }
        return ertek;
    }

    public String atValt(String mibol, String mibe, String ertek){
        String decimal = valamiToDEC(mibol,ertek);
        return DECtoValami(mibe, decimal);
    }

    //------------------- Valuta es mertekegyseg ----------------------
// A eloszor KG-ba valtunk
    private String valamiToKG(String mibol, String ertek){
        switch(mibol){
            case"mg":
                return String.valueOf(parse(Double.parseDouble(ertek)/1000000)); //mg->kg 1.000.000
            case"g":
                return String.valueOf(parse(Double.parseDouble(ertek)/1000)); //g->kg 1.000
            case"dkg":
                return String.valueOf(parse(Double.parseDouble(ertek)/10)); //dkg->kg 10
            case"kg":
                return String.valueOf(parse(Double.parseDouble(ertek))); //kg->kg 1
            case"t":
                return String.valueOf(parse(Double.parseDouble(ertek)*1000)); //T->kg 1000
        }
        return ertek;
    }
// Majd KG-bol amibe szeretnenk
    private String KGtoValami(String mibe, String ertek){
        switch(mibe){
            case"mg":
                return String.valueOf(parse(Double.parseDouble(ertek)*1000000)); //mg->kg 1.000.000
            case"g":
                return String.valueOf(parse(Double.parseDouble(ertek)*1000)); //g->kg 1.000
            case"dkg":
                return String.valueOf(parse(Double.parseDouble(ertek)*10)); //dkg->kg 10
            case"kg":
                return String.valueOf(parse(Double.parseDouble(ertek))); //kg->kg 1
            case"t":
                return String.valueOf(parse(Double.parseDouble(ertek)/1000)); //T->kg 1000
        }
        return ertek;
    }

// Eloszor atvaltjuk az adott ertekunket Ft-ba
    private String valamiToFt(String mibol, String ertek){
        switch(mibol){
            case"$":
                return String.valueOf(parse(Double.parseDouble(ertek)*328));
            case"€":
                return String.valueOf(parse(Double.parseDouble(ertek)*355));
        }
        return ertek;
    }
// Majd pedig amibe szeretnenk
    private String FtToValami(String mibe, String ertek){
        switch(mibe){
            case"$":
                return String.valueOf(parse(Double.parseDouble(ertek)/328));
            case"€":
                return String.valueOf(parse(Double.parseDouble(ertek)/355));
        }
        return ertek;
    }

// F-C es C-F kozotti atvaltas
    private String temperature(String mibol, String mibe, String ertek){
        if(mibol.equals("F") && mibe.equals("Celsius")){
            return String.valueOf(parse((Double.parseDouble(ertek)-32)/1.8));
        }
        if(mibol.equals("Celsius") && mibe.equals("F")){
            return String.valueOf(parse(Double.parseDouble(ertek)*1.8+32));
        }
        return ertek;
    }
// Itt donti el, hogy mik kozott tortenjen a valtas
    public String valutaAtValt(String mibol, String mibe, String ertek){
        if(mibe.equals("mg") || mibe.equals("g") || mibe.equals("dkg") || mibe.equals("kg") || mibe.equals("t")){
            String temp = valamiToKG(mibol, ertek);
            return KGtoValami(mibe, temp);
        } else if(mibe.equals("Ft") || mibe.equals("$") || mibe.equals("€")){
            String temp = valamiToFt(mibol, ertek);
            return FtToValami(mibe, temp);
        } else if(mibe.equals("F") || mibe.equals("Celsius")){
            return temperature(mibol, mibe, ertek);
        }


        return ertek;
    }
}
