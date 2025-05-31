package com.coloboc.server.service;

import com.coloboc.server.functions.FileHash;
import com.coloboc.server.functions.FileReader;
import com.coloboc.server.functions.FileSaver;
import com.coloboc.server.utils.ConnectionManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Storage {
    public static void start() throws SQLException {
        var connection = ConnectionManager.open();
        var statement = connection.createStatement();

        String sql = """
                create table text (
                    id serial primary key,
                    name varchar(256),
                    location varchar(256),
                    hash int
                )
                """;

        statement.execute(sql);
    }

    public static void finish() throws SQLException {
        var connection = ConnectionManager.open();
        var statement = connection.createStatement();

        String sql = """
                drop table text;
                """;

        statement.execute(sql);
    }

    public static void insert(String fileName, String fileLocation, int fileHash) throws SQLException {
        var connection = ConnectionManager.open();
        var statement = connection.prepareStatement(
                "INSERT INTO text(name, location, hash) VALUES (?, ?, ?)");
        statement.setString(1, fileName);
        statement.setString(2, fileLocation);
        statement.setInt(3, fileHash);

        statement.execute();
    }

    public static int getId(String fileName) throws SQLException {
        var connection = ConnectionManager.open();

        String sql = "select * from text where name = ?";
        var statement = connection.prepareStatement(sql);
        statement.setString(1, fileName);

        var result = statement.executeQuery();
        if (result.next()) return result.getInt("id");
        return -1;
    }

    public static String getFile(int id) throws SQLException, FileNotFoundException {
        var connection = ConnectionManager.open();
        String sql = "select * from text where id = ?";

        var statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        var result = statement.executeQuery();
        if (result.next()) {
            String location = result.getString("location");
            return FileReader.readFile(location);
        }
        return "File does not exist";
    }

    public static int loadFile(String name, String content) throws SQLException, IOException {
        String location = FileSaver.saveFile(name, content);
        int hash = FileHash.gehHash(content);

        if (getId(name) == -1) {
            insert(name, location, hash);
        }

        return getId(name);
    }
}
