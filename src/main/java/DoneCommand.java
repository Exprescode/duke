public class DoneCommand extends Command {
    private int index;

    /**
     * DoneCommand constructor to init DoneCommand object & variables.
     * @param index Task index in TaskList to be marked done.
     */
    public DoneCommand(int index){
        this.index = index;
    }

    /**
     * Mark index task in TaskList to done.
     * @param tasks                 list of task required for command processing.
     * @param ui                    Object interact with user console.
     * @param storage               Object interact with data file.
     * @throws DukeBoundException   TaskList is empty.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        String task_str = tasks.tick(index);
        if(task_str.length() < 1){
            throw new DukeBoundException("Done argument is not a valid entry from list command.");
        }
        ui.show("Nice! I've marked this task as done:\n " + task_str);
        storage.store(tasks);
    }

    /**
     * Proceed to exit program.
     * @return Allowed to exit program if true.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
