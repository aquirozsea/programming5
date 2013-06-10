/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programming5.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 *
 * @author andresqh
 */
public class FileZip implements FileProcessor {

    protected JarOutputStream zipStream;
    private boolean keepOpen = false;
    private boolean ignoreRoot = false;
    private File startDir = null;

    public FileZip(String outputFile) throws IOException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        zipStream = new JarOutputStream(new FileOutputStream(outputFile), manifest);
    }

    public FileZip(String outputFile, Manifest myManifest) throws IOException {
        zipStream = new JarOutputStream(new FileOutputStream(outputFile), myManifest);
    }

    public void setKeepOpen(boolean value) {
        keepOpen = value;
    }

    public void setIgnoreRoot(boolean value) {
        ignoreRoot = value;
    }

    public void addDirectory(File directory) throws IOException {
        String name = directory.getPath().replaceAll("\\\\", "/");
        if (ignoreRoot) {
            name = name.substring(name.indexOf("/") + 1);
        }
        if (!name.isEmpty()) {
            if (!name.endsWith("/")) {
                name += "/";
            }
            JarEntry entry = new JarEntry(name);
            entry.setTime(directory.lastModified());
            zipStream.putNextEntry(entry);
            zipStream.closeEntry();
        }
    }

    public void addFile(File file) throws IOException {
        String name = file.getPath().replaceAll("\\\\", "/");
        if (ignoreRoot) {
            name = name.substring(name.indexOf("/") + 1);
        }
        JarEntry entry = new JarEntry(name);
        entry.setTime(file.lastModified());
        zipStream.putNextEntry(entry);
        FileHandler handler = new FileHandler(file.getPath(), FileHandler.HandleMode.READ, FileHandler.FileType.BINARY);
        zipStream.write(handler.readFully());
        zipStream.closeEntry();
    }

    public void close() throws IOException {
        zipStream.close();
    }

    public void directoryPreProcess(File directory) {
        validateStartDir(directory);
        try {
            addDirectory(directory);
        }
        catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }

    public void directoryPostProcess(File directory) {
        closeIfStartDir(directory);
    }

    public void fileProcess(File file) {
        invalidateStartDir();
        try {
            addFile(file);
        }
        catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private void validateStartDir(File directory) {
        if (startDir == null) {
            startDir = directory;
        }
    }

    private void invalidateStartDir() {
        if (startDir == null) {
            startDir = new File("NotStart");
        }
    }

    private void closeIfStartDir(File directory) {
        if (directory.equals(startDir)) {
            if (!keepOpen) {
                Debug.println("FileZip: Closing zip stream");
                try {this.close();}
                catch (IOException ioe) {throw new RuntimeException(ioe);}
            }
            else {
                startDir = null; // Reset
            }
        }
    }

}
