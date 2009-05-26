/*
 * ServiceObjectFactory.java
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

/**
 *A class that implements this interface is expected to return instances of ServiceObjects, which in turn are expected 
 *to provide a specific service to a client in a client/server system. ServiceObject implementations and protocols are 
 *application specific.
 *@author Andrés Quiroz Hernández
 *@version 5.0
 */
public interface ServiceObjectFactory {
    
    /**
     *@return a new ServiceObject instance
     */
    public ServiceObject getServiceObject();	
        
}
