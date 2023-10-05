import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

public class MyFileUtilTest extends TestCase {
    @Override
    public void setUp() throws IOException {
        File root = new File("testresource");

        File file1 = new File("testresource\\file1.txt");

        File dir1 = new File("testresource\\dir1");
        File file2 = new File("testresource\\dir1\\file2.txt");
        File file3 = new File("testresource\\dir1\\file3.txt");
        File dir1_1 = new File("testresource\\dir1\\dir1_1");
        File file4 = new File("testresource\\dir1\\dir1_1\\file4.txt");
        File dir1_2 = new File("testresource\\dir1\\dir1_2");
        File dir1_2_1 = new File("testresource\\dir1\\dir1_2\\dir1_2_1");

        File dir2 = new File("testresource\\dir2");
        File file5 = new File("testresource\\dir2\\file5.txt");
        File file6 = new File("testresource\\dir2\\file6.txt");
        File file7 = new File("testresource\\dir2\\file7.txt");

        root.mkdirs();
        dir1.mkdirs();
        dir2.mkdirs();
        dir1_1.mkdirs();
        dir1_2.mkdirs();
        dir1_2_1.mkdirs();
        file1.createNewFile();
        file2.createNewFile();
        file3.createNewFile();
        file4.createNewFile();
        file5.createNewFile();
        file6.createNewFile();
        file7.createNewFile();
    }

    @Override
    public void tearDown(){
        new MyFileUtil().delete("testresource");
    }

    public void testDelete(){
        new MyFileUtil().delete("testresource");
        assertFalse(new File("testresource").exists());
    }

    public void testDeleteFileOnly(){
        new MyFileUtil().deleteFileOnly("testresource");

        File root = new File("testresource");
        File file1 = new File("testresource\\file1.txt");
        File dir1 = new File("testresource\\dir1");
        File file2 = new File("testresource\\dir1\\file2.txt");
        File file3 = new File("testresource\\dir1\\file3.txt");
        File dir1_1 = new File("testresource\\dir1\\dir1_1");
        File file4 = new File("testresource\\dir1\\dir1_1\\file4.txt");
        File dir1_2 = new File("testresource\\dir1\\dir1_2");
        File dir1_2_1 = new File("testresource\\dir1\\dir1_2\\dir1_2_1");
        File dir2 = new File("testresource\\dir2");
        File file5 = new File("testresource\\dir2\\file5.txt");
        File file6 = new File("testresource\\dir2\\file6.txt");
        File file7 = new File("testresource\\dir2\\file7.txt");

        boolean condition1 = root.exists() && dir1.exists() && dir2.exists()
                            && dir1_1.exists() && dir1_2.exists() && dir1_2_1.exists();
        boolean condition2 = !file1.exists() && !file2.exists() && !file3.exists()
                            && !file4.exists() && !file5.exists() && !file6.exists()
                            && !file7.exists();

        assertTrue(condition1 && condition2);
    }

    public void testFindFirst(){
        new MyFileUtil().findFirst("testresource", "dir");
    }

    public void testDirTree(){
        new MyFileUtil().dirTree("testresource");
    }

    public void testDirStat(){
        System.out.println(new MyFileUtil().dirStat("D:\\ZZ"));
    }

    public void testFindAll(){
        new MyFileUtil().findAll("D:\\Test", "txt", "docx", "pdf");
    }

    public void testDeleteAll(){
        new MyFileUtil().deleteAll("D:\\Test", "txt", "docx", "pdf");
    }
}
