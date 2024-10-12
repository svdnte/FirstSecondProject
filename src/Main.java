public class Main {
    public static void main(String[] args) {
        //template
        Fraction fr = null;
        Fraction fr1 = null;
        System.out.println(Fraction.divOf(new Fraction(8, 9), new Fraction(1, 3)));
    }
}


class Fraction {
    private int num;
    private int den = 1;
    private int intPart;

    Fraction(int num, int den) {
        try {
            int a = num / den;
        } catch (Exception ex) {
            System.out.println("Warning: " + ex);
            return;
        }
        this.num = num * (den < 0 ? -1 : 1);
        this.den = Math.abs(den);

        normalisation();
    }

    Fraction(int num, int den, boolean b) {
        try {
            int a = num / den;
        } catch (Exception ex) {
            System.out.println("Warning: " + ex);
            return;
        }
        this.num = num * (den < 0 ? -1 : 1);
        this.den = Math.abs(den);
    }

    //// private methods

    private int gcd(int num, int den) {
        while (num != 0 && den != 0) {
            if (num > den) {
                num %= den;
            } else {
                den %= num;
            }
        }
        return num + den;
    }

    private void normalisation() {
        int n = this.gcd(Math.abs(num), den);

        this.num /= n;
        this.den /= n;

        this.gettingIntPart();
    }

    private void gettingIntPart() {
        ///  Возвращает целую часть дроби
        this.intPart = this.num / this.den;
    }

    private Fraction toGeneralDen(Fraction fr) {
        /// Приводит дробь к общему знаменателю
        int num1 = this.num;
        int den1 = this.den;

        num1 *= fr.den;
        den1 *= fr.den;
        return new Fraction(num1, den1, true);
    }

    //// public methods

    public Fraction sum(Fraction fr) {
        /// Сумма данного экземпляра и аргумента
        Fraction fr1 = this.toGeneralDen(fr);
        Fraction fr2 = fr.toGeneralDen(this);

        Fraction finalFr = new Fraction(fr1.getNum() + fr2.getNum(), fr1.getDen());
        finalFr.normalisation();

        return finalFr;
    }

    public static Fraction sumOf(Fraction fr1, Fraction fr2) {
        /// Сумма двух аргументов
        Fraction fr11 = fr1.toGeneralDen(fr2);
        Fraction fr22 = fr2.toGeneralDen(fr1);

        return new Fraction(fr11.getNum() + fr22.getNum(), fr11.getDen());
    }

    public Fraction sub(Fraction fr) {
        Fraction fr1 = this.toGeneralDen(fr);
        Fraction fr2 = fr.toGeneralDen(this);

        Fraction finalFr = new Fraction(fr1.getNum() - fr2.getNum(), fr1.getDen());
        finalFr.normalisation();

        return finalFr;
    }

    public static Fraction subOf(Fraction fr1, Fraction fr2) {
        Fraction fr11 = fr1.toGeneralDen(fr2);
        Fraction fr22 = fr2.toGeneralDen(fr1);

        return new Fraction(fr11.getNum() - fr22.getNum(), fr11.getDen());
    }

    public Fraction mul(Fraction fr) {
        Fraction finalFr = new Fraction(this.num * fr.num, this.den * fr.den);
        finalFr.normalisation();

        return finalFr;
    }

    public static Fraction mulOf(Fraction fr1, Fraction fr2) {
        Fraction finalFr = new Fraction(fr1.num * fr2.num, fr1.den * fr2.den);
        finalFr.normalisation();

        return finalFr;
    }

    public Fraction div(Fraction fr) {
        Fraction finalFr = new Fraction(this.num * fr.den, this.den * fr.num);
        finalFr.normalisation();

        return finalFr;
    }

    public static Fraction divOf(Fraction fr1, Fraction fr2) {
        Fraction finalFr = new Fraction(fr1.num * fr2.den, fr1.den * fr2.num);
        finalFr.normalisation();

        return finalFr;
    }

    //// getters, setters

    public int getDen() {
        return den;
    }

    public void setDen(int dec) {
        if (dec == 0) {
            System.out.println("Warning: dec can`t be 0");
            return;
        } else if (dec < 0) {
            this.num *= -1;
        }
        this.den = dec;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIntPart() {
        return intPart;
    }

    //// to string

    public String toString() {
        return (this.intPart != 0 ? this.intPart : "") + (this.num % den != 0 ? " " + this.num % den + "/" + this.den : "");
    }
}