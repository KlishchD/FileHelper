import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FileHelper {

    private FileHelper(){}

    public static List<String> find(String path) {
        List<String> result = new ArrayList<>();

        File file = new File(path);

        if (!file.exists()) {
            return result;
        }

        Queue<File> fileQueue = new PriorityQueue<>();

        fileQueue.add(file);

        while (!fileQueue.isEmpty()) {
            File current = fileQueue.poll();

            if (current.isFile()) {
                result.add(current.getPath());
                continue;
            }

            result.add(current.getPath());

            File[] tmp = current.listFiles();

            if (tmp == null) continue;

            fileQueue.addAll(Arrays.asList(tmp));
        }

        return result;
    }

    public static List<String> ls(String path) {
        List<String> result = new ArrayList<>();

        File file = new File(path);

        if (!file.exists()) return result;
        if (file.isFile()) return result;

        File[] tmp = file.listFiles();

        if (tmp == null) return result;

        for (File f : tmp) {
            result.add(f.getPath());
        }

        return result;
    }

    public static String cat(String path) throws FileNotFoundException {
        File file = new File(path);

        StringBuilder result = new StringBuilder();

        try (FileInputStream input = new FileInputStream(file)) {
            int i;

            while ((i = input.read()) != -1) {
                result.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static boolean rm(String path) {
        File file = new File(path);

        return file.delete();
    }

    public static boolean mv(String from, String to) {
        return (new File(from)).renameTo(new File(to));
    }

    public static boolean cmp(String path1, String path2) throws IOException {
        FileInputStream fileInputStream1 = new FileInputStream(path1);

        StringBuilder buffer1 = new StringBuilder();
        int tmp;
        while ((tmp = fileInputStream1.read()) != -1) {
            buffer1.append((char) tmp);
        }

        FileInputStream fileInputStream2 = new FileInputStream(path2);

        StringBuilder buffer2 = new StringBuilder();
        while ((tmp = fileInputStream2.read()) != -1) {
            buffer2.append((char) tmp);
        }

        return buffer1.toString().equals(buffer2.toString());
    }

}
