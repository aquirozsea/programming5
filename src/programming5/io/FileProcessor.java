/*
 * FileProcessor.java
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

import java.io.File;

/**
 * To be implemented by objects that process files in an ordered traversal of a directory tree
 * @author Andres Quiroz Hernandez
 * @version 6.1
 */
public interface FileProcessor {

    /**
     * Called when the directory is first encountered in a depth-first traversal, before its contents are 
     * processed
     * @param directory the directory to process
     */
    public void directoryPreProcess(File directory);

    /**
     * Called when the directory is encountered after its contents are processed in a depth-first traversal
     * @param directory the directory to process
     */
    public void directoryPostProcess(File directory);

    /**
     * Called when a file is encountered
     * @param file the file to process
     */
    public void fileProcess(File file);

}
