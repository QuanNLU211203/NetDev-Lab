import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

public class MyFileUtil {

    /**
     * Xóa file, thư mục bất kì.
     * @param path đường dẫn của file / thư mục
     * @return true nếu xóa thành công, false nếu không thành công hoặc file không tồn tại.
     */
    public boolean delete(String path){
        File file = new File(path);
        // Kiểm tra file tồn tại hay chưa
        if(!file.exists()){
            return false;
        }

        //Trong trường hợp là file thì có thể delete được
        if(file.isFile()){
            return file.delete();
        }

        //Trường hợp đó là folder thì phải delete các file bên trong trước
        for(String childPath : file.list()){
            if(delete(path + "\\" + childPath) == false){
                return false;
            }
        }

        return file.delete();
    }

    /**
     * Xóa file, nếu đường dẫn là thư mục thì chỉ xóa file đệ qui bên trong, giữ nguyên cấu trúc thư mục
     * @param path đường dẫn file/thư mục
     * @return true nếu delete thành công, false nếu không delete được hoặc file không tồn tại
     */
    public boolean deleteFileOnly(String path){
        File file = new File(path);
        // Kiểm tra file tồn tại hay chưa
        if(!file.exists()){
            return false;
        }

        //Trong trường hợp là file thì có thể delete được
        if(file.isFile()){
            return file.delete();
        }

        //Trường hợp đó là folder thì phải delete các file bên trong, giữ lại thư mục
        for(String childPath : file.list()){
            if(deleteFileOnly(path + "\\" + childPath) == false){
                return false;
            }
        }

        return true;
    }

    public boolean findFirst(String path, String pattern){
        try {
            File dir = new File(path);
            if(!dir.exists() || dir.isFile()){
                System.out.println(dir + " không tồn tại hoặc không phải là thư mục");
                return false;
            }

            for(File subFile : dir.listFiles()){
                if(subFile.getName().contains(pattern)){
                    System.out.println(subFile.getCanonicalPath());
                    return true;
                }
            }

            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dirTree(String path){
        File file = new File(path);
        if(!file.exists()){
            System.out.println("File hoặc thư mục không tồn tại");
        }
        printTree(path, 0);
    }

    private void printTree(String path, int height){
        File file = new File(path);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < height; i++){
            builder.append("\t");
        }
        builder.append(file.getName());
        System.out.println(builder.toString());
        if(file.isDirectory()){
            for(String childName : file.list()){
                printTree(path + "\\" + childName, height + 1);
            }
        }
    }

    public long dirStat(String path){
        File file = new File(path);
        if(!file.exists()){
            System.out.println("Không tồn tại file: " + path);
            return -1;
        }

        if(file.isFile()){
            return file.length();
        }
        else{
            long sum = 0;
            for(String subName : file.list()){
                sum += dirStat(path + "/" + subName);
            }
            return sum;
        }
    }

    public void findAll(String path, String... exts){
        try {
            File dir = new File(path);
            if(!dir.exists() || dir.isFile()){
                System.out.println(dir + " không tồn tại hoặc không phải là thư mục");
                return;
            }

            for(String ext : exts){
                FilenameFilter filter = new FilterFactory().createFilter(ext);
                for(File file : dir.listFiles(filter)){
                    System.out.println(file.getCanonicalPath());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(String path, String... exts){
        File dir = new File(path);
        if(!dir.exists() || dir.isFile()){
            System.out.println(dir + " không tồn tại hoặc không phải là thư mục");
            return;
        }

        for(String ext : exts){
            FilenameFilter filter = new FilterFactory().createFilter(ext);
            for(File file : dir.listFiles(filter)){
                file.delete();
            }
        }
    }

//    public boolean fileCopy(String sFile, String desFile, String moved){
//        try {
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(sFile));
//            byte[] buffer = new byte[100];
//            while
//
//
//            return false;
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
