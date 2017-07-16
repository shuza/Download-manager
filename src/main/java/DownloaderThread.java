import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Boka on 15-Jul-17.
 */
public class DownloaderThread extends Thread {
    private URL url;
    private Communicator listener;
    private int partNo;
    private int partLength;

    public DownloaderThread(Communicator listener, URL url, int partNo, int partLength) {
        this.listener = listener;
        this.url = url;
        this.partNo = partNo;
        this.partLength = partLength;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            inputStream.skip(partLength * partNo);

            String filePath = Util.FILE_ROOT_DIR + "Temp-" + partNo;
            File file = new File(filePath);
            FileOutputStream outputStream = new FileOutputStream(filePath);
            System.out.println("DownloaderThread\t==/\tDownload start..partNo: " + partNo);

            int read = -1;
            byte[] buffer = new byte[4096];
            for (int i = 0; i * buffer.length < partLength && (read = inputStream.read(buffer)) != -1; i++) {
                outputStream.write(buffer, 0, read);
            }

            inputStream.close();
            outputStream.close();
            System.out.println("DownloaderThread\t==/\tDownload complete..partNo: " + partNo);

            listener.onPartDownloadComplete(file, partNo);
        } catch (Exception e) {
            System.out.println("DownloaderThread\t==/\tError at partNo " + partNo);
            System.out.println("DownloaderThread\t==/\tError:  " + e.getMessage());
        }
    }
}
