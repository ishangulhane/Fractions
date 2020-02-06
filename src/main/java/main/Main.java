package main;

import java.util.Scanner;
import java.util.Stack;

public class Main {

    private boolean isOperator(String ops) {
        return ops.equals("/") || ops.equals("*") || ops.equals("-") || ops.equals("+");
    }

    private boolean checkPrecedence(String operation, String peekOperation) {
        return !((operation.equals("*") || operation.equals("/")) && (peekOperation.equals("-") || peekOperation.equals("+")));
    }

    private Fraction parseFraction(String fraction) {
        int flag = 1;
        if (fraction.startsWith("-")) {
            fraction = fraction.substring(1);
            flag = -1;
        }
        if (fraction.startsWith("+")) {
            fraction = fraction.substring(1);
        }
        long quotient = 1;
        boolean quotientPresent = false;
        if (fraction.contains("_")) {
            quotientPresent = true;
            String split[] = fraction.split("_");
            quotient = Long.parseLong(split[0]);
            fraction = split[1];
        }
        if (fraction.contains("/")) {
            String split[] = fraction.split("/");
            if (split[1].equals("0")) {
                throw new IllegalArgumentException("Invalid fraction. Denominator cannot be 0");
            } else {
                long denominator = Long.parseLong(split[1]);
                long numerator = quotientPresent ? (quotient * denominator + Long.parseLong(split[0])) : Long.parseLong(split[0]);
                return new Fraction(flag * numerator, denominator);
            }
        } else {
            long numerator = quotientPresent ? (quotient * 1 + Long.parseLong(fraction)) : Long.parseLong(fraction);
            return new Fraction(flag * numerator, 1);
        }
    }

    private Fraction evaluate(String operation, Fraction first, Fraction second) {
        switch (operation) {
            case "+":
                return second.add(first);
            case "-":
                return second.substract(first);
            case "*":
                return second.multiply(first);
            case "/":
                return second.divide(first);
        }
        throw new UnsupportedOperationException("Operation not supported");
    }

    public String solve(String operations[]) {
        Stack<Fraction> values = new Stack<>();
        Stack<String> ops = new Stack<>();
        for (int i = 0; i < operations.length; i++) {
            if (isOperator(operations[i])) {
                while (!ops.empty() && checkPrecedence(operations[i], ops.peek())) {
                    if (values.size() <= 1) {
                        throw new IllegalArgumentException("Invalid input");
                    }
                    values.push(evaluate(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(operations[i]);
            } else {
                values.push(parseFraction(operations[i]));
            }
        }
        while (!ops.empty()) {
            if (values.size() <= 1) {
                throw new IllegalArgumentException("Invalid input");
            }
            values.push(evaluate(ops.pop(), values.pop(), values.pop()));
        }
        if (values.size() > 1) {
            throw new IllegalArgumentException("Invalid input");
        } else {
            return values.peek().toString();
        }
    }

    private void help() {
        System.out.println("\n=========================================================");
        System.out.println("Please enter operation in valid format for eg 1/2 * 3_3/4");
        System.out.println("To quit enter q or quit");
        System.out.println("=========================================================\n");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Main main = new Main();
        while (true) {
            main.help();
            String input = in.nextLine().trim();
            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                break;
            } else {
                try {
                    System.out.println(main.solve(input.split("\\s+")));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
