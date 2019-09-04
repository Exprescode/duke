public class FindCommand extends Command {
    private String query;
    public FindCommand(String query){
        this.query = query;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeBoundException {
        if(tasks.len() < 1){
            throw new DukeBoundException("Find command cannot operate on an empty task list.");
        }
        String result = tasks.find(query);
        if(result.length() > 0){
            ui.show("Here are the matching tasks in your list:\n" + result);
        } else {
            ui.show("I've search everywhere but there is not matching tasks.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
