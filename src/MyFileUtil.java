import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
            if(delete(path + "/" + childPath) == false){
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
            if(deleteFileOnly(path + "/" + childPath) == false){
                return false;
            }
        }

        return true;
    }

    /**
     * Tìm và in ra console file/thư mục đầu tiên có tên chứa một chuỗi con cho trước
     * @param path thư mục cần tìm
     * @param pattern chuỗi con
     * @return true nếu tìm được và sẽ in ra console.
     */
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

    /**
     * In ra console cây thư mục từ một thư mục cho trước.
     * @param path đường dẫn thư mục
     */
    public void dirTree(String path){
        File file = new File(path);
        if(!file.exists()){
            System.out.println("File hoặc thư mục không tồn tại");
        }
        printTree(file, 0, new LinkedList<Boolean>());
    }

    /**
     *
     * @param file
     * @param height cấp bậc của file tính từ root
     * @param vlp "Vertical Line Position":
     *            là list boolean có độ dài tùy thuộc.
     *            Nếu là false thì không có đường kẻ dọc.
     *            Nếu là true thì ngược lại.
     */
    private void printTree(File file, int height, List<Boolean> vlp){
        StringBuilder builder = new StringBuilder();
        int count = vlp.size();
        for(boolean position : vlp){
            if(count == 1){
                break;
            }
            if(position == true){
                builder.append("|\t");
            }
            else {
                builder.append('\t');
            }
            count--;
        }
        if(height != 0){
            builder.append("+-");
        }
        builder.append(file.getName());
        System.out.println(builder.toString());

        if(file.isDirectory()){
            File[] subFiles = file.listFiles();
            for(int i = 0; i < subFiles.length; i++){
                List<Boolean> subVCP = new LinkedList<>(vlp);
                if(i != subFiles.length - 1){
                    subVCP.add(true);
                }
                else{
                    subVCP.add(false);
                }
                printTree(subFiles[i], height + 1, subVCP);
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

    public boolean fileCopy(String sFile, String desFile, boolean moved){
        try {
            int size = 1024;
            byte[] buffer = new byte[size];
            InputStream inputStream = new BufferedInputStream(new FileInputStream(sFile), size);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(desFile));

            int bytesReaded;
            while ((bytesReaded = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesReaded);
            }

            inputStream.close();
            outputStream.close();

            if(moved){
                delete(sFile);
            }
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public boolean folderCopy(String sFolder, String destFolder, boolean moved){
        File sDir = new File(sFolder);
        File destDir = new File(destFolder);

        if(!sDir.exists()){
            return false;
        }

        if(sDir.isFile()){
            return fileCopy(sFolder, destFolder, moved);
        }
        else{
            if(!destDir.exists()){
                destDir.mkdir();
            }
            String[] childs = sDir.list();
            for(String child : childs){
                if(folderCopy(sFolder + "/" + child, destFolder + "/" + child, moved) == false){
                    return false;
                }
            }

            return true;
        }
    }

    public void copyAll(String sDir, String dDir, String... ext){

    }

    public void fileSplit(String filePath, String desFolder, int bytesPerPart){
        try {
            File file = new File(filePath);
            String filename = file.getName();
            int extIndex = filename.lastIndexOf('.');
            String name = filename.substring(0, extIndex);
            String ext = filename.substring(extIndex);

            //Setup công cụ
            long numberOfPart = file.length()/bytesPerPart + 1;
            InputStream is = new BufferedInputStream(new FileInputStream(file), bytesPerPart);
            byte[] buffer = new byte[bytesPerPart];

            for(int i = 1; i <= numberOfPart; i++){
                if(is.available() != 0){
                    OutputStream os = new BufferedOutputStream(
                            new FileOutputStream(desFolder + "/" + name + "-P" + i + ext));
                    int bytesRead = is.read(buffer);
                    os.write(buffer, 0, bytesRead);
                    os.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String fileType(String fname){
        int end = fname.lastIndexOf(".");
        return (end != -1) ? (fname.substring(end + 1)):("None");
    }
}
