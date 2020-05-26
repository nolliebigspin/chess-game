package schach;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Checker {
    File txt = new File("C:\\Users\\Henry\\Documents\\Studium lokal\\6. Semester\\SwEng Praktikum\\Git\\schach\\src\\main\\java\\schach\\input.txt");

    public String getNextLine() throws FileNotFoundException {
        Scanner scanner = new Scanner(txt);
        if (scanner.hasNextLine()){
            return scanner.nextLine();
        }
        return null;
    }

}
