package mineswipper;

import java.util.Objects;
import java.util.Scanner;


public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = new Board();
        Integer[] input;
        int x;
        int y;

        board.prettyPrint();
        input = getInput();
        x = input[0];
        y = input[1];

        board.firstMove(x, y);
        board.prettyPrint();

        input = getInput();
        x = input[0];
        y = input[1];

        char move = board.move(x, y);
        while (move != 'l' & move != 'w') {
            board.prettyPrint();

            input = getInput();
            x = input[0];
            y = input[1];
            move = board.move(x, y);
        }
        board.prettyPrint();
        if (move == 'l') {
            System.out.println("You loose!");
        } else {
            System.out.println("You won!");
        }
    }

    private static Integer[] getInput() {
        int x;
        int y;

        try {
            System.out.println("\nEnter: y x");
            x = sc.nextInt();
            y = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Wrong input. Try again");
            x = sc.nextInt();
            y = sc.nextInt();
        }
        return new Integer[]{x, y};
    }
}


class Board {
    private static int mine = 10;
    private static int size = 10;
    private Boolean[][] minesArray;
    private Boolean[][] flagsArray;
    private String[][] field;
    private final Integer[][] neighbours = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
    private int cellsOpen = 0;

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

    public void firstMove(int x1, int y1) {
        y1--;
        x1 = size - x1;

        try {
            while (minesArray[x1][y1]) {
                minesArray = generateMines();
            }

            makeMove(x1, y1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect coordinates!");
        }
    }

    public char move(int x1, int y1) {
        y1--;
        x1 = size - x1;

        try {
            if (minesArray[x1][y1]) {
                onLoose();
                return 'l';
            }

            makeMove(x1, y1);

            if (cellsOpen == size * size - mine) {
                return 'w';
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect coordinates!");
        }

        return 'm';
    }

    private void makeMove(int x, int y) {
        int n = getNeighbours(x, y);
        this.field[x][y] = String.valueOf(n);
        cellsOpen++;

        if (n == 0) {
            for (Integer[] zip : this.neighbours) {
                try {
                    if (Objects.equals(this.field[x + zip[0]][y + zip[1]], " ")) {
                        makeMove(x + zip[0], y + zip[1]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }
    }

    private void onLoose() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (minesArray[i][j]) {
                    field[i][j] = "M";
                }
            }
        }
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

    public void printMinesArray() {
        for (Boolean[] i : minesArray) {
            for (Boolean b : i) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }

    public void prettyPrint() {
        int maxLength = String.valueOf(size).length();
        String template = "%" + maxLength + "d";

        System.out.println(" y");
        System.out.println(new String(new char[maxLength + 1]).replace("\0", " ") + new String(new char[4 * size + 1]).replace("\0", "-"));

        for (int i = 0; i < size; i++) {
            System.out.printf(template, size - i);
            System.out.print(" ");

            for (int j = 0; j < size; j++) {
                System.out.print("| " + field[i][j] + " ");
            }
            System.out.print("|\n");
            System.out.println(new String(new char[maxLength + 1]).replace("\0", " ") + new String(new char[4 * size + 1]).replace("\0", "-"));
        }

        System.out.print(new String(new char[maxLength + 3]).replace("\0", " "));

        for (int j = 0; j < size; j++) {
            System.out.printf("%-4d", j + 1);
        }
        System.out.print(" x\n");
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