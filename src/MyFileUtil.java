import java.io.File;
import java.io.IOException;

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
}
