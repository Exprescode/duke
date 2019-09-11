import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DeadlineCommand extends Command {
    private String desc;
    private Calendar by;

    /**
     * DeadlineCommand constructor init DeadlineCommand obj & variables.
     * @param desc Deadline description.
     * @param by Deadline date time.
     */
    public DeadlineCommand(String desc, Calendar by){
        this.desc = desc;
        this.by = by;
    }

    /**
     * Create Deadline object & add it to TaskList.
     * @param tasks                 list of task required for command processing.
     * @param ui                    Object interact with user console.
     * @param storage               Object interact with data file.
     * @throws DukeBoundException   Date time argument has expired.
     */
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

    /**
     * Proceed to exit program.
     * @return allowed to exit program if true.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
