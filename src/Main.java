package src;

import src.analyzer.FileAnalyzer;
import src.analyzer.FileStats;
import src.archiver.ZipTask;
import src.util.Timer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static final String INPUT_DIR = "input";
    private static final String OUTPUT_ZIP = "output/archived-files.zip";
    private static final int MAX_THREAD_LIMIT = 10;

    public static void main(String[] args) {
        Timer totalTimer = new Timer();

        // 1. Dosya kontrolü
        File inputFolder = new File(INPUT_DIR);
        File[] txtFiles = inputFolder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (txtFiles == null || txtFiles.length == 0) {
            System.out.println("No input .txt files found.");
            return;
        }

        int fileCount = Math.min(txtFiles.length, MAX_THREAD_LIMIT);
        System.out.println("Processing " + fileCount + " files...\n");

        // 2. Analiz işlemleri (çoklu thread)
        Timer analysisTimer = new Timer();
        ConcurrentHashMap<String, FileStats> resultMap = new ConcurrentHashMap<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < fileCount; i++) {
            File file = txtFiles[i];
            Thread thread = new Thread(new FileAnalyzer(file, resultMap));
            thread.start();
            threads.add(thread);
        }

        // 3. join() - tüm analiz thread'lerini bekle
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }
        analysisTimer.printElapsed("Dosya analiz süresi");

        // 4. Sonuçları yazdır
        int totalLines = 0;
        int totalChars = 0;
        System.out.println("==== ANALİZ SONUÇLARI ====");
        for (String fileName : resultMap.keySet()) {
            FileStats stats = resultMap.get(fileName);
            System.out.println(fileName + " - " + stats.getLineCount() + " satır / " + stats.getCharCount() + " karakter");
            totalLines += stats.getLineCount();
            totalChars += stats.getCharCount();
        }
        System.out.println("Toplam: " + totalLines + " satır / " + totalChars + " karakter\n");

        // 5. Zipleme işlemi (tek thread)
        Timer zipTimer = new Timer();
        Thread zipThread = new Thread(new ZipTask(INPUT_DIR, OUTPUT_ZIP));
        zipThread.start();

        try {
            zipThread.join(); // zip işlemi tamamlanmadan devam etme
        } catch (InterruptedException e) {
            System.err.println("Zip thread interrupted: " + e.getMessage());
        }
        zipTimer.printElapsed("Zip oluşturma süresi");

        // 6. Toplam süre
        totalTimer.printElapsed("Toplam program çalışma süresi");
    }
}
