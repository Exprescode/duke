import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DeadlineCommand extends Command {
    private String desc;
    private Calendar by;
    public DeadlineCommand(String desc, Calendar by){
        this.desc = desc;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        if(by.compareTo(Calendar.getInstance()) < 1){
            throw new DukeBoundException("Date time has already expired.");
        }
        Deadline new_deadline = new Deadline(desc, by);
        tasks.add(new_deadline);
        ui.show("Got it. I've added this task:\n  " + new_deadline.toString() + "\nNow you have " + tasks.len() + " tasks in the list.");
        storage.store(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
