/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.io;

import java.io.File;

/**
 *
 * @author aquirozh
 */
public interface FileProcessor {

    public void directoryPreProcess(File directory);
    public void directoryPostProcess(File directory);
    public void fileProcess(File file);

}
