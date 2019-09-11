import java.util.ArrayList;
import java.util.regex.Pattern;

public class TaskList {
    private ArrayList<Task> list;

    /**
     * Initialize empty Tasklist.
     */
    public TaskList(){
        list = new ArrayList<Task>();
    }

    /**
     * Initialize populated TaskList.
     * @param list List of tasks to be add into TaskList.
     */
    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    /**
     * Add task into TaskList.
     * @param t Task to be added into TaskList.
     */
    public void add(Task t){
        list.add(t);
    }

    /**
     * Remove Task at a given index from TaskList.
     * @param index Index of Task to be remove from TaskList.
     * @return  Task details in text as String.
     */
    public String remove(int index){
        if(index < 0 || index >= list.size()){
            return "";
        }
        String task_str = list.get(index).toString();
        list.remove(index);
        return task_str;
    }

    /**
     * List all Tasks' details in Tasklist in text.
     * @return  All tasks' details in text as String.
     */
    public String list(){
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Task t : list) {
            sb.append((++count) + "." + t.toString() + "\n");
        }
        sb.deleteCharAt(sb.length() - 1); //Remove extra newline.
        return sb.toString();
    }

    /**
     * Search matching keywords given with all tasks description in TaskList.
     * @param query Keywords to search for in task description.
     * @return  All matching tasks details in text as String.
     */
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

    /**
     * Retrieve serializable object of TaskList.
     * @return Serializable object representing TaskList.
     */
    public ArrayList getSerializable(){
        return list;
    }

    /**
     * Get length of TaskList.
     * @return Length of TaskList.
     */
    public int len(){
        return list.size();
    }

    /**
     * Mark task at given index as done in TaskList.
     * @param index Index of tasks in TaskList to be marked done.
     * @return Task's details at given index in text as String.
     */
    public String tick(int index){
        if(index < 0 || index >= list.size()){
            return "";
        }
        Task curr_task = list.get(index);
        curr_task.setDone();
        return curr_task.toString();
    }
}
