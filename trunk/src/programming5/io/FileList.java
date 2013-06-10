/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andresqh
 */
public class FileList implements FileProcessor {

    protected List<File> list = new ArrayList<File>();

    public void directoryPreProcess(File directory) {
    }

    public void directoryPostProcess(File directory) {
    }

    public void fileProcess(File file) {
        list.add(file);
    }

    public List<File> getCurrentList() {
        return new ArrayList<File>(list);
    }

}
