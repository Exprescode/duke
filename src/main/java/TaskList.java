import java.util.ArrayList;
import java.util.regex.Pattern;

public class TaskList {
    private ArrayList<Task> list;

    public TaskList(){
        list = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    public void add(Task t){
        list.add(t);
    }

    public String remove(int index){
        if(index < 0 || index >= list.size()){
            return "";
        }
        String task_str = list.get(index).toString();
        list.remove(index);
        return task_str;
    }

    public String list(){
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Task t : list) {
            sb.append((++count) + "." + t.toString() + "\n");
        }
        sb.deleteCharAt(sb.length() - 1); //Remove extra newline.
        return sb.toString();
    }

    public String find(String query){
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile(".*" + query.replaceAll(" ", ".*") + ".*", Pattern.CASE_INSENSITIVE);
        int count = 0;
        for (Task t : list) {
            if (pattern.matcher(t.getDesc()).find()) {
                sb.append(++count + "." + t.toString() + "\n");
            }
        }
        if(count != 0){
            sb.deleteCharAt(sb.length() - 1); //Remove extra newline.
            return sb.toString();
        }
        return "";
    }

    public ArrayList getSerializable(){
        return list;
    }

    public int len(){
        return list.size();
    }

    public String tick(int index){
        if(index < 0 || index >= list.size()){
            return "";
        }
        Task curr_task = list.get(index);
        curr_task.setDone();
        return curr_task.toString();
    }
}
