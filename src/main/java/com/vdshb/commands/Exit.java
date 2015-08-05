package com.vdshb.commands;

import static java.lang.System.out;

public class Exit {

    public static void exit(String[] args) {
        if (args.length > 1) {
            out.println("Incorrect command parameters.");
            return;
        }
        if (args.length == 1) {
            try {
                int exitCode = Integer.valueOf(args[0]);
                System.exit(exitCode);
            } catch (NumberFormatException e) {
                out.println("Incorrect exit code.");
                return;
            }
        }
        System.exit(0);
    }
}
