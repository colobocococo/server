package com.coloboc.server.functions;

public class StringAnalisys {
    public static int countSymbols(String string) {
        return string.length();
    }

    public static int countWords(String string) {
        string = " " + string;
        var array = string.toCharArray();
        int cnt = 0;
        for (int i = 0; i+1 < string.length(); i++) {
            if (array[i]  == ' ' && array[i+1] != ' ') cnt++;
        }

        return cnt;
    }

    public static int countParagraphs(String string) {
        int cnt = 1;
        for (char c: string.toCharArray()) {
            if (c == '\n') cnt++;
        }

        return cnt;
    }
}
