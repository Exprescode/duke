import java.util.Scanner;

public class Ui {

    private final String BAR = "    ____________________________________________________________";
    private Scanner scan;

    public Ui(){
        this.scan = new Scanner(System.in);
    }

    public String readCommand(){
        return scan.nextLine();
    }

    public void show(String msg){
        System.out.println(BAR + "\n     " + msg.replaceAll("\n", "\n     ") + "\n" + BAR + "\n");
    }

    public void showWelcome(){
        show("Hello! I'm Duke\nWhat can I do for you?");
    }

    public void showBye(){
        show("Bye. Hope to see you again soon!");
    }

    public void showError(String error){
        show("☹ OOPS!!! " + error);
    }

    public void showLoadingError() {
        showError("Data file might be corrupted! Staring Duke with empty list.");
    }
}
