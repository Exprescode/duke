import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                case "delete":
                    try{
                        cmd_delete(user_input_parts[1]);
                    } catch (DukeFormatException e) {
                        print_console(format_error("The argument of delete must be numeric."));
                    } catch (DukeBoundException e) {
                        print_console(format_error("The argument of delete must be an entry number from list."));
                    } catch (DukeEmptyException e) {
                        print_console(format_error("The argument of delete must not be empty"));
                    }
                    break;
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
                case "find":
                    try{
                        cmd_find(user_input_parts[1]);
                    } catch (DukeEmptyException e) {
                        print_console((format_error("Your list is empty. Please populate it first.")));
                    }
                    break;
                case "todo":
                    try{
                        cmd_todo(user_input_parts[1]);
                    } catch(DukeEmptyException e){
                        print_console(format_error("The description of a todo cannot be empty."));
                    }
                    break;
                case "deadline":
                    try{
                        cmd_deadline(user_input_parts[1]);
                    } catch(DukeEmptyException e){
                        print_console(format_error("The description and date/time of a deadline cannot be empty."));
                    } catch (DukeFormatException e) {
                        print_console(format_error("Incorrect date time format!\nPlease enter date time in the follow format:\n<DAY>/<MONTH>/<YEAR><space><HOURS><MINUTES>\nDD/MM/YYYY HHMM"));
                    } catch (DukeBoundException e) {
                        print_console(format_error("Deadline date time must be earlier than the present date time."));
                    }
                    break;
                case "event":
                    try{
                        cmd_event(user_input_parts[1]);
                    } catch(DukeEmptyException e){
                        print_console(format_error("The description and data/time of an event cannot be empty."));
                    } catch (DukeFormatException e) {
                        String msg = e.getMessage();
                        if(msg.equals("Wrong start date time format.")){
                            msg = "Incorrect starting";
                        } else if (msg.equals("Wrong end date time format.")){
                            msg = "Incorrect ending";
                        } else {
                            msg = "Incorrect";
                        }
                        print_console(format_error(msg + " date time format!\nPlease enter date time in the follow format:\n<DAY>/<MONTH>/<YEAR><space><HOURS><MINUTES>\nDD/MM/YYYY HHMM"));
                    } catch (DukeBoundException e) {
                        String msg = e.getMessage();
                        if(msg.equals("Start date time expired.")){
                            msg = "Starting date time must be earlier than the present date time.";
                        } else {
                            msg = "Ending date time must be earlier than the starting date time.";
                        }
                        print_console(format_error(msg));
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

    private static String format_add_task(String added_task) {
        return "Got it. I've added this task:\n  " + added_task + "\nNow you have " + tasks.size() + " tasks in the list.";
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
            backup_tasks();
        } else {
            throw new DukeBoundException("Option is not within list options.");
        }
    }

    private static void cmd_delete(String option) throws DukeEmptyException,DukeFormatException,DukeBoundException {
        int index;
        if(option.length() == 0){
            throw new DukeEmptyException("Arguemnt cannot be empty!");
        }
        try {
            index = Integer.parseInt(option);
        } catch (Exception e) {
            throw new DukeFormatException("Delete argument is not numeric.");
        }
        if (index > 0 && index <= tasks.size()) {
            String task_desc = tasks.get(--index).toString();
            tasks.remove(index);
            print_console("Noted. I've removed this task:\n  " + task_desc + "\nNow you have " + tasks.size() + " tasks in the list.");
            backup_tasks();
        } else {
            throw new DukeBoundException("Option is not within list options.");
        }
    }

    private static void cmd_find(String search_str) throws DukeEmptyException {
        if(tasks.size() < 1){
            throw new DukeEmptyException("Task list is empty! Cannot list an empty list.");
        }
        Pattern pattern = Pattern.compile(".?" + search_str.replaceAll(" ", ".?") + ".?", Pattern.CASE_INSENSITIVE);
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (Task proc_task : tasks) {
            if (pattern.matcher(proc_task.getDesc()).find()) {
                counter++;
                sb.append(counter + "." + proc_task.toString() + "\n");
            }
        }
        sb.deleteCharAt(sb.length() - 1); //Remove extra newline.
        if(counter > 0){
            print_console(sb.toString());
        } else {
            print_console("I've search everywhere but there is not matching tasks.");
        }
    }

    private static void cmd_todo(String desc) throws DukeEmptyException{
        if(desc.isEmpty() || desc.isBlank()){
            throw new DukeEmptyException("Todo description cannot be empty");
        }
        Todo new_todo = new Todo(desc);
        tasks.add(new_todo);
        print_console(format_add_task(new_todo.toString()));
        backup_tasks();
    }

    private static void cmd_deadline(String args) throws DukeEmptyException, DukeFormatException, DukeBoundException {
        if(args.isEmpty() || args.isBlank()){
            throw new DukeEmptyException("Deadline description cannot be empty");
        }
        String[] args_parts = args.split(" /by ", 2);
        if(args_parts.length < 2){
            throw new DukeEmptyException("Deadline date time cannot be empty.");
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HHmm");
        sdf.setLenient(false);
        try{
            cal.setTime(sdf.parse(args_parts[1]));
        } catch (ParseException e) {
            throw new DukeFormatException("Wrong date time format.");
        }
        if(cal.compareTo(Calendar.getInstance()) < 1){
            throw new DukeBoundException("Date time has already expired.");
        }
        Deadline new_deadline = new Deadline(args_parts[0], cal);
        tasks.add(new_deadline);
        print_console(format_add_task(new_deadline.toString()));
        backup_tasks();
    }

    private static void cmd_event(String args) throws DukeEmptyException, DukeFormatException, DukeBoundException {
        if(args.isEmpty() || args.isBlank()){
            throw new DukeEmptyException("Event description cannot be empty.");
        }
        String[] args_parts = args.split(" /at ", 2);
        if(args_parts.length < 2) {
            throw new DukeEmptyException("Event data/time cannot be empty.");
        }
        Matcher end_date_matcher = Pattern.compile("^(\\d{2}/\\d{2}/\\d{4} \\d{4}) ?(\\d{2}/\\d{2}/\\d{4} \\d{4})?$").matcher(args_parts[1]);
        if(!end_date_matcher.find()){
            throw new DukeFormatException("Wrong date time format.");
        }
        String start_str = end_date_matcher.group(1);
        String end_str = end_date_matcher.group(2);
        Calendar start_cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HHmm");
        sdf.setLenient(false);
        try{
            start_cal.setTime(sdf.parse(start_str));
        } catch (ParseException e) {
            throw new DukeFormatException("Wrong start date time format.");
        }
        if(start_cal.compareTo(Calendar.getInstance()) < 1){
            throw new DukeBoundException("Start date time expired.");
        }
        Event new_event;
        if(end_str != null){
            Calendar end_cal = Calendar.getInstance();
            try{
                end_cal.setTime(sdf.parse(end_str));
            } catch (ParseException e) {
                throw new DukeFormatException("Wrong end date time format.");
            }
            if(end_cal.compareTo(start_cal) < 1){
                throw new DukeBoundException("End date time expired.");
            }
            new_event = new Event(args_parts[0], start_cal, end_cal);
        }else{
            new_event = new Event(args_parts[0], start_cal);
        }
        tasks.add(new_event);
        print_console(format_add_task(new_event.toString()));
        backup_tasks();
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
            Object obj = ois.readObject();
            if(obj instanceof ArrayList<?>){
                tasks = (ArrayList<Task>) obj;
            }
        } catch (ClassNotFoundException | IOException e) {
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
