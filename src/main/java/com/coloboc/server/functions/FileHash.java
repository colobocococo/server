package com.coloboc.server.functions;

public class FileHash {
    public static int gehHash(String string) {
        int p = 31;
        int mod = 998244353;
        int result = 0;
        for (char c: string.toCharArray()) {
            result *= p;
            result += c;
            result %= mod;
        }

        return result;
    }
}
