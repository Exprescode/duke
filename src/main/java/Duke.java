import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String BAR = "\t____________________________________________________________";
    /**
     * Program starts here. This is the main method.
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
        ArrayList<String> user_inputs = new ArrayList<String>();
        while(!user_input.equals("bye")){
            if(user_input.equals("list")){
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < user_inputs.size(); ++i){
                    sb.append(i + 1).append(". ").append(user_inputs.get(i)).append("\n");
                }
                sb.deleteCharAt(sb.length()-1);
                print_console(sb.toString());
            } else {
                user_inputs.add(user_input);
                print_console("added: " + user_input);
            }
            user_input = scan.nextLine();
        }
        print_console("Bye. Hope to see you again soon!");
    }

    private static void print_console(String msg) {
        System.out.println(BAR + "\n\t " + msg.replaceAll("\n", "\n\t ") + "\n" + BAR + "\n");
    }

    private static void print_init_msg() {
        print_console("Hello! I'm Duke\nWhat can I do for you?");
    }
}
