package archiver;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;
import util.Timer;

public class ZipTask implements Runnable {

    private final Path inputDir = Paths.get("input");
    private final Path outputZip = Paths.get("output", "archived-files.zip");
    private final boolean deleteAfterZip;

    public ZipTask(boolean deleteAfterZip) {
        this.deleteAfterZip = deleteAfterZip;
    }

    @Override
    public void run() {
        Timer timer = new Timer();

        try {
            Files.createDirectories(outputZip.getParent());

            try (ZipOutputStream zos = new ZipOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(outputZip)))) {

                Files.walk(inputDir)
                        .filter(path -> !Files.isDirectory(path) && path.toString().endsWith(".txt"))
                        .forEach(path -> {
                            try {
                                ZipEntry entry = new ZipEntry(inputDir.relativize(path).toString());
                                zos.putNextEntry(entry);
                                Files.copy(path, zos);
                                zos.closeEntry();

                                System.out.println("Ziplendi: " + path);

                                if (deleteAfterZip) {
                                    Files.delete(path);
                                    System.out.println("Silindi: " + path);
                                }

                            } catch (IOException e) {
                                System.err.println("HATA (atlanıyor): " + path + " - " + e.getMessage());
                            }
                        });

            }

            System.out.println("Dosyalar başarıyla ziplenmiştir: " + outputZip);

        } catch (IOException e) {
            System.err.println("Zip işlemi sırasında hata oluştu: " + e.getMessage());
        }

        timer.printElapsed("Zip işlemi");
    }
}
