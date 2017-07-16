import java.io.File;

/**
 * Created by Boka on 15-Jul-17.
 */
public interface Communicator {
    public void onPartDownloadComplete(File file, int partNo);
}
