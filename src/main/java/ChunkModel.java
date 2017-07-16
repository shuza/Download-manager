import java.io.File;

/**
 * Created by Boka on 15-Jul-17.
 */
public class ChunkModel implements Comparable<ChunkModel> {
    private File file;
    private int partNo;

    public ChunkModel(File file, int partNo) {
        this.file = file;
        this.partNo = partNo;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getPartNo() {
        return partNo;
    }

    public void setPartNo(int partNo) {
        this.partNo = partNo;
    }

    public int compareTo(ChunkModel chunkModel) {
        int position = ((ChunkModel) chunkModel).getPartNo();
        return this.partNo - position;
    }
}
