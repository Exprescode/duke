public class ListCommand extends Command {
    public ListCommand(){
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        if(tasks.len() < 1){
            throw new DukeBoundException("List command cannot operate on an empty task list.");
        }
        ui.show("Here are the tasks in your list:\n" + tasks.list());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
