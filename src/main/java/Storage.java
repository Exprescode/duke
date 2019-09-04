import java.io.*;
import java.util.ArrayList;

public class Storage {
    private String path;
    public Storage(String path){
        this.path = path;
    }
    // FileOutputStream():FileNotFoundException - If the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
    // ObjectOutputStream():IOException - If an I/O error occurs while writing stream header.
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
    // FileInputStream():FileNotFoundException - If the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
    // ObjectInputStream():IOException - If an I/O error occurs while reading stream header.
    // ObjectInputStream.readObject():ClassNotFoundException - Class of a serialized object cannot be found.
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