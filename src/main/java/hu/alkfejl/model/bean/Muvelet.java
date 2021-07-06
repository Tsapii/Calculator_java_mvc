package hu.alkfejl.model.bean;

import java.io.Serializable;

public class Muvelet implements Serializable{

        private static final long serialVersionUID = -8433714437907036328L;
        private int id;
        private double number1;
        private double number2 = 0;
        private String operator;
        private double result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "("+id+"): " + number1 + " " + operator + " " + number2 + " = " + result +"\n";
    }

    public Muvelet() {
        }

        // 2 operandusu muveletekhez
        public Muvelet(double number1, double number2, String operator, double result) {
            this.number1 = number1;
            this.number2 = number2;
            this.operator = operator;
            this.result = result;
        }
        public Muvelet(double number1, String operator, double result) {
            this.number1 = number1;
            this.operator = operator;
            this.result = result;
         }

        public double getNumber1() {
            return number1;
        }

        public void setNumber1(double number1) {
            this.number1 = number1;
        }

        public double getNumber2() {
            return number2;
        }

        public void setNumber2(double number2) {
            this.number2 = number2;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public double getResult() {
            return result;
        }

        public void setResult(double result) {
            this.result = result;
        }
    }



