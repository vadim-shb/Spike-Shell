package com.vdshb;

import com.vdshb.exceptions.PathNotFound;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.out;

public class CurrentPath {
    private static Path currentPath = Paths.get("/");

    public static String pwd() {
        return currentPath.toString();
    }

    public static Path getCurrentPath() {
        return currentPath;
    }

    public static void setCurrentPath(Path path) {
        CurrentPath.currentPath = path;
    }

    public static Path getRelatedPath(String path) throws PathNotFound {
        Path destination;

        if (path.startsWith("/")) {
            destination = Paths.get(path);
        } else {
            destination = currentPath.resolve(path);
        }

        if (Files.exists(destination)) {
            return destination;
        } else {
            throw new PathNotFound();
        }
    }
}
