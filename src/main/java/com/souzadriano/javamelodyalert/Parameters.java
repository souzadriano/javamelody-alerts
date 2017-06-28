package com.souzadriano.javamelodyalert;

import java.io.File;

public class Parameters {

    private static final File TEMPORARY_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

    private static final String DEFAULT_DIRECTORY = "javamelody";

    static File getStorageDirectory(String application) {
        final String directory = getStorageDirectoryWithoutApplication();
        if (application != null) {
            return new File(directory + '/' + application);
        }
        return new File(directory);
    }

    static String getStorageDirectoryWithoutApplication() {
        final String param = System.getProperty("javamelody.storage-directory");
        final String dir;
        if (param == null) {
            dir = DEFAULT_DIRECTORY;
        } else {
            dir = param;
        }
        final String directory;
        if (dir.length() > 0 && new File(dir).isAbsolute()) {
            directory = dir;
        } else {
            directory = TEMPORARY_DIRECTORY.getPath() + '/' + dir;
        }
        return new File(directory).getAbsolutePath();
    }
}
