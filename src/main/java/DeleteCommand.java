public class DeleteCommand extends Command {
    private int index;
    public DeleteCommand(int index){
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        String task_str = tasks.remove(index);
        if(task_str.length() < 1){
            throw new DukeBoundException("Delete argument is not a valid entry from list command.");
        }
        ui.show("Noted. I've removed this task:\n  " + task_str + "\nNow you have " + tasks.len() + " tasks in the list.");
        storage.store(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
