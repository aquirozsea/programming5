/*
 * ExtensionFileFilter.java
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

package programming5.ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *This class implements a FileFilter that accepts the file extensions given to it in the constructor.
 *@see javax.swing.filechooser.FileFilter
 *@author Andres Quiroz Hernandez
 *@version 6.0
 */
public class ExtensionFileFilter extends FileFilter {
    
    String[] extensions;
    String description;
    
    /**
     *Creates a new ExtensionFileFilter which accepts the given file extensions.
     */
    public ExtensionFileFilter(String... acceptedExtensions) {
        extensions = new String[acceptedExtensions.length];
        description = "Files with extensions ";
        for (int i = 0; i < acceptedExtensions.length; i++) {
            extensions[i] = acceptedExtensions[i].toLowerCase();
            description = description + extensions[i] + " ";
        }
    }
    
    /**
     *Changes the caption that describes the files that this filter accepts
     */
    public void changeDescription(String fileDescription) {
        description = fileDescription;
    }
    
    /**
     *Implementation of the abstract method in FileFilter
     *@see javax.swing.filechooser.FileFilter#accept(File)
     */
    public boolean accept(File file) {
        boolean ret = false;
        if (file.isDirectory()) {
            ret = true;
        }
        else {
            String fileName = file.getName().toLowerCase();
            for (String extension : extensions) {
                if (fileName.endsWith(extension)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }
    
    /**
     *Implementation of the abstract method in FileFilter
     *@see javax.swing.filechooser.FileFilter#getDescription
     */
    public String getDescription() {
        return description;
    }
    
}
