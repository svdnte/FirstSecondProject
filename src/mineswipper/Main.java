package mineswipper;

import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Board board = new Board();
        board.printMinesArray();
    }
}


class Board {
    private static int mine = 10;
    private static int size = 10;
    private Boolean[][] minesArray;
    private Boolean[][] flagsArray;
    private String[][] field;

    Board(){
        this(size, mine);
    }
    Board(int size, int mine){
        Board.mine = mine;
        Board.size = size;

        init();
    }

    private void init(){
        this.field = new String[size][size];
        String[] arr = new String[size];
        Arrays.fill(arr, " ");
        Arrays.fill(field, arr);

        this.flagsArray = new Boolean[size][size];
        this.minesArray = generateMines();
    }

    private Boolean[][] generateMines(){
        Boolean[][] mines = new Boolean[size][size];

        SupFun.fillArray(mines, false);

        for (int i=0; i<mine; i++){
            int x = SupFun.getRandInt(mine), y = SupFun.getRandInt(mine);
            while (mines[x][y]){
                x = SupFun.getRandInt(mine);
                y = SupFun.getRandInt(mine);
            }
            mines[x][y] = true;
        }
        return mines;
    }




    public int getMine(){
        return mine;
    }
    public int getSize(){
        return size;
    }

    public void printMinesArray(){
        for(Boolean[] i: minesArray){
            for (Boolean b: i){
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }

}


class SupFun {
    /// Support functions
    public static int getRandInt(int range){
        double num = Math.random();
        num *= range;
        return (int) num;
    }
    public static Boolean[][] fillArray(Boolean[][] array, boolean value){
        for (int i=0; i<array.length; i++){
            for (int j=0; j<array.length; j++){
                array[i][j] = value;
            }
        }
        return array;
    }
    public static String[][] fillArray(String[][] array, String value){
        for (int i=0; i<array.length; i++){
            for (int j=0; j<array.length; j++){
                array[i][j] = value;
            }
        }
        return array;
    }
    public static Integer[][] fillArray(Integer[][] array, Integer value){
        for (int i=0; i<array.length; i++){
            for (int j=0; j<array.length; j++){
                array[i][j] = value;
            }
        }
        return array;
    }
}