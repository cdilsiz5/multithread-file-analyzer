package analyzer;

public class FileStats {

    private int lineCount;
    private long charCount;

    public FileStats() {
        // Boş yapıcı metot (constructor)
    }

    public FileStats(int lineCount, long charCount) {
        this.lineCount = lineCount;
        this.charCount = charCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public long getCharCount() {
        return charCount;
    }

    public void setCharCount(long charCount) {
        this.charCount = charCount;
    }
}