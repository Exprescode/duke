import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String BAR = "    ____________________________________________________________";

    /**
     * Program starts here. This is the main method.
     *
     * @param args cmd line input arguments.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        print_init_msg();
        String user_input = "";
        ArrayList<Task> tasks = new ArrayList<>();
        while (!user_input.equals("bye")) {
            user_input = scan.nextLine();
            if (user_input.equals("list")) {
                StringBuilder sb = new StringBuilder();
                sb.append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); ++i) {
                    sb.append((i + 1) + ".");           //Append entry number
                    sb.append(tasks.get(i).toString());    //Append task desc
                    sb.append("\n");                    //Append newline
                }
                sb.deleteCharAt(sb.length() - 1); //Remove extra newline.
                print_console(sb.toString());
                continue;
            }
            String[] user_input_parts = user_input.split(" ", 2);
            String added_task = "";
            switch(user_input_parts[0]){
                case "done":
                    int done_arg = str_to_int(user_input_parts[1]);
                    if (done_arg > 0 && done_arg <= tasks.size()) {
                        Task curr_task = tasks.get(done_arg - 1);
                        curr_task.setDone();
                        print_console("Nice! I've marked this task as done:\n " + curr_task.toString());
                    } else {
                        print_console("Invalid command!");
                    }
                    break;
                case "todo":
                    Todo new_todo = new Todo(user_input_parts[1]);
                    tasks.add(new_todo);
                    print_console(added_task_msg(new_todo.toString(), tasks.size()));
                    break;
                case "deadline":
                    String[] deadline_args = user_input_parts[1].split(" /by ", 2);
                    Deadline new_deadline = new Deadline(deadline_args[0], deadline_args[1]);
                    tasks.add(new_deadline);
                    print_console(added_task_msg(new_deadline.toString(), tasks.size()));
                    break;
                case "event":
                    String[] event_args = user_input_parts[1].split(" /at ", 2);
                    Event new_event = new Event(event_args[0], event_args[1]);
                    tasks.add(new_event);
                    print_console(added_task_msg(new_event.toString(), tasks.size()));
                    break;
            }
        }
        print_exit_msg();
    }

    private static void print_console(String msg) {
        System.out.println(BAR + "\n     " + msg.replaceAll("\n", "\n     ") + "\n" + BAR + "\n");
    }

    private static void print_init_msg() {
        print_console("Hello! I'm Duke\nWhat can I do for you?");
    }

    private static void print_exit_msg() {
        print_console("Bye. Hope to see you again soon!");
    }

    private static int str_to_int(String str_int) {
        try {
            return Integer.parseInt(str_int);
        } catch (Exception e) {
            //Ignore error.
        }
        return 0;
    }

    private static String added_task_msg(String added_task, int no_tasks) {
        return "Got it. I've added this task:\n  " + added_task + "\nNow you have " + no_tasks + " tasks in the list.";
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        Label helloWorld = new Label("Hello World!"); //Creating a new Label control.
//        helloWorld.setFont(new Font("Arial", 50));
//        Scene scene = new Scene(helloWorld); //Setting the scene to be our label.
//        stage.setScene(scene); //Settings the stage to show our screen.
//        stage.show(); //Render the stage.
//        Stage stage2 = new Stage();
//        stage2.setScene(scene);
//        stage2.show();
//    }
}
