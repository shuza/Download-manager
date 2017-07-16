import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Boka on 15-Jul-17.
 */
public class DownloadManagerMultiPart implements Communicator {
    private String downloadUrl = "http://hindisongs.fusionbd.com/downloads/download.php?file=mp3/hindi/Munna_Michael/03.Beat_It_Bujuriya-Munna_Michael_FusionBD.Com.mp3";
    private String fileName;

    public void startDownload() {
        URL url = Util.verifyURL(downloadUrl);

        try {
            System.out.println("DownloadManagerMultiPart\t==/\tstarting time: " + Util.getCurrentTime());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            fileName = url.getFile();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            InputStream inputStream = connection.getInputStream();
            int partLength = connection.getContentLength() / Util.totalParts;
            if (connection.getContentLength() % Util.totalParts != 0) {
                Util.totalParts++;
            }

            System.out.println("DownloadManagerMultiPart\t==/\tcontent length:  " + connection.getContentLength());
            System.out.println("DownloadManagerMultiPart\t==/\ttotal part:  " + Util.totalParts);
            System.out.println("DownloadManagerMultiPart\t==/\tpart length:  " + partLength);

            for (int i = 0; i < Util.totalParts; i++) {
                new DownloaderThread(this, url, i, partLength).start();
            }


            System.out.println("DownloadManagerMultiPart\t==/\tfinish time: " + Util.getCurrentTime());
            System.out.println("DownloadManagerMultiPart\t==/\tComplete  @@@");
        } catch (Exception e) {
            System.out.println("DownloadManagerMultiPart\t==/\tError:  " + e.getMessage());
        }
    }

    public void onPartDownloadComplete(File file, int partNo) {
        Util.completeParts.add(new ChunkModel(file, partNo));
        System.out.println("on_part_download_complete\t==/part_no:  " + partNo);
        System.out.println("on_part_download_complete\t==/complete parts count:  " + Util.completeParts.size());
        if (Util.completeParts.size() == Util.totalParts) {
            processFile();
        }
    }

    public void processFile() {
        System.out.println("process_file\t==/\tstart...");
        Collections.sort(Util.completeParts);
        try {
            FileOutputStream outputStream = new FileOutputStream(Util.FILE_ROOT_DIR + fileName);
            for (int i = 0; i < Util.completeParts.size(); i++) {
                File file = Util.completeParts.get(i).getFile();
                FileInputStream inputStream = new FileInputStream(Util.FILE_ROOT_DIR + "Temp-" + i);

                int read = -1;
                byte[] buffer = new byte[4096];
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                inputStream.close();
            }
            outputStream.close();
            System.out.println("process_file\t==/\tDownload finish...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DownloadManagerMultiPart\t==/\tfinish time: " + Util.getCurrentTime());

    }

}
