public class TodoCommand extends Command {
    private String desc;

    /**
     * TodoCommand constructor init TodoCommand obj & variable.
     * @param desc  Todo object description.
     */
    public TodoCommand(String desc){
        this.desc = desc;
    }

    /**
     * Create 1 todo object & add to TaskList.
     * @param tasks                 list of task required for command processing.
     * @param ui                    Object interact with user console.
     * @param storage               Object interact with data file.
     * @throws DukeBoundException   No possible boundary exception.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        Todo new_todo = new Todo(desc);
        tasks.add(new_todo);
        ui.show("Got it. I've added this task:\n  " + new_todo.toString() + "\nNow you have " + tasks.len() + " tasks in the list.");
        storage.store(tasks);
    }

    /**
     * Proceed with exit action.
     * @return  Exit program if true.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
