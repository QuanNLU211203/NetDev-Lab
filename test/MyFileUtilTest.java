import junit.framework.TestCase;
import model.Student;
import model.Subject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFileUtilTest extends TestCase {
    @Override
    public void setUp() throws IOException {
        File root = new File("testresource");

        File file1 = new File("testresource\\a1.txt");

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
        file1.createNewFile();
        dir1_1.mkdirs();
        dir1_2.mkdirs();
        dir1_2_1.mkdirs();
        file2.createNewFile();
        file3.createNewFile();
        file4.createNewFile();
        dir2.mkdirs();
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
        File file1 = new File("testresource\\a.txt");
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

    public void testDirTree(){ // Show case !!!
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

    public void testFileCopy(){
        new MyFileUtil().fileCopy("D:\\ZZ\\src.txt", "D:\\ZZ\\des.txt", false);
    }

    public void testFolderCopy(){
        new MyFileUtil().folderCopy("testresource", "D:\\YY", false);
    }

    public void testFileSplit(){
        new MyFileUtil().fileSplit("D:\\YY\\news.txt", "D:\\YY", 1024);
    }

    public void testFileJoin(){
        new MyFileUtil().fileJoin("D:\\YY\\newsjoin.txt",
                "D:\\YY\\news-P1",
                "D:\\YY\\news-P2",
                "D:\\YY\\news-P3",
                "D:\\YY\\news-P4",
                "D:\\YY\\news-P5",
                "D:\\YY\\news-P6",
                "D:\\YY\\news-P7");
    }

    public void testFileType(){
        System.out.println(new MyFileUtil().fileType("abc.txt"));
    }

    public void testWriteStudentList(){
        Subject subject1 = new Subject(214321, "Lập trình cơ bản", 4);
        Subject subject2 = new Subject(214231, "Cấu trúc máy tính", 2);
        Subject subject3 = new Subject(214242, "Nhập môn hệ điều hành", 3);
        Subject subject4 = new Subject(214331, "Lập trình nâng cao", 4);
        Subject subject5 = new Subject(214241, "Mạng máy tính cơ bản", 3);
        List<Subject> subjects1 = Arrays.asList(subject1, subject2, subject3);
        List<Subject> subjects2 = Arrays.asList(subject2, subject3, subject4);
        List<Subject> subjects3 = Arrays.asList(subject1, subject3, subject5);

        Student student1 = new Student(21130494, "Trần Minh Quân", subjects1);
        Student student2 = new Student(18130494, "Nguyễn Siu", subjects2);
        Student student3 = new Student(17030300, "Phạm Chi", subjects3);
        List<Student> students = Arrays.asList(student1, student2, student3);

        new MyFileUtil().writeStudentList(students, "D:\\YY\\students.txt");
    }

    public void testReadStudentList(){
        System.out.println(new MyFileUtil().readStudentList("D:\\YY\\students.txt").toString());
    }
}
