package com.vdshb.commands;

import com.vdshb.CurrentPath;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;

public class Mkfile {

    public static void mkfile(String[] args) {
        if (args.length < 2) {
            out.println("Incorrect command parameters.");
            return;
        }

        Path newFilePath = CurrentPath.getRelatedPath(args[0]);
        try (BufferedWriter writer = Files.newBufferedWriter(newFilePath, StandardCharsets.UTF_8)) {
            for (int i = 1; i < args.length - 1; i++)
                writer.write(args[i] + " ");
            writer.write(args[args.length - 1]);
        } catch (IOException e) {
            out.println("Can not write file");
        }

    }
}
