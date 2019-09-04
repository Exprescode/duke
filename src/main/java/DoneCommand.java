public class DoneCommand extends Command {
    private int index;
    public DoneCommand(int index){
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        String task_str = tasks.tick(index);
        if(task_str.length() < 1){
            throw new DukeBoundException("Done argument is not a valid entry from list command.");
        }
        ui.show("Nice! I've marked this task as done:\n " + task_str);
        storage.store(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
