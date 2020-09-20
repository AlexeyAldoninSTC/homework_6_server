package app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CurrentDirectoryFilesListCollector {

    /**
     * Method to collect all Files in current source directory int general list of files
     * @return resulting List of Files
     */
    public List<File> fileList() {
        List<File> fileList = new ArrayList<>();
        File folder = new File(System.getProperty("user.dir"));
        if (folder.isDirectory()) {
            fileList.addAll(Arrays.asList(Objects.requireNonNull(folder.listFiles())));
        }
        return fileList;
    }
}
