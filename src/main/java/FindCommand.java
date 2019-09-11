public class FindCommand extends Command {
    private String query;

    /**
     * FindCommand constructor init FindCommand obj & variables.
     * @param query Task description search string.
     */
    public FindCommand(String query){
        this.query = query;
    }


    /**
     * Find tasks with matching description to query string.
     * @param tasks                 list of task required for command processing.
     * @param ui                    Object interact with user console.
     * @param storage               Object interact with data file.
     * @throws DukeBoundException   Taskslist is empty.
     */
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

    /**
     * Proceed to exit program.
     * @return Exit program if true.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
