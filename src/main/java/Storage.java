import java.io.*;
import java.util.ArrayList;

public class Storage {
    private String path;

    /**
     * Storage class constructor init object variable.
     * @param path Path of data file to be read.
     */
    public Storage(String path){
        this.path = path;
    }

    /**
     * Write and save TaskList into a file.
     * @param data  TaskList to be saved.
     * @return  If successful, exit code 0 will be returned. Otherwise, negative integer will be returned.
     */
    public int store(TaskList data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this.path);
        } catch (FileNotFoundException e) {
            return -1;
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            return -2;
        }
        try {
            oos.writeObject(data.getSerializable());
        } catch (IOException e) {
            return -3;
        }
        try {
            oos.close();
        } catch (IOException e) {
            return -4;
        }
        try {
            fos.close();
        } catch (IOException e) {
            return -5;
        }
        return 0;
    }

    /**
     * Read saved TaskList file into Task ArrayList.
     * @return  Serializable object to be initiated in TaskList.
     * @throws ClassNotFoundException   Data in the object is not Task ArrayList.
     * @throws IOException  Invalid path or no read permission or I/O operation is interrupted.
     */
    public ArrayList<Task> load() throws ClassNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(this.path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Task> holder = (ArrayList<Task>) ois.readObject();
        ois.close();
        fis.close();
        return holder;
    }
}

//"C:\\Users\\JH\\Documents\\GitHub\\duke\\data\\duke.txt"