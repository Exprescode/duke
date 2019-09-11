public class ExitCommand extends Command {

    /**
     * ExitCommand constructor init ExitCommand obj.
     */
    public ExitCommand(){
    }

    /**
     * Display program exit message.
     * @param tasks                 list of task required for command processing.
     * @param ui                    Object interact with user console.
     * @param storage               Object interact with data file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    /**
     * Proceed to exit program.
     * @return Exit program if true.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
