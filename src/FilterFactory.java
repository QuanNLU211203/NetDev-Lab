import java.io.File;
import java.io.FilenameFilter;

public class FilterFactory {
    public FilenameFilter createFilter(String ext){
        return new FilenameFilter() {
            String ext = null;
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(ext);
            }

            public FilenameFilter setExt(String ext){
                this.ext = "." + ext;
                return this;
            }
        }.setExt(ext);
    }
}
