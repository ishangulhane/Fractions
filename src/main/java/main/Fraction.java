package main;

public class Fraction {
    private long numerator;
    private long denominator;

    public Fraction(long num, long deno) {
        this.numerator = num;
        this.denominator = deno;
    }

    public Fraction add(Fraction b) {
        return add(b.numerator, b.denominator);
    }

    public Fraction add(long num, long deno) {
        long commonDenominator = lcm(this.denominator, deno);
        long commonNumerator = this.numerator * (commonDenominator / this.denominator) + num * (commonDenominator / deno);
        return new Fraction(commonNumerator, commonDenominator);
    }

    public Fraction substract(Fraction b) {
        Fraction op = add(-b.numerator, b.denominator);
        return op;
    }

    public Fraction multiply(Fraction b) {
        return new Fraction(this.numerator * b.numerator, this.denominator * b.denominator);
    }

    public Fraction divide(Fraction b) {
        if (b.numerator == 0) {
            throw new ArithmeticException("Error : Divide by 0");
        }
        return new Fraction(this.numerator * b.denominator, this.denominator * b.numerator);
    }

    private long lcm(long a, long b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    private long gcd(long a, long b) {
        if (a == 0)
            return b;
        if (b == 0)
            return a;
        long r;
        while (b > 0) {
            r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    @Override
    public String toString() {
        int flag = numerator<0? -1:1;
        long gcd = gcd(Math.abs(numerator), denominator);
        long num = Math.abs(numerator) / gcd;
        long deno = denominator / gcd;

        if (num == 0 || deno == 1) {
            return String.valueOf((num * flag));
        }

        if (num < deno) {
            return (num * flag) + "/" + deno;
        }
        long remainder = num % deno;
        long quotient = num / deno;
        quotient = quotient * flag;
        return quotient + "_" + remainder + "/" + deno;
    }
}
