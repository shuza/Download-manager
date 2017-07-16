import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Boka on 15-Jul-17.
 */
public class Util {
    public static String FILE_ROOT_DIR = "E:\\Download" + File.separator;
    public static int totalParts = 6;
    public static ArrayList<ChunkModel> completeParts = new ArrayList<ChunkModel>();

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static URL verifyURL(String url) {
        if (!url.toLowerCase().startsWith("http://")) {
            return null;
        }
        URL verifyURL = null;
        try {
            verifyURL = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("verifyURL\t==/\tError: " + e.getMessage());
        }
        return verifyURL;
    }

}
