public class ListCommand extends Command {

    /**
     * ListCommand constructor init ListCommand obj.
     */
    public ListCommand(){
    }

    /**
     * List all Task in TaskList & display.
     * @param tasks                 list of task required for command processing.
     * @param ui                    Object interact with user console.
     * @param storage               Object interact with data file.
     * @throws DukeBoundException   Process empty TaskList.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        if(tasks.len() < 1){
            throw new DukeBoundException("List command cannot operate on an empty task list.");
        }
        ui.show("Here are the tasks in your list:\n" + tasks.list());
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
