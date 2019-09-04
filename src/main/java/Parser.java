import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.copyOf;

public abstract class Parser {

    public static Command parse(String input) throws DukeFormatException, DukeEmptyException {
        if(input.equals("bye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }
        String[] input_parts = input.split(" ", 2);
        if(input_parts.length < 2) {
            input_parts = copyOf(input_parts, 2);
            input_parts[1] = "";
        } else {
            input_parts[1] = input_parts[1].replaceAll("\\s+", " ").trim();
        }
        String[] args;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HHmm");
        sdf.setLenient(false);
        switch(input_parts[0]){
            case "delete":
                if(input_parts[1].length() < 1){
                    throw new DukeEmptyException("Delete arguemnt cannot be empty!");
                }
                try {
                    return new DeleteCommand(Integer.parseInt(input_parts[1]) - 1);
                } catch (Exception e) {
                    throw new DukeFormatException("Delete argument must be numeric.");
                }
            case "done":
                if(input_parts[1].length() < 1){
                    throw new DukeEmptyException("Done arguemnt cannot be empty.");
                }
                try {
                    return new DoneCommand(Integer.parseInt(input_parts[1]) - 1);
                } catch (Exception e) {
                    throw new DukeFormatException("Done argument must be numeric.");
                }
            case "find":
                return new FindCommand(input_parts[1]);
            case "todo":
                if(input_parts[1].isEmpty() || input_parts[1].isBlank()){
                    throw new DukeEmptyException("Todo description cannot be empty");
                }
                return new TodoCommand(input_parts[1]);
            case "deadline":
                args = input_parts[1].split(" /by ", 2);
                if(args.length < 2){
                    throw new DukeFormatException("Invalid event command!\nPlease use the following event command format:\nevent <Description> /at <Date> <Time>[ <Date> <Time]");
                }
                if(args[0].isEmpty() || args[0].isBlank()){
                    throw new DukeEmptyException("Deadline description cannot be empty.");
                }
                Calendar cal = Calendar.getInstance();
                try{
                    cal.setTime(sdf.parse(args[1]));
                } catch (ParseException e) {
                    throw new DukeFormatException("Invalid date/time format!\nPlease use the following date/time format:\n<DAY>/<MONTH>/<YEAR> <HOURS><MINUTES>\nDD/MM/YYYY HHMM");
                }
                return new DeadlineCommand(args[0], cal);
            case "event":
                args = input_parts[1].split(" /at ", 2);
                if(args.length < 2) {
                    throw new DukeFormatException("Invalid event command!\nPlease use the following event command format:\nevent <Description> /at <Date> <Time>[ <Date> <Time]");
                }
                if(args[0].isEmpty() || args[0].isBlank()){
                    throw new DukeEmptyException("Event description cannot be empty.");
                }
                Matcher start_end_matcher = Pattern.compile("^(\\d{2}/\\d{2}/\\d{4} \\d{4}) ?(\\d{2}/\\d{2}/\\d{4} \\d{4})?$").matcher(args[1]);
                if(!start_end_matcher.find()){
                    throw new DukeFormatException("Invalid date/time format!\nPlease use the following date/time format:\n<DAY>/<MONTH>/<YEAR> <HOURS><MINUTES>\nDD/MM/YYYY HHMM");
                }
                String start_str = start_end_matcher.group(1);
                String end_str = start_end_matcher.group(2);
                Calendar start = Calendar.getInstance();
                try{
                    start.setTime(sdf.parse(start_str));
                } catch (ParseException e) {
                    throw new DukeFormatException("Invalid start date/time format!\nPlease use the following date/time format:\n<DAY>/<MONTH>/<YEAR> <HOURS><MINUTES>\nDD/MM/YYYY HHMM");
                }
                if(end_str != null){
                    Calendar end = Calendar.getInstance();
                    try{
                        end.setTime(sdf.parse(end_str));
                    } catch (ParseException e) {
                        throw new DukeFormatException("Invalid end date/time format!\nPlease use the following date/time format:\n<DAY>/<MONTH>/<YEAR> <HOURS><MINUTES>\nDD/MM/YYYY HHMM");
                    }
                    return new EventCommand(args[0], start, end);
                }
                return new EventCommand(args[0], start);
            default:
                throw new DukeFormatException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
