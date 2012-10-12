/*
 * NetworkException.java
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

package programming5.net;

import java.io.IOException;

/**
 *This exception class is meant to be a generic and unique exception class for all network        
 *communication methods. 
 *@author Andres Quiroz Hernandez
 *@version 6.1
 */
public class NetworkException extends IOException {

	public NetworkException() {
		super();
	}

	public NetworkException(String s) {
		super(s);
	}
	
}