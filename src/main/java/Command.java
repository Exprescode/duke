/**
 * Abstract class command
 */
public abstract class Command {

    /**
     * Start processing command.
     * @param tasks                 list of task required for command processing.
     * @param ui                    Object interact with user console.
     * @param storage               Object interact with data file.
     * @throws DukeBoundException   Argument not with command boundaries.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException;

    /**
     * Return exit action.
     * @return Exit program when true.
     */
    public abstract boolean isExit();
}
