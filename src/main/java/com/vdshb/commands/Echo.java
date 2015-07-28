package com.vdshb.commands;

import static java.lang.System.out;

public class Echo {
    public static void echo(String[] args) {
        if (args.length > 0) {
            for (int i = 1; i < args.length; i++) {
                out.print(args[i]);
                out.print(" ");
            }
            out.print(args[args.length - 1]);
        }
        out.format("%n");
        out.flush();
    }
}
