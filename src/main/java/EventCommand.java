import java.util.Calendar;

public class EventCommand extends Command {

    private String desc;
    private Calendar start, end;

    /**
     * EventCommand constructor for event without end date time.
     * @param desc  event description.
     * @param start event start date time.
     */
    public EventCommand(String desc, Calendar start) {
        this.desc = desc;
        this.start = start;
        this.end = null;
    }

    /**
     * EventCommand constructor for event with end date time.
     * @param desc  Event description.
     * @param start Event start date time.
     * @param end   Event end date time.
     */
    public EventCommand(String desc, Calendar start, Calendar end) {
        this.desc = desc;
        this.start = start;
        this.end = end;
    }

    /**
     * Create Event object and add to TaskList.
     * @param tasks                 list of task required for command processing.
     * @param ui                    Object interact with user console.
     * @param storage               Object interact with data file.
     * @throws DukeBoundException   Argument not within command boundaries.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        if (start.compareTo(Calendar.getInstance()) < 1) {
            throw new DukeBoundException("Event start date time argument must be earlier than current date time.");
        }
        if (end.compareTo(start) < 1) {
            throw new DukeBoundException("Event end date time argument must be earlier than start date time argument.");
        }
        Event new_event = new Event(desc, start, end);
        tasks.add(new_event);
        ui.show("Got it. I've added this task:\n  " + new_event.toString() + "\nNow you have " + tasks.len() + " tasks in the list.");
        storage.store(tasks);
    }

    /**
     * Return exit action proceed.
     * @return exit program if true.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
