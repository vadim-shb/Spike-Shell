package com.vdshb.commands;

import com.vdshb.CurrentPath;
import com.vdshb.exceptions.PathNotFound;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;

public class Mv {
    public static void mv(String[] args) {
        Path from;
        try {
            from = CurrentPath.getExistRelatedPath(args[0]);
        } catch (PathNotFound pathNotFound) {
            out.format("Destination %s not exist%n", args[0]);
            return;
        }
        Path to = CurrentPath.getRelatedPath(args[1]);
        try {
            Files.move(from, to);
        } catch (IOException e) {
            out.format("Can not move file%n");
            e.printStackTrace();
        }
    }
}
