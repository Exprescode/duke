import java.io.*;

public class Duke {
    private static final String BAR = "    ____________________________________________________________";
    private static Storage storage;
    private static TaskList tasks;
    private static Ui ui;

    /**
     * Init Ui & Storage. Populate TaskList from storage.
     * @param filePath Path of data file for Duke.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException | ClassNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Show Duke init message and ready to accept user input.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeEmptyException | DukeFormatException | DukeBoundException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Init Duke object and run Duke.
     * @param args
     */
    public static void main(String[] args) {
        new Duke("C:\\Users\\JH\\Documents\\GitHub\\duke\\data\\duke.txt").run();
    }
}