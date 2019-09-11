public class DeleteCommand extends Command {
    private int index;

    /**
     * Constructor for DeleteCommand object.
     * @param index task index to delete from TaskList obj.
     */
    public DeleteCommand(int index){
        this.index = index;
    }

    /**
     * Delete task at index from TaskList.
     * @param tasks     list of task required for command processing.
     * @param ui        Object interact with user console.
     * @param storage   Object interact with data file.
     * @throws DukeBoundException   Argument not within command boundaries.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        String task_str = tasks.remove(index);
        if(task_str.length() < 1){
            throw new DukeBoundException("Delete argument is not a valid entry from list command.");
        }
        ui.show("Noted. I've removed this task:\n  " + task_str + "\nNow you have " + tasks.len() + " tasks in the list.");
        storage.store(tasks);
    }

    /**
     * Proceed to exit program.
     * @return Exit program if true.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
