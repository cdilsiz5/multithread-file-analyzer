package analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

// Her bir dosyayı ayrı bir thread'de analiz eden görev sınıfı.
public class FileAnalyzer implements Runnable {

    private final String filePath;
    private final ConcurrentHashMap<String, FileStats> results;

    public FileAnalyzer(String filePath, ConcurrentHashMap<String, FileStats> results) {
        this.filePath = filePath;
        this.results = results;
    }

    // Thread başlatıldığında bu metot çalışır ve analiz işlemini yapar.
    @Override
    public void run() {
        File file = new File(filePath);
        String fileName = file.getName();

        System.out.println(Thread.currentThread().getName() + " -> '" + fileName + "' dosyasını analiz etmeye başladı.");

        int lineCount = 0;
        long charCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Dosyayı satır satır okuyarak analiz eder.
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
            }

            FileStats stats = new FileStats(lineCount, charCount);

            // Sonucu thread-safe olan ortak haritaya ekler.
            results.put(fileName, stats);

            System.out.println(Thread.currentThread().getName() + " -> '" + fileName + "' analizini tamamladı.");

        } catch (IOException e) {
            System.err.println("HATA: '" + fileName + "' dosyası okunurken bir hata oluştu: " + e.getMessage());
        }
    }
}