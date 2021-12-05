import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static void copyFolder(String source, String destination)
            throws IOException {
        File src = new File(source);
        File dest = new File(destination);
        if (src.isDirectory()) {

            if (!dest.exists()) {
                dest.mkdir();
            }

            String[] files = src.list();

            for (String file : files) {
                System.out.println(file);


                copyFolder(source + "/" + file, destination + "/" + file);
            }

        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
        }
    }
}
