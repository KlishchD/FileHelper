import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FileHelper {

    private static final Map<String, String> informationAboutCommands = new HashMap<>();

    private static final String COMMAND_NOT_FOUND_ERROR_TEXT = "Three is no such command: ";

    static {
        informationAboutCommands.put("find", "Returns all files and directories of given directory and it's subdirectories");
        informationAboutCommands.put("ls", "Returns List of all files and directories paths of given directory");
        informationAboutCommands.put("cat", "Returns String, which contains file's data");
        informationAboutCommands.put("rm", "Removes give file or directory");
        informationAboutCommands.put("mv", "Moves file or directory from path1 to path2");
        informationAboutCommands.put("cmp", "Compares two files");
        informationAboutCommands.put("help", "Command used to get information about commands");
    }

    private FileHelper(){}

    public static List<String> find(String path) {
        List<String> result = new ArrayList<>();

        File file = new File(path);

        if (!file.exists()) return result;

        Queue<File> fileQueue = new PriorityQueue<>();

        fileQueue.add(file);

        while (!fileQueue.isEmpty()) {
            File current = fileQueue.poll();
            result.add(current.getPath());

            if (current.isFile()) continue;

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

    public static @NotNull String cat(String path) throws IOException {
        File file = new File(path);

        StringBuilder result = new StringBuilder();

        FileInputStream input = new FileInputStream(file);

        int i;
        while ((i = input.read()) != -1) {
            result.append((char) i);
        }

        input.close();

        return result.toString();
    }

    public static boolean rm(String path) {
        return new File(path).delete();
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

    public static String help(String commandName) {
        return informationAboutCommands.getOrDefault(commandName, COMMAND_NOT_FOUND_ERROR_TEXT + commandName);
    }

}
