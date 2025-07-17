package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String INPUT_DIR = "input";

    private FileUtils() {}

    // input klasöründeki tüm .txt dosyalarının tam yolunu döndürür
    public static List<String> findTxtFilePaths() {
        List<String> filePaths = new ArrayList<>();

        File dir = new File(INPUT_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                filePaths.add(file.getAbsolutePath());
            }
        }

        return filePaths;
    }

    // .txt dosyalarını siler (opsiyonel özellik)
    public static void deleteTxtFiles(List<String> filePaths) {
        for (String pathStr : filePaths) {
            try {
                Files.deleteIfExists(Paths.get(pathStr));
                System.out.println("Silindi: " + pathStr);
            } catch (IOException e) {
                System.err.println("Silinemedi: " + pathStr + " - " + e.getMessage());
            }
        }
    }
}
