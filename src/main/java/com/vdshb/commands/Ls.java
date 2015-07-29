package com.vdshb.commands;

import com.vdshb.CurrentPath;
import com.vdshb.exceptions.PathNotFound;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.lang.System.out;

public class Ls {

    private static boolean l;
    private static boolean a;
    private static Path path;
    private static int maxFileNameLength;
    private static int maxOwnerNameLength;


    public synchronized static void ls(String[] args) {
        try {
            setFlags(args);
            setPath(args);
            setParams();
            for (Path file : Files.newDirectoryStream(path)) {
                if (!a && file.getFileName().toString().startsWith(".")) continue;
                if (l) {
                    out.printf("%-12s%-" + (maxOwnerNameLength + 2) + "s%-" + (maxFileNameLength + 2) + "s%n", getPermissionsString(file), Files.getOwner(file).getName(), file.getFileName());
                } else {
                    out.printf("%s%n", file.getFileName());
                }
            }
        } catch (IOException e) {
            out.println("IO exception... ");
        } catch (PathNotFound pathNotFound) {
            out.println("Path not exists");
        }
    }

    private static void setFlags(String[] args) {
        List<String> argsAsList = Arrays.asList(args);
        l = argsAsList.contains("-l");
        a = argsAsList.contains("-a");
        if (argsAsList.contains("-al") || argsAsList.contains("-la")) {
            l = true;
            a = true;
        }
    }

    private static void setPath(String[] args) throws PathNotFound {
        if (args.length == 0) {
            path = CurrentPath.getCurrentPath();
            return;
        }
        String where = args[args.length - 1];
        if (where.startsWith("-")) {
            path = CurrentPath.getCurrentPath();
        } else {
            path = CurrentPath.getExistRelatedPath(where);
        }
    }

    private static String getPermissionsString(Path file) throws IOException {
        Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(file);
        StringBuilder retval = new StringBuilder();
        // @formatter:off
        if (permissions.contains(PosixFilePermission.OWNER_READ)) retval.append("r"); else retval.append("-");
        if (permissions.contains(PosixFilePermission.OWNER_WRITE)) retval.append("w"); else retval.append("-");
        if (permissions.contains(PosixFilePermission.OWNER_EXECUTE)) retval.append("x"); else retval.append("-");
        if (permissions.contains(PosixFilePermission.GROUP_READ)) retval.append("r"); else retval.append("-");
        if (permissions.contains(PosixFilePermission.GROUP_WRITE)) retval.append("w"); else retval.append("-");
        if (permissions.contains(PosixFilePermission.GROUP_EXECUTE)) retval.append("x"); else retval.append("-");
        if (permissions.contains(PosixFilePermission.OTHERS_READ)) retval.append("r"); else retval.append("-");
        if (permissions.contains(PosixFilePermission.OTHERS_WRITE)) retval.append("w"); else retval.append("-");
        if (permissions.contains(PosixFilePermission.OTHERS_EXECUTE)) retval.append("x"); else retval.append("-");
        // @formatter:on
        return retval.toString();

    }

    private static void setParams() throws IOException {
        if (l) {
            maxFileNameLength = 0;
            maxOwnerNameLength = 0;
            for (Path file : Files.newDirectoryStream(path)) {
                int nameLength = file.getFileName().toString().length();
                int ownerNameLength = Files.getOwner(file).getName().length();

                if (nameLength > maxFileNameLength) maxFileNameLength = nameLength;
                if (ownerNameLength > maxOwnerNameLength) maxOwnerNameLength = ownerNameLength;
            }
        }
    }
}
