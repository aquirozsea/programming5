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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.LinkedList;

/**
 *This class allows executing OS commands from java applications. The command is passed as a string to the execute method 
 *just as it would be written in the console, or as part of an array with each parameter as additional string elements. 
 *If the command has output or throws an error, it can be retrieved with the appropriate commands.
 *WARNING: Some commands do not execute properly when executed in this way.
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class ConsoleInterface {
	
	//private Runtime rt;
	private String commandOutput = null;
	private String commandError = null;
	
	/**
	 *Executes the given OS command and reads the output or error code, if any.
	 */
	public int execute(String... command) {
		Process p = null;
		int ret = 0;
		commandOutput = null;
		commandError = null;
		try {
			//p = rt.exec(command);
			List<String> commandChain = new LinkedList<String>();
			for (String elem : command) {
				commandChain.add(elem);
			}
			p = new ProcessBuilder(commandChain).start();
			if (p != null) {
				if (p.waitFor() == 0) {
					ret = p.exitValue();
					if (ret == 0) {
						InputStream iStream = p.getInputStream();
						StringBuffer sb = new StringBuffer();
						int iout = 0;
						while ((iout = iStream.read()) != -1)
							sb.append((char)iout);
						commandOutput = sb.toString();
					}
					else {
						InputStream iStream = p.getErrorStream();
						StringBuffer sb = new StringBuffer();
						int iout = 0;
						while ((iout = iStream.read()) != -1)
							sb.append((char)iout);
						commandError = sb.toString();
					}
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
}
