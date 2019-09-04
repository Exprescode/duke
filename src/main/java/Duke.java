import java.io.*;

public class Duke {
    private static final String BAR = "    ____________________________________________________________";
    private static Storage storage;
    private static TaskList tasks;
    private static Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage= new Storage(filePath);
        try{
            tasks = new TaskList(storage.load());
        } catch (IOException | ClassNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run(){
        ui.showWelcome();
        boolean isExit = false;
        while(!isExit){
            try{
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeEmptyException | DukeFormatException | DukeBoundException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Duke("C:\\Users\\JH\\Documents\\GitHub\\duke\\data\\duke.txt").run();
    }

//    private static void print_console(String msg) {
//        System.out.println(BAR + "\n     " + msg.replaceAll("\n", "\n     ") + "\n" + BAR + "\n");
//    }
//
//    private static void print_init_msg() {
//        print_console("Hello! I'm Duke\nWhat can I do for you?");
//    }
//
//    private static void print_exit_msg() {
//        print_console("Bye. Hope to see you again soon!");
//    }

//    private static String format_add_task(String added_task) {
//        return "Got it. I've added this task:\n  " + added_task + "\nNow you have " + tasks.len() + " tasks in the list.";
//    }
//
//    private static String format_error(String msg) {
//        return "☹ OOPS!!! " + msg;
//    }
//
//    private static void cmd_list() throws DukeEmptyException {
//        if(tasks.len() < 1){
//            throw new DukeEmptyException("Task list is empty! Cannot list an empty list.");
//        }
//        ui.show("Here are the tasks in your list:\n" + tasks.list());
//    }
//
//    private static void cmd_done(String option) throws DukeEmptyException,DukeFormatException,DukeBoundException {
//        int index;
//        if(option.length() == 0){
//            throw new DukeEmptyException("Arguemnt cannot be empty!");
//        }
//        try {
//            index = Integer.parseInt(option);
//        } catch (Exception e) {
//            throw new DukeFormatException("Done argument is not numeric.");
//        }
//        String task_str = tasks.tick(--index);
//        if(task_str.length() < 1){
//            throw new DukeBoundException("Option is not within list options.");
//        }
//        ui.show("Nice! I've marked this task as done:\n " + task_str);
//        storage.store(tasks);
//    }
//
//    private static void cmd_delete(String option) throws DukeEmptyException,DukeFormatException,DukeBoundException {
//        int index;
//        if(option.length() == 0){
//            throw new DukeEmptyException("Arguemnt cannot be empty!");
//        }
//        try {
//            index = Integer.parseInt(option);
//        } catch (Exception e) {
//            throw new DukeFormatException("Delete argument is not numeric.");
//        }
//        String task_str = tasks.remove(--index);
//        if(task_str.length() < 1){
//            throw new DukeBoundException("Option is not within list options.");
//        }
//        ui.show("Noted. I've removed this task:\n  " + task_str + "\nNow you have " + tasks.len() + " tasks in the list.");
//        storage.store(tasks);
//    }
//
//    private static void cmd_find(String search_str) throws DukeEmptyException {
//        if(tasks.len() < 1){
//            throw new DukeEmptyException("Task list is empty! Cannot list an empty list.");
//        }
//        String result = tasks.find(search_str);
//        if(result.length() > 0){
//            ui.show("Here are the matching tasks in your list:\n" + result);
//        } else {
//            ui.show("I've search everywhere but there is not matching tasks.");
//        }
//    }
//
//    private static void cmd_todo(String desc) throws DukeEmptyException{
//        if(desc.isEmpty() || desc.isBlank()){
//            throw new DukeEmptyException("Todo description cannot be empty");
//        }
//        Todo new_todo = new Todo(desc);
//        tasks.add(new_todo);
//        ui.show(format_add_task(new_todo.toString()));
//        storage.store(tasks);
//    }
//
//    private static void cmd_deadline(String args) throws DukeEmptyException, DukeFormatException, DukeBoundException {
//        if(args.isEmpty() || args.isBlank()){
//            throw new DukeEmptyException("Deadline description cannot be empty");
//        }
//        String[] args_parts = args.split(" /by ", 2);
//        if(args_parts.length < 2){
//            throw new DukeEmptyException("Deadline date time cannot be empty.");
//        }
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HHmm");
//        sdf.setLenient(false);
//        try{
//            cal.setTime(sdf.parse(args_parts[1]));
//        } catch (ParseException e) {
//            throw new DukeFormatException("Wrong date time format.");
//        }
//        if(cal.compareTo(Calendar.getInstance()) < 1){
//            throw new DukeBoundException("Date time has already expired.");
//        }
//        Deadline new_deadline = new Deadline(args_parts[0], cal);
//        tasks.add(new_deadline);
//        ui.show(format_add_task(new_deadline.toString()));
//        storage.store(tasks);
//    }
//
//    private static void cmd_event(String args) throws DukeEmptyException, DukeFormatException, DukeBoundException {
//        if(args.isEmpty() || args.isBlank()){
//            throw new DukeEmptyException("Event description cannot be empty.");
//        }
//        String[] args_parts = args.split(" /at ", 2);
//        if(args_parts.length < 2) {
//            throw new DukeEmptyException("Event data/time cannot be empty.");
//        }
//        Matcher end_date_matcher = Pattern.compile("^(\\d{2}/\\d{2}/\\d{4} \\d{4}) ?(\\d{2}/\\d{2}/\\d{4} \\d{4})?$").matcher(args_parts[1]);
//        if(!end_date_matcher.find()){
//            throw new DukeFormatException("Wrong date time format.");
//        }
//        String start_str = end_date_matcher.group(1);
//        String end_str = end_date_matcher.group(2);
//        Calendar start_cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HHmm");
//        sdf.setLenient(false);
//        try{
//            start_cal.setTime(sdf.parse(start_str));
//        } catch (ParseException e) {
//            throw new DukeFormatException("Wrong start date time format.");
//        }
//        if(start_cal.compareTo(Calendar.getInstance()) < 1){
//            throw new DukeBoundException("Start date time expired.");
//        }
//        Event new_event;
//        if(end_str != null){
//            Calendar end_cal = Calendar.getInstance();
//            try{
//                end_cal.setTime(sdf.parse(end_str));
//            } catch (ParseException e) {
//                throw new DukeFormatException("Wrong end date time format.");
//            }
//            if(end_cal.compareTo(start_cal) < 1){
//                throw new DukeBoundException("End date time expired.");
//            }
//            new_event = new Event(args_parts[0], start_cal, end_cal);
//        }else{
//            new_event = new Event(args_parts[0], start_cal);
//        }
//        tasks.add(new_event);
//        ui.show(format_add_task(new_event.toString()));
//        storage.store(tasks);
//    }

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
