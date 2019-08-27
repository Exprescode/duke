import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Arrays.copyOf;

public class Duke {
    private static final String BAR = "    ____________________________________________________________";
    private static ArrayList<Task> tasks;

    /**
     * Program starts here. This is the main method.
     *
     * @param args cmd line input arguments.
     */
    public static void main(String[] args) {
        restore_tasks();
        Scanner scan = new Scanner(System.in);
        String user_input = "";
        print_init_msg();
        while (!user_input.equals("bye")) {
            user_input = scan.nextLine();
            if (user_input.equals("list")) {
                try {
                    cmd_list();
                } catch(DukeEmptyException e){
                    print_console(format_error("You have no task."));
                }
                continue;
            }
            String[] user_input_parts = user_input.split(" ", 2);
            if(user_input_parts.length < 2) {
                user_input_parts = copyOf(user_input_parts, 2);
                user_input_parts[1] = "";
            } else {
                user_input_parts[1] = user_input_parts[1].replaceAll("\\s+", " ").trim();
            }
            switch(user_input_parts[0]){
                case "done":
                    try{
                        cmd_done(user_input_parts[1]);
                        backup_tasks();
                    } catch(DukeEmptyException e) {
                        print_console(format_error("The arguments of done must not be empty"));
                    } catch (DukeFormatException e) {
                        print_console(format_error("The argument of done must be numeric."));
                    } catch (DukeBoundException e) {
                        print_console(format_error("The argument of done must be an entry number from list."));
                    }
                    break;
                case "todo":
                    try{
                        cmd_todo(user_input_parts[1]);
                        backup_tasks();
                    } catch(DukeEmptyException e){
                        print_console(format_error("The description of a todo cannot be empty."));
                    }
                    break;
                case "deadline":
                    try{
                        cmd_deadline(user_input_parts[1]);
                        backup_tasks();
                    } catch(DukeEmptyException e){
                        print_console(format_error("The description and date/time of a deadline cannot be empty."));
                    }
                    break;
                case "event":
                    try{
                        cmd_event(user_input_parts[1]);
                        backup_tasks();
                    } catch(DukeEmptyException e){
                        print_console(format_error("The description and data/time of an event cannot be empty."));
                    }
                    break;
                case "bye":
                    print_exit_msg();
                    break;
                default:
                    print_console(format_error("I'm sorry, but I don't know what that means :-("));
            }
        }
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

    private static String format_add_task(String added_task, int no_tasks) {
        return "Got it. I've added this task:\n  " + added_task + "\nNow you have " + no_tasks + " tasks in the list.";
    }

    private static String format_error(String msg) {
        return "☹ OOPS!!! " + msg;
    }

    private static void cmd_list() throws DukeEmptyException {
        if(tasks.size() < 1){
            throw new DukeEmptyException("Task list is empty! Cannot list an empty list.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); ++i) {
            sb.append((i + 1) + "." + tasks.get(i).toString() + "\n");
        }
        sb.deleteCharAt(sb.length() - 1); //Remove extra newline.
        print_console(sb.toString());
    }

    private static void cmd_done(String option) throws DukeEmptyException,DukeFormatException,DukeBoundException {
        int index;
        if(option.length() == 0){
            throw new DukeEmptyException("Arguemnt cannot be empty!");
        }
        try {
            index = Integer.parseInt(option);
        } catch (Exception e) {
            throw new DukeFormatException("Done argument is not numeric.");
        }
        if (index > 0 && index <= tasks.size()) {
            Task curr_task = tasks.get(--index);
            curr_task.setDone();
            print_console("Nice! I've marked this task as done:\n " + curr_task.toString());
        } else {
            throw new DukeBoundException("Option is not within list options.");
        }
    }

    private static void cmd_todo(String desc) throws DukeEmptyException{
        if(desc.isEmpty() || desc.isBlank()){
            throw new DukeEmptyException("Todo description cannot be empty");
        }
        Todo new_todo = new Todo(desc);
        tasks.add(new_todo);
        print_console(format_add_task(new_todo.toString(), tasks.size()));
    }

    private static void cmd_deadline(String args) throws DukeEmptyException {
        if(args.isEmpty() || args.isBlank()){
            throw new DukeEmptyException("Deadline description cannot be empty");
        }
        String[] args_parts = args.split(" /by ", 2);
        if(args_parts.length < 2){
            throw new DukeEmptyException("Deadline date time cannot be empty.");
        }
        Deadline new_deadline = new Deadline(args_parts[0], args_parts[1]);
        tasks.add(new_deadline);
        print_console(format_add_task(new_deadline.toString(), tasks.size()));
    }

    private static void cmd_event(String args) throws DukeEmptyException {
        if(args.isEmpty() || args.isBlank()){
            throw new DukeEmptyException("Event description cannot be empty.");
        }
        String[] args_parts = args.split(" /at ", 2);
        if(args_parts.length < 2) {
            throw new DukeEmptyException("Event data/time cannot be empty.");
        }
        Event new_event = new Event(args_parts[0], args_parts[1]);
        tasks.add(new_event);
        print_console(format_add_task(new_event.toString(), tasks.size()));
    }

    private static void backup_tasks(){
        try {
            FileOutputStream fos = new FileOutputStream("C:\\Users\\JH\\Documents\\GitHub\\duke\\data\\duke.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); //If duke.txt cannot be created or open.
        } catch (IOException e) {
            e.printStackTrace(); //Error while writing stream header
        }
    }

    private static void restore_tasks(){
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\JH\\Documents\\GitHub\\duke\\data\\duke.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            tasks = (ArrayList<Task>) ois.readObject();
        } catch (FileNotFoundException e) {
            // If duke.txt does not exist.
            // Ignore error. File will be created later.
            tasks = new ArrayList<Task>();
        } catch (IOException e) {
            // Error while reading stream header.
            // File maybe corrupted! Repopulate list manually.
            tasks = new ArrayList<Task>();
        } catch (ClassNotFoundException e) {
            // If ois cannot be casted into ArrayList<Task>.
            // File is corrupted! Repopulate list manually.
            tasks = new ArrayList<Task>();
        }

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
