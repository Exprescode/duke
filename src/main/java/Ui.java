import java.util.Scanner;

public class Ui {

    private final String BAR = "    ____________________________________________________________";
    private Scanner scan;

    /**
     * Initialize Ui object.
     */
    public Ui(){
        this.scan = new Scanner(System.in);
    }

    /**
     * Get user input.
     * @return  User input as String.
     */
    public String readCommand(){
        return scan.nextLine();
    }

    /**
     * Display given message onto console.
     * @param msg   Message to be printed onto console.
     */
    public void show(String msg){
        System.out.println(BAR + "\n     " + msg.replaceAll("\n", "\n     ") + "\n" + BAR + "\n");
    }

    /**
     * Display Duke init message onto console.
     */
    public void showWelcome(){
        show("Hello! I'm Duke\nWhat can I do for you?");
    }

    /**
     * Display Duke exit message onto console.
     */
    public void showBye(){
        show("Bye. Hope to see you again soon!");
    }

    /**
     * Format and display given error message onto console.
     * @param error Error message to be printed onto console.
     */
    public void showError(String error){
        show("☹ OOPS!!! " + error);
    }

    /**
     * Display file I/O error message on console.
     */
    public void showLoadingError() {
        showError("Data file might be corrupted! Staring Duke with empty list.");
    }
}
