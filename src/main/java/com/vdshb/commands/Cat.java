package com.vdshb.commands;

import com.vdshb.CurrentPath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;

public class Cat {

    public static void cat(String[] args) {
        if (args.length < 1) {
            out.println("Incorrect command parameters.");
            return;
        }

        Path filePath = CurrentPath.getRelatedPath(args[0]);
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line = null;
            while ((line = reader.readLine()) != null)
                out.println(line);
        } catch (IOException e) {
            out.println("Can not read file");
        }
    }
}
