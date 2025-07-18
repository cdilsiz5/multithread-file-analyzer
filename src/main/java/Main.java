import analyzer.FileAnalyzer;
import analyzer.FileStats;
import archiver.ZipTask;
import util.FileUtils;
import util.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    private static final int MAX_THREAD_LIMIT = 10;

    public static void main(String[] args) {
        Timer totalTimer = new Timer();

        // 1. .txt dosyalarını bul
        List<String> txtFilePaths = FileUtils.findTxtFilePaths();

        if (txtFilePaths.isEmpty()) {
            System.out.println("input klasöründe .txt dosyası bulunamadı.");
            return;
        }

        int fileCount = Math.min(txtFilePaths.size(), MAX_THREAD_LIMIT);
        System.out.println("==> Toplam analiz edilecek dosya sayısı: " + fileCount);

        // 2. Dosya analiz thread'lerini başlat
        Timer analysisTimer = new Timer();
        ConcurrentHashMap<String, FileStats> resultMap = new ConcurrentHashMap<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < fileCount; i++) {
            String filePath = txtFilePaths.get(i);
            Thread thread = new Thread(new FileAnalyzer(filePath, resultMap));
            thread.start();
            threads.add(thread);
        }

        // 3. Tüm thread'lerin bitmesini bekle
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread beklenirken hata: " + e.getMessage());
            }
        }

        analysisTimer.printElapsed("Dosya analiz süresi");

        // 4. Analiz sonuçlarını yazdır
        int totalLines = 0;
        long totalChars = 0;
        System.out.println("\n==== ANALİZ SONUÇLARI ====");
        for (var entry : resultMap.entrySet()) {
            String fileName = entry.getKey();
            FileStats stats = entry.getValue();
            System.out.println(fileName + " - " + stats.getLineCount() + " satır / " + stats.getCharCount() + " karakter");
            totalLines += stats.getLineCount();
            totalChars += stats.getCharCount();
        }
        System.out.printf("Toplam: %d satır / %d karakter\n\n", totalLines, totalChars);

        // 5. Zipleme işlemi
        Timer zipTimer = new Timer();
        Thread zipThread = new Thread(new ZipTask(false)); // silme yapılmayacak
        zipThread.start();

        try {
            zipThread.join();
        } catch (InterruptedException e) {
            System.err.println("Zip thread beklenirken hata: " + e.getMessage());
        }

        zipTimer.printElapsed("Zip oluşturma süresi");

        // 6. Dosyaları sil
        System.out.println("\n==> Analiz tamamlandı. .txt dosyaları siliniyor...");
        FileUtils.deleteTxtFiles(txtFilePaths);
        System.out.println("==> Silme işlemi tamamlandı.");

        // 7. Toplam süre
        totalTimer.printElapsed("Toplam çalışma süresi");
    }
}
