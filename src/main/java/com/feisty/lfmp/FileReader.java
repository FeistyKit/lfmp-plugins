package com.feisty.lfmp;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class FileReader {
    // why the hell is this not implemented smh my head
    public static String readToString(String fname) throws FileNotFoundException {
        File f = new File(fname);
        Scanner s = new Scanner(f);
        String toReturn = new String();
        while (s.hasNextLine()) {
            toReturn += s.nextLine() + "\n";
        }
        return toReturn;
    }

    // this too
    public static ArrayList<String> readToArrayList(String fname) throws FileNotFoundException {
        File f = new File(fname);
        Scanner s = new Scanner(f);
        ArrayList<String> toReturn = new ArrayList<>();
        while (s.hasNextLine()) {
            toReturn.add(s.nextLine());
        }
        return toReturn;
    }
}
