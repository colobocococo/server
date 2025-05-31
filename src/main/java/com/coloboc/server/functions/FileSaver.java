package com.coloboc.server.functions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSaver {
    public static String saveFile(String name, String content) throws IOException {
        File file = new File(name);
        PrintWriter pw = new PrintWriter(file);

        pw.println(content);
        pw.close();

        return file.getAbsolutePath();
    }
}
