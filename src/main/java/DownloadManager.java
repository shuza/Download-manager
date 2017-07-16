import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Boka on 15-Jul-17.
 */
public class DownloadManager {
    private static String downloadUrl = "http://hindisongs.fusionbd.com/downloads/download.php?file=mp3/hindi/Munna_Michael/03.Beat_It_Bujuriya-Munna_Michael_FusionBD.Com.mp3";

    public void startDownload() {
        URL url = Util.verifyURL(downloadUrl);

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = null;
            String fileName = url.getFile();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            inputStream = connection.getInputStream();

            FileOutputStream outputStream = new FileOutputStream("E:\\Download" + File.separator + fileName);

            System.out.println("Main\t==/\tstarting time: " + Util.getCurrentTime());

            int read = -1;
            byte[] buffer = new byte[4096];
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            inputStream.close();
            outputStream.close();

            System.out.println("Main\t==/\tfinish time: " + Util.getCurrentTime());
            System.out.println("Main\t==/\tComplete  @@@");

        } catch (Exception e) {
            System.out.println("Main\t==/\tError:  " + e.getMessage());
        }
    }

}
