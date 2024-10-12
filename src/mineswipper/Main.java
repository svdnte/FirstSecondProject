package mineswipper;

import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Board board = new Board();

        System.out.println("Enter: x y");
        int x = sc.nextInt(), y = sc.nextInt();
        board.firstMove(x, y);
        board.prettyPrint();
        System.out.println("\n\n\n\n\n");

        x = sc.nextInt();
        y = sc.nextInt();

        char move = board.move(x, y);
        while (move != 'l' | move != 'w'){
            board.prettyPrint();
            System.out.println("\n\n\n\n\n");
            System.out.println("Enter: x y");
            x = sc.nextInt();
            y = sc.nextInt();
            move = board.move(x, y);
        }
            board.printMinesArray();
        board.prettyPrint();
    }
}


class Board {
    private static int mine = 10;
    private static int size = 10;
    private Boolean[][] minesArray;
    private Boolean[][] flagsArray;
    private String[][] field;
    private final Integer[][] neighbours = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

    Board() {
        this(size, mine);
    }

    Board(int size, int mine) {
        Board.mine = mine;
        Board.size = size;

        init();
    }

    private void init() {
        this.field = new String[size][size];
        SupFun.fillArray(this.field, " ");

        this.flagsArray = new Boolean[size][size];
        SupFun.fillArray(this.flagsArray, false);

        this.minesArray = generateMines();
    }

    private Boolean[][] generateMines() {
        Boolean[][] mines = new Boolean[size][size];

        SupFun.fillArray(mines, false);

        for (int i = 0; i < mine; i++) {
            int x = SupFun.getRandInt(mine), y = SupFun.getRandInt(mine);
            while (mines[x][y]) {
                x = SupFun.getRandInt(mine);
                y = SupFun.getRandInt(mine);
            }
            mines[x][y] = true;
        }
        return mines;
    }

    public void firstMove(int x, int y) {
        int y1 = y--;
        int x1 = size - x;

        while (minesArray[x][y]) {
            minesArray = generateMines();
        }
        this.field[x1][y1] = String.valueOf(getNeighbours(x1, y1));
    }

    public char move(int x, int y) {
        int y1 = y--;
        int x1 = size - x;

        if (minesArray[x1][y1]) {
            return 'l';
        }
        this.field[x][y] = String.valueOf(getNeighbours(x, y));

        return 'm';
    }

    private int getNeighbours(int x, int y) {
        int counter = 0;
        for (Integer[] zip : this.neighbours) {
            try {
                if (minesArray[x + zip[0]][y + zip[1]]) {
                    counter++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return counter;
    }


    public int getMine() {
        return mine;
    }

    public int getSize() {
        return size;
    }

    public void printMinesArray() {
        for (Boolean[] i : minesArray) {
            for (Boolean b : i) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }

    public void printField() {
        for (String[] i : this.field) {
            for (String b : i) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }

    public void prettyPrint() {
        System.out.println(new String(new char[3 * size + size + 1]).replace("\0", "-"));
        for (String[] i : this.field) {
            for (String b : i) {
                System.out.print("| " + b + " ");
            }
            System.out.print("|\n");
            System.out.println(new String(new char[3 * size + size + 1]).replace("\0", "-"));
        }
    }
}


class SupFun {
    /// Support functions
    public static int getRandInt(int range) {
        double num = Math.random();
        num *= range;
        return (int) num;
    }

    public static Boolean[][] fillArray(Boolean[][] array, boolean value) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = value;
            }
        }
        return array;
    }

    public static String[][] fillArray(String[][] array, String value) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = value;
            }
        }
        return array;
    }

    public static Integer[][] fillArray(Integer[][] array, Integer value) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = value;
            }
        }
        return array;
    }
}