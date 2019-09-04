public class TodoCommand extends Command {
    private String desc;

    public TodoCommand(String desc){
        this.desc = desc;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        Todo new_todo = new Todo(desc);
        tasks.add(new_todo);
        ui.show("Got it. I've added this task:\n  " + new_todo.toString() + "\nNow you have " + tasks.len() + " tasks in the list.");
        storage.store(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
