package com.vdshb;

import java.util.Arrays;

import static com.vdshb.commands.Cat.cat;
import static com.vdshb.commands.Cd.cd;
import static com.vdshb.commands.Cp.cp;
import static com.vdshb.commands.Echo.echo;
import static com.vdshb.commands.Exit.exit;
import static com.vdshb.commands.Ls.ls;
import static com.vdshb.commands.Mkdir.mkdir;
import static com.vdshb.commands.Mkfile.mkfile;
import static com.vdshb.commands.Mv.mv;
import static com.vdshb.commands.Rm.rm;
import static com.vdshb.commands.Tail.tail;
import static java.lang.System.out;

public class CommandLoop {

    public static void start() {

        while (true) {
            out.print(CurrentPath.pwd() + "$");
            String command = ConsoleReader.readLine();
            String[] commandParts = command.split("[ \t]");
            String[] args = Arrays.copyOfRange(commandParts, 1, commandParts.length);
            switch (commandParts[0]) {
                case "exit":
                    exit(args);
                    break;
                case "echo":
                    echo(args);
                    break;
                case "ls":
                    ls(args);
                    break;
                case "cd":
                    cd(args);
                    break;
                case "mkdir":
                    mkdir(args);
                    break;
                case "cp":
                    cp(args);
                    break;
                case "rm":
                    rm(args);
                    break;
                case "mv":
                    mv(args);
                    break;
                case "mkfile":
                    mkfile(args);
                    break;
                case "cat":
                    cat(args);
                    break;
                case "tail":
                    tail(args);
                    break;

            }
        }
    }

}
