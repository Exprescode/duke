import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String BAR = "\t____________________________________________________________";

    /**
     * Program starts here. This is the main method.
     *
     * @param args cmd line input arguments.
     */
    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);
        Scanner scan = new Scanner(System.in);
        print_init_msg();
        String user_input = scan.nextLine();
        ArrayList<Task> tasks = new ArrayList<>();
        while (!user_input.equals("bye")) {
            if (user_input.equals("list")) {
                StringBuilder sb = new StringBuilder();
                sb.append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); ++i) {
                    Task curr_task = tasks.get(i);
                    sb.append((i + 1) + ".");                                           //Append entry number.
                    sb.append("[" + (curr_task.getStatus() ? "✓" : "✗") + "] ");   //Append task status
                    sb.append(curr_task.getName());                                      //Append task name
                    sb.append("\n");                                                    //Append newline
                }
                sb.deleteCharAt(sb.length() - 1); //Remove extra newline.
                print_console(sb.toString());
            } else {
                String[] user_input_parts = user_input.split(" ", 2);
                if (user_input_parts[0].equals("done")) {
                    int cmd_arg = str_to_int(user_input_parts[1]);
                    if (cmd_arg > 0 && cmd_arg <= tasks.size()) {
                        Task curr_task = tasks.get(cmd_arg - 1);
                        curr_task.setDone();
                        print_console("Nice! I've marked this task as done:\n  [✓] " + curr_task.getName());
                    } else {
                        print_console("Invalid option!");
                    }
                } else {
                    tasks.add(new Task(user_input));
                    print_console("added: " + user_input);
                }
            }
            user_input = scan.nextLine();
        }
        print_exit_msg();
    }

    private static void print_console(String msg) {
        System.out.println(BAR + "\n\t " + msg.replaceAll("\n", "\n\t ") + "\n" + BAR + "\n");
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
}
