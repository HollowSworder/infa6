import java.io.*;
import java.util.*;

public class ClassicIOCacheWithLimit {
    
    Map<String, FileCacheEntry> cache;
    int maxSize;
    
    private static class FileCacheEntry {
        String content;
        long lastReadTime;
        long lastModifiedTimeAtRead;
        
        public FileCacheEntry(String content, long lastReadTime, long lastModifiedTimeAtRead) {
            this.content = content;
            this.lastReadTime = lastReadTime;
            this.lastModifiedTimeAtRead = lastModifiedTimeAtRead;
        }
    }
    
    public ClassicIOCacheWithLimit(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new HashMap<>();
    }
    
    public ClassicIOCacheWithLimit() {
        this(100);
    }
    
    public String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не существует: " + filePath);
        }
        
        String absolutePath = file.getAbsolutePath();
        long currentModifiedTime = file.lastModified();
        
        FileCacheEntry cachedEntry = cache.get(absolutePath);
        if (cachedEntry != null && isCacheValid(cachedEntry, currentModifiedTime)) {
            cachedEntry.lastReadTime = System.currentTimeMillis();
            return cachedEntry.content;
        }
        
        if (cache.size() >= maxSize) {
            removeOldestFile();
        }
        
        return updateCache(file, absolutePath, currentModifiedTime);
    }
    
    private boolean isCacheValid(FileCacheEntry cachedEntry, long currentModifiedTime) {
        return cachedEntry.lastModifiedTimeAtRead == currentModifiedTime;
    }
    
    private String updateCache(File file, String absolutePath, long currentModifiedTime) throws IOException {
        String content = readFileContent(file);
        long currentTime = System.currentTimeMillis();
        
        FileCacheEntry newEntry = new FileCacheEntry(content, currentTime, currentModifiedTime);
        cache.put(absolutePath, newEntry);
        
        return content;
    }
    
    private String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        char[] buffer = new char[8192]; // 8KB буфер
        
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {
            
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                content.append(buffer, 0, charsRead);
            }
        }
        
        return content.toString();
    }
    
    private void removeOldestFile() {
        if (cache.isEmpty()) return;
        
        String oldestKey = null;
        long oldestTime = Long.MAX_VALUE;
        
        for (Map.Entry<String, FileCacheEntry> entry : cache.entrySet()) {
            if (entry.getValue().lastReadTime < oldestTime) {
                oldestTime = entry.getValue().lastReadTime;
                oldestKey = entry.getKey();
            }
        }
        
        if (oldestKey != null) {
            cache.remove(oldestKey);
        }
    }
    
    public void invalidate(String filePath) {
        String absolutePath = new File(filePath).getAbsolutePath();
        cache.remove(absolutePath);
    }
    
    public void invalidateAll() {
        cache.clear();
    }
    
    public boolean isCached(String filePath) {
        String absolutePath = new File(filePath).getAbsolutePath();
        FileCacheEntry entry = cache.get(absolutePath);
        if (entry != null) {
            File file = new File(filePath);
            return file.exists() && isCacheValid(entry, file.lastModified());
        }
        return false;
    }
    
    public int getCachedFilesCount() {
        return cache.size();
    }
    
    public long getCacheSizeInMemory() {
        long totalSize = 0;
        for (FileCacheEntry entry : cache.values()) {
            totalSize += entry.content.length() * 2; 
        }
        return totalSize;
    }
    
    public void printCacheStats() {
        System.out.println("=== СТАТИСТИКА КЭША ===");
        System.out.println("Количество файлов: " + getCachedFilesCount() + " / " + maxSize);
        System.out.println("Размер в памяти: " + getCacheSizeInMemory() + " байт");
        System.out.println("Закэшированные файлы:");
        
        for (Map.Entry<String, FileCacheEntry> entry : cache.entrySet()) {
            FileCacheEntry fileEntry = entry.getValue();
            int size = fileEntry.content.length() * 2;
            System.out.printf("  %s [%d байт, прочитан: %tF %tT]%n",
                    entry.getKey(),
                    size,
                    fileEntry.lastReadTime,
                    fileEntry.lastReadTime);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("ТЕСТ");
        
        try {
            createTestFiles();
            
            ClassicIOCacheWithLimit cache = new ClassicIOCacheWithLimit(3);
            
            System.out.println("\nЧтение из диска");
            long startTime = System.currentTimeMillis();
            
            String content1 = cache.readFile("test1.txt");
            String content2 = cache.readFile("test2.txt");
            String content3 = cache.readFile("test3.txt");
            
            long diskReadTime = System.currentTimeMillis() - startTime;
            System.out.println("Время чтения с диска: " + diskReadTime + " мс");
            System.out.println("Файлов в кэше: " + cache.getCachedFilesCount());
            
            System.out.println("\nЧтение из кэша");
            startTime = System.currentTimeMillis();
            
            String cachedContent1 = cache.readFile("test1.txt");
            String cachedContent2 = cache.readFile("test2.txt");
            
            long cacheReadTime = System.currentTimeMillis() - startTime;
            System.out.println("Время чтения из кэша: " + cacheReadTime + " мс");
            System.out.println("Содержимое совпадает: " + content1.equals(cachedContent1));
            
            System.out.println("\nВытеснение старых файлов");
            cache.readFile("test4.txt");
            
            System.out.println("Файлов в кэше после добавления 4-го: " + cache.getCachedFilesCount());
            System.out.println("test1.txt в кэше: " + cache.isCached("test1.txt"));
            System.out.println("test4.txt в кэше: " + cache.isCached("test4.txt"));
            
        System.out.println("\nСравнение производительности");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            cache.readFile("test1.txt");
        }
        long diskTime = System.currentTimeMillis() - startTime;
  
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            cache.readFile("test1.txt");
        }
        long cacheTime = System.currentTimeMillis() - startTime;
        
        System.out.println("100 чтений с диска: " + diskTime + " мс");
        System.out.println("100 чтений из кэша: " + cacheTime + " мс");
        System.out.println("Ускорение: " + (double)diskTime/cacheTime + "x");
            
        } catch (Exception e) {
            System.err.println("Ошибка при тестировании: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanupTestFiles();
        }
    }
    
    private static void createTestFiles() throws IOException {
        String[] testContents = {
            "1файл",
            "2",
            "3",
            "Ч"
        };
        
        for (int i = 0; i < testContents.length; i++) {
            try (FileWriter writer = new FileWriter("test" + (i + 1) + ".txt")) {
                writer.write(testContents[i]);
            }
        }
        System.out.println("Создано " + testContents.length + " тестовых файлов");
    }
    
    private static void cleanupTestFiles() {
        for (int i = 1; i <= 4; i++) {
            File file = new File("test" + i + ".txt");
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Удален test" + i + ".txt");
                }
            }
        }
    }
}