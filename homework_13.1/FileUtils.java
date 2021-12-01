import java.io.File;
import java.util.Arrays;

public class FileUtils {

    public static long calculateFolderSize(String path) {
        File folder = new File(path);
        long folderSize = 0;
        long subDirSize = 0;
        try {
            for (int i = 0; i < folder.listFiles().length; i++) {
                File file[] = folder.listFiles();
                if (!(file[i].isDirectory())) {
                    folderSize += file[i].length();
                } else for (String insideSubDir : folder.list()) {
                    File subDir = new File(path + "\\" + insideSubDir);
                    if (subDir.isDirectory()) {
                        for (File subFile : subDir.listFiles()) {
                                subDirSize += subFile.length();
                                System.out.println(subDirSize);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return subDirSize + folderSize;
    }
}
