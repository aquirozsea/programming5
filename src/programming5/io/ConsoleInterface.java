/*
 * ConsoleInterface.java
 *
 * Copyright 2004 Andres Quiroz Hernandez
 *
 * This file is part of Programming5.
 * Programming5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Programming5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Programming5.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package programming5.io;

import programming5.concurrent.ConditionVariable;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *This class allows executing OS commands from java applications. The command is passed as a string to the execute method 
 *just as it would be written in the console, or as part of an array with each parameter as additional string elements. 
 *If the command has output or throws an error, it can be retrieved with the appropriate commands.
 *WARNING: Some commands do not execute properly when executed in this way.
 *@author Andres Quiroz Hernandez
 *@version 6.1
 */
public class ConsoleInterface {

    public static enum Shell {BASH};

    //private Runtime rt;
    private String commandOutput = null;
    private String commandError = null;

    private boolean redirectOutput = true;
    private PrintStream redirectStream = System.out;

    public ConsoleInterface() {
    }

    public ConsoleInterface(boolean doRedirectOutput) {
        redirectOutput = doRedirectOutput;
    }

    public void setRedirectPrintStream(PrintStream printStream) {
        redirectStream = printStream;
    }

    protected int runProcess(ProcessBuilder pb) {
        Process p = null;
        int ret = 0;
        commandOutput = null;
        commandError = null;
        try {
            p = pb.start();
            if (p != null) {
                OutputRedirector redirector = null;
                if (redirectOutput) {
                    redirector = new OutputRedirector(p.getInputStream(), redirectStream);
                    new Thread(redirector).start();
                }
                if (p.waitFor() == 0) {
                    ret = p.exitValue();
//                    if (ret == 0) {
                        if (redirectOutput) {
                            redirector.end();
                        }
                        else {
                            InputStream iStream = p.getInputStream();
                            StringBuilder sb = new StringBuilder();
                            int iout = 0;
                            while ((iout = iStream.read()) != -1) {
                                sb.append((char) iout);
                            }
                            commandOutput = sb.toString();
                        }
//                    }
//                    else {
                        InputStream iStream = p.getErrorStream();
                        StringBuilder sb = new StringBuilder();
                        int iout = 0;
                        while ((iout = iStream.read()) != -1) {
                            sb.append((char) iout);
                        }
                        commandError = sb.toString();
//                    }
                }
                if (redirectOutput) {
                    redirector.waitUntilDone();
                }
                p.destroy();
            }
            else {
                ret = -1;
                commandError = "Invalid command";
            }
        }
        catch (InterruptedException ie) {
            ret = -2;
            commandError = ie.getMessage();
        }
        catch (IOException ioe) {
            ret = -3;
            commandError = ioe.getMessage();
        }
        return ret;
    }

    /**
     *Executes the given OS command and reads the output or error code, if any.
     */
    public int execute(String... command) {
        List<String> commandChain = new LinkedList<String>();
        commandChain.addAll(Arrays.asList(command));
        return runProcess(new ProcessBuilder(commandChain));
    }

    public int execute(List<String> commandChain, Map<String, String> environment) {
        ProcessBuilder pb = new ProcessBuilder(commandChain);
        pb.environment().putAll(environment);
        return runProcess(pb);
    }

    public int executeShell(String cmd, Shell shellType) {
        switch (shellType) {
            case BASH: return execute("bash", "-c", cmd);
            default: return -1;
        }
    }

    public Process executeAsync(String... command) throws IOException {
        List<String> commandChain = new LinkedList<String>();
        commandChain.addAll(Arrays.asList(command));
        Process p = new ProcessBuilder(commandChain).start();
        if (p != null) {
            OutputRedirector redirector = null;
            if (redirectOutput) {
                redirector = new OutputRedirector(p.getInputStream(), redirectStream);
                new Thread(redirector).start();
            }
            return p;
        }
        else {
            throw new IOException("ConsoleInterface: Could not execute command: Could not build process");
        }
    }

    /**
     *@return a string with the command's output, null if there is none
     */
    public String getCommandOutput() {
        return commandOutput;
    }

    /**
     *@return a string with the command's error result, null if there is none.
     */
    public String getCommandError() {
        return commandError;
    }

    private class OutputRedirector implements Runnable {

        boolean running = true;
        ConditionVariable done = new ConditionVariable();
        InputStream iStream;
        PrintStream oStream;

        public OutputRedirector(InputStream myInputStream, PrintStream myOutputStream) {
            iStream = myInputStream;
            oStream = myOutputStream;
        }

        public void end() {
            running = false;
        }

        public void waitUntilDone() {
            done.awaitUninterruptibly();
        }

        public void run() {
            int iout = 0;
            while (running || iout != -1) {
                try {
                    iout = iStream.read();
                    if (iout != -1) {
                        StringBuilder sb = new StringBuilder();
                        while (iout != 10 && iout != 13 && iout != -1) {
                            sb.append((char) iout);
                            iout = iStream.read();
                        }
                        oStream.println(sb.toString());
                    }
                }
                catch (IOException ioe) {
                    running = false;
                    ioe.printStackTrace();
                }
            }
            done.signalAll();
        }
    }
}
