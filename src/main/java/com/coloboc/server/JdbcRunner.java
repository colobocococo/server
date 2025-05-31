package com.coloboc.server;

import com.coloboc.server.functions.FileHash;
import com.coloboc.server.functions.FileReader;
import com.coloboc.server.functions.FileSaver;
import com.coloboc.server.service.Analysis;
import com.coloboc.server.service.Storage;

import java.io.IOException;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] argc) throws SQLException, IOException {
        int id = 8;
        String content = Storage.getFile(id);
        System.out.println(content);

        String analisys = Analysis.getAnalysis(id, content);
        System.out.println(analisys);
    }
}
