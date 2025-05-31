package com.coloboc.server.service;

import com.coloboc.server.functions.FileReader;
import com.coloboc.server.functions.StringAnalisys;
import com.coloboc.server.utils.NewConnectionManager;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Analysis {
    public static void start() throws SQLException {
        var connection = NewConnectionManager.open();
        var statement = connection.createStatement();

        String sql = """
                create table words (
                    id serial primary key,
                    symbols int, 
                    words int,
                    paragraphs int
                )
                """;

        statement.execute(sql);
    }

    public static void finish() throws SQLException {
        var connection = NewConnectionManager.open();
        var statement = connection.createStatement();

        String sql = """
                drop table words;
                """;

        statement.execute(sql);
    }

    public static void setAnalysis(int id, String content) throws SQLException {
        int symbols = StringAnalisys.countSymbols(content);
        int words = StringAnalisys.countWords(content);
        int paragraphs = StringAnalisys.countParagraphs(content);

        var connection = NewConnectionManager.open();

        var statement = connection.prepareStatement(
                "INSERT INTO words(id, symbols, words, paragraphs) VALUES (?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setInt(2, symbols);
        statement.setInt(3, words);
        statement.setInt(4, paragraphs);

        statement.execute();
    }

    public static String getAnalysis(int id, String content)
            throws SQLException, FileNotFoundException {
        var connection = NewConnectionManager.open();
        String sql = "select * from words where id = ?";

        var statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        var result = statement.executeQuery();
        if (result.next()) {} else {
            setAnalysis(id, content);
            return "Analisys of file"
                    + ": symbols - " + StringAnalisys.countSymbols(content)
                    + ", words - " + StringAnalisys.countWords(content)
                    + ", paragraphs " + StringAnalisys.countParagraphs(content) + ";";
        }

        return "Analisys of file"
                + ": symbols - " + result.getInt("symbols")
                + ", words - " + result.getInt("words")
                + ", paragraphs " + result.getInt("paragraphs") + ";";
    }
}
