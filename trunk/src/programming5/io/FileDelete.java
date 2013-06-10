/*
 * FileDelete.java
 *
 * Copyright 2013 Andres Quiroz Hernandez
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
 * FileProcessor that deletes all files before deleting the (then) empty directory
 * @author Andres Quiroz Hernandez
 * @version 6.0
 */
public class FileDelete implements FileProcessor {

    public void directoryPreProcess(File directory) {
        Debug.println("FileDelete: Visiting " + directory.getPath());
    }

    public void directoryPostProcess(File directory) {
        Debug.println("FileDelete: Done with directory " + directory.getPath() + ": Deleting");
        directory.delete();
    }

    public void fileProcess(File file) {
        Debug.println("FileDelete: Deleting file " + file.getName());
        file.delete();
    }

}
