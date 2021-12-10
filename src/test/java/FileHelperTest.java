import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileHelperTest {

    @Test
    public void find_WrongPath_ReturnsEmptyList() {
        assertTrue(FileHelper.find("sdf").isEmpty());
    }

    @Test
    public void find_RightPath_ReturnsList() {
        String path = System.getProperty ("user.home") + "/Documents/FileHelper/find_test/A";

        List<String> expectedResult = Arrays.asList(path, path + "/aasd", path + "/.DS_Store", path + "/B1", path + "/C", path + "/V", path + "/B");
        List<String> actualResult = FileHelper.find(path);

        assertTrue(actualResult.size() == expectedResult.size() && actualResult.containsAll(expectedResult) && expectedResult.containsAll(actualResult));
    }

    @Test(expected = IOException.class)
    public void ls_WrongPath_ThrowsException() throws IOException {
        assertTrue(FileHelper.cat("Wrong path").isEmpty());
    }

    @Test
    public void ls_RightPath_ReturnsList() {
        String path = System.getProperty ("user.home") + "/Documents/FileHelper/find_test/A";

        List<String> expectedResult = Arrays.asList(path + "/aasd", path + "/.DS_Store", path + "/B1", path + "/C", path + "/V", path + "/B");
        List<String> actualResult = FileHelper.ls(path);

        assertTrue(actualResult.size() == expectedResult.size() && actualResult.containsAll(expectedResult) && expectedResult.containsAll(actualResult));
    }

    @Test(expected = IOException.class)
    public void cat_WrongPath_ThrowsException() throws IOException {
        FileHelper.cat("Wrong");
    }

    @Test
    public void cat_RightPath_ReturnsFileText() throws IOException {
        String expected = "Hello world \nIt works";
        assertEquals(expected, FileHelper.cat(System.getProperty("user.home") + "/Documents/FileHelper/cat_test/text.txt"));
    }

    @Test
    public void rm_WrongPath_ReturnsFalse() {
        assertFalse(FileHelper.rm(System.getProperty("user.home") + "/Documents/FileHelper/rm_test/asdf.txt"));
    }

    @Test
    public void rm_RemoveFile_DeletesFile() {
        String filePath = System.getProperty("user.home") + "/Documents/FileHelper/rm_test/a.txt";
        assertTrue(FileHelper.rm(filePath) && !(new File(filePath)).exists());
    }

    @Test
    public void rm_RemoveDirectory_DeletesDirectory() {
        String filePath = System.getProperty("user.home") + "/Documents/FileHelper/rm_test/A";
        assertTrue(FileHelper.rm(filePath) && !(new File(filePath)).exists());
    }

    @Test
    public void mv_WrongPath_ReturnsFalse() {
        assertFalse(FileHelper.mv(System.getProperty("user.home") + "/Documents/sadsfsdf/text.txt", System.getProperty("user.home") + "/Documents"));
    }

    @Test
    public void mv_MoveFile_MovesFile() throws IOException {
        String from = System.getProperty("user.home") + "/Documents/FileHelper/MV_Test/a.txt";
        String to = System.getProperty("user.home") + "/Documents/FileHelper/MV_Test/B/b.txt";
        String data = "Hello \nGood";

        assertTrue(FileHelper.mv(from, to) && !(new File(from)).exists() && readFromFile(to).equals(data));
    }

    @Test
    public void mv_MoveDirectory_MovesDirectory() {
        String from = System.getProperty("user.home") + "/Documents/FileHelper/MV_Test/A";
        String to = System.getProperty("user.home") + "/Documents/FileHelper/MV_Test/B/A";

        assertTrue(FileHelper.mv(from, to) && !(new File(from)).exists() && (new File(to)).exists());
    }

    @Test(expected = IOException.class)
    public void cmp_WrongPath_ThrowsException() throws IOException {
        FileHelper.cmp(System.getProperty("user.home") + "/Documents/FileHelper/cmp_test/a.txt", System.getProperty("user.home") + "/Documents/FileHelper/cmp_test/b.txt");
    }

    @Test
    public void cmp_CompareEqualFiles_ReturnTrue() throws IOException {
        assertTrue(FileHelper.cmp(System.getProperty("user.home") + "/Documents/FileHelper/cmp_test/c.txt", System.getProperty("user.home") + "/Documents/FileHelper/cmp_test/d.txt"));
    }

    @Test
    public void cmp_CompareUnEqualFiles_ReturnFalse() throws IOException {
        assertFalse(FileHelper.cmp(System.getProperty("user.home") + "/Documents/FileHelper/cmp_test/e.txt", System.getProperty("user.home") + "/Documents/FileHelper/cmp_test/f.txt"));
    }

    private String readFromFile(String path) throws IOException {
        StringBuilder buffer = new StringBuilder();
        File movedFile = new File(path);

        if (!movedFile.exists()) fail();

        FileInputStream fileInputStream = new FileInputStream(movedFile);
        int tmp;
        while ((tmp = fileInputStream.read()) != -1) {
            buffer.append((char)tmp);
        }
        return buffer.toString();
    }
}
