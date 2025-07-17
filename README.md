# 🧵 Java Multithreaded File Analyzer Project

This project demonstrates the use of **Java multithreading**, file I/O, concurrency utilities, and archiving mechanisms. It processes multiple `.txt` files in parallel threads and compresses them into a `.zip` file after analysis.

---

## 📌 Project Purpose

- Learn and implement multithreaded file processing in Java.
- Apply thread-safe data sharing (`ConcurrentHashMap`).
- Practice working with `Runnable`, `Thread`, and `join()`.
- Measure performance of operations with `System.nanoTime()`.
- Safely archive files using `ZipOutputStream`.

---

## ⚙️ Technologies Used

- Java 17
- Maven
- Java Threads
---

## 🧪 How It Works

1. Reads `.txt` files from the `input/` directory (up to 10 files).
2. Each file is analyzed (line and character count) in its own thread.
3. Results are stored in a thread-safe structure.
4. After all threads complete, a separate thread zips all files into `output/archived-files.zip`.
5. Execution times are printed using a custom `Timer` class.

---

## 🚀 Running the Project

### Prerequisites
- Java 17+
- Maven installed

### Steps
```bash
git clone https://github.com/YOUR_USERNAME/multithread-file-analyzer.git
cd multithread-file-analyzer
mvn clean compile
java -cp target/classes Main
```

---

## 🧾 Sample Output

```
==> Toplam analiz edilecek dosya sayısı: 5
Thread-1 -> 'test1.txt' dosyasını analiz etmeye başladı.
Thread-2 -> 'test2.txt' dosyasını analiz etmeye başladı.
...
==== ANALİZ SONUÇLARI ====
test1.txt - 45 satır / 3120 karakter
test2.txt - 38 satır / 1987 karakter
Toplam: 83 satır / 5107 karakter
Dosyalar başarıyla ziplenmiştir: output/archived-files.zip
```

---

## 🧑‍🤝‍🧑 Contributors

| Name               | GitHub ID          |
|--------------------|--------------------|
| Cihan Dilsiz       | [cdilsiz5](https://github.com/cdilsiz5) |
| Muhammed Çavuşoğlu | [mcavus10](https://github.com/mcavus10) |
| Adem Hilmi Bozkurt | [ademhilmibozkurt](https://github.com/ademhilmibozkurt) |
| Furkan Boztepe     | [furkanboztepe](https://github.com/furkanboztepe) |

---

## 📁 Folder Structure

```
.
├── input/                # Input text files
├── output/               # Archived zip output
├── src/
│   └── main/java/
│       ├── Main.java
│       ├── analyzer/
│       ├── archiver/
│       └── util/
├── pom.xml
└── .gitignore
```

---

## 📈 Metrics and Design

- ✅ Max 10 concurrent threads
- ✅ Zip operation in a separate thread
- ✅ Execution timing via custom `Timer` utility
- ✅ Clean code, modular classes
- ✅ Git best practices: branches, PRs, merge approvals

---

## 📌 Screenshots & Documentation

Screenshots and workflow documentation are included in the `/docs` folder and project wiki.

---

## ✅ Status

✅ All requirements implemented and merged.
