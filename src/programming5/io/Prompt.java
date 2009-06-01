/*
 * Prompt.java
 *
 * Copyright 2009 Andres Quiroz Hernandez
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class encapsulates a buffered reader for System.in and provides utility methods for the input of
 * basic types. The default behavior can be accessed via static methods. IO exceptions thrown by the
 * reader cause the stack trace to be printed.
 * @author Andres Quiroz Hernandez
 */
public class Prompt {

    protected static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private String separator = "?";
    private boolean handleErrors = true;

    /**
     * Creates a default prompt, with error handling and using "?" as prompt separator symbol
     */
    public Prompt() {
    }

    /**
     * Creates a prompt, with error handling and the given string as prompt separator symbol
     */
    public Prompt(String defaultSeparator) {
        separator = defaultSeparator;
    }

    /**
     * @param newSeparator the separator symbol to use for prompts
     */
    public void setSeparator(String newSeparator) {
        separator = newSeparator;
    }

    /**
     * @param doHandle if true, errors in the input of specific types will result in an error message being 
     * displayed and the prompt to be re-issued
     */
    public void setHandleErrors(boolean doHandle) {
        handleErrors = doHandle;
    }

    /**
     * @param prompt the prompt message
     * @return the user's input
     */
    public String ask(String prompt) {
        String ret = null;
        try {
            System.out.print(prompt + separator + " ");
            ret = in.readLine();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return ret;
    }

    /**
     * @param prompt the prompt message
     * @return the user's integer input
     * @throws NumberFormatException if not handling errors
     */
    public int askInt(String prompt) {
        int ret = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + separator + " ");
                ret = Integer.parseInt(in.readLine());
                valid = true;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            catch (NumberFormatException nfe) {
                if (handleErrors) {
                    System.out.println("Input must be an integer");
                }
                else {
                    throw nfe;
                }
            }
        } while (!valid);
        return ret;
    }

    /**
     * @param prompt the prompt message
     * @param errorMsg the message to display before re-asking if the input is not in valid format
     * @return the user's integer input
     */
    public int askInt(String prompt, String errorMsg) {
        int ret = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + separator + " ");
                ret = Integer.parseInt(in.readLine());
                valid = true;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            catch (NumberFormatException nfe) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return ret;
    }

    /**
     * @param prompt the prompt message
     * @return the user's long integer input
     * @throws NumberFormatException if not handling errors
     */
    public long askLong(String prompt) {
        long ret = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + separator + " ");
                ret = Long.parseLong(in.readLine());
                valid = true;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            catch (NumberFormatException nfe) {
                if (handleErrors) {
                    System.out.println("Input must be a long integer");
                }
                else {
                    throw nfe;
                }
            }
        } while (!valid);
        return ret;
    }

    /**
     * @param prompt the prompt message
     * @param errorMsg the message to display before re-asking if the input is not in valid format
     * @return the user's long integer input
     */
    public long askLong(String prompt, String errorMsg) {
        long ret = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + separator + " ");
                ret = Long.parseLong(in.readLine());
                valid = true;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            catch (NumberFormatException nfe) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return ret;
    }

    /**
     * @param prompt the prompt message
     * @return the user's float input
     * @throws NumberFormatException if not handling errors
     */
    public float askFloat(String prompt) {
        float ret = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + separator + " ");
                ret = Float.parseFloat(in.readLine());
                valid = true;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            catch (NumberFormatException nfe) {
                if (handleErrors) {
                    System.out.println("Input must be a floating point number");
                }
                else {
                    throw nfe;
                }
            }
        } while (!valid);
        return ret;
    }

    /**
     * @param prompt the prompt message
     * @param errorMsg the message to display before re-asking if the input is not in valid format
     * @return the user's float input
     */
    public float askFloat(String prompt, String errorMsg) {
        float ret = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + separator + " ");
                ret = Float.parseFloat(in.readLine());
                valid = true;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            catch (NumberFormatException nfe) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return ret;
    }

    /**
     * @param prompt the prompt message
     * @return the user's double input
     * @throws NumberFormatException if not handling errors
     */
    public double askDouble(String prompt) {
        double ret = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + separator + " ");
                ret = Double.parseDouble(in.readLine());
                valid = true;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            catch (NumberFormatException nfe) {
                if (handleErrors) {
                    System.out.println("Input must be a double floating point number");
                }
                else {
                    throw nfe;
                }
            }
        } while (!valid);
        return ret;
    }

    /**
     * @param prompt the prompt message
     * @param errorMsg the message to display before re-asking if the input is not in valid format
     * @return the user's double input
     */
    public double askDouble(String prompt, String errorMsg) {
        double ret = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(prompt + separator + " ");
                ret = Double.parseDouble(in.readLine());
                valid = true;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            catch (NumberFormatException nfe) {
                System.out.println(errorMsg);
            }
        } while (!valid);
        return ret;
    }

    /**
     * Waits for the user to press ENTER, displaying "Press ENTER to continue..."
     */
    public void pause() {
        try {
            System.out.print("Press ENTER to continue...");
            in.readLine();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Waits for the user to press ENTER, displaying the given prompt message
     */
    public void pause(String prompt) {
        try {
            System.out.print(prompt);
            in.readLine();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Static version of ask
     */
    public static String staticAsk(String prompt) {
        return (new Prompt()).ask(prompt);
    }

    /**
     * Static version of askInt
     */
    public static int staticAskInt(String prompt) {
        return (new Prompt()).askInt(prompt);
    }

    /**
     * Static version of askLong
     */
    public static long staticAskLong(String prompt) {
        return (new Prompt()).askLong(prompt);
    }

    /**
     * Static version of askFloat
     */
    public static float staticAskFloat(String prompt) {
        return (new Prompt()).askFloat(prompt);
    }

    /**
     * Static version of askDouble
     */
    public static double staticAskDouble(String prompt) {
        return (new Prompt()).askDouble(prompt);
    }

    /**
     * Static version of pause
     */
    public static void staticPause() {
        (new Prompt()).pause();
    }

}
