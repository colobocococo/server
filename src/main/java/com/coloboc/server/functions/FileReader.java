package com.coloboc.server.functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static String readFile(String location) throws FileNotFoundException {
        File file = new File(location);
        Scanner scanner = new Scanner(file);

        return scanner.nextLine();
    }
}
