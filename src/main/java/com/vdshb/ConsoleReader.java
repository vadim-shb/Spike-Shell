package com.vdshb;

import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @NotNull
    public static  String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
