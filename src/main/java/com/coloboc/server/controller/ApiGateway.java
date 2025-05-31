package com.coloboc.server.controller;

import com.coloboc.server.functions.FileHash;
import com.coloboc.server.service.Analysis;
import com.coloboc.server.service.Storage;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/gateway")
public class ApiGateway {
    @GetMapping("/hello")
    public String mainListener() {
        return "Hello, world;";
    }

    @GetMapping("/number/{number}")
    public String getNumber(@PathVariable("number") int number) {
        return "Your number is: " + number;
    }

    @PostMapping("/get")
    public String get(@RequestParam int id) throws SQLException, FileNotFoundException {
        return Storage.getFile(id);
    }

    @PostMapping("/load")
    public int load(@RequestParam String name,
                       @RequestParam String content) throws SQLException, IOException {
        return Storage.loadFile(name, content);
    }

    @PostMapping("/analyze")
    public String load(@RequestParam int id) throws SQLException, FileNotFoundException {
        return Analysis.getAnalysis(id, Storage.getFile(id));
    }
}
