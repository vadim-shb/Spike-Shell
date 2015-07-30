package com.vdshb.commands;

import com.vdshb.CurrentPath;
import com.vdshb.exceptions.PathNotFound;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static java.lang.System.out;
import static java.nio.file.FileVisitResult.CONTINUE;

public class Rm {
    public static void rm(String[] args) {
        if (args.length < 1) {
            out.println("Incorrect command parameters.");
            return;
        }
        Path target;
        try {
            target = CurrentPath.getExistRelatedPath(args[0]);
        } catch (PathNotFound pathNotFound) {
            out.format("Destination %s not exist%n", args[0]);
            return;
        }
        if (Files.isDirectory(target))
            recursivelyDeleteDirectory(target);
        else {
            try {
                Files.delete(target);
            } catch (IOException e) {
                out.println("Can not delete file");
            }
        }

    }

    private static void recursivelyDeleteDirectory(Path target) {
        try {
            Files.walkFileTree(target, new DeleteFileVisitor());
        } catch (IOException e) {
            out.println("Can not delete directory");
        }
    }

    private static class DeleteFileVisitor implements FileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            try {
                Files.delete(file);
            } catch (IOException e) {
                out.println("Can not delete file");
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            throw new IOException();
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return CONTINUE;
        }
    }
}
