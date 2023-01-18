package coen6317.process;


import java.io.IOException;

public class ProcessDemo {

    public static void main(String[] args) throws IOException, InterruptedException {

        var processBuilder = new ProcessBuilder();

        processBuilder.command("notepad.exe");

        var process = processBuilder.start();

        var ret = process.waitFor();

        System.out.printf("Program exited with code: %d", ret);
    }
}