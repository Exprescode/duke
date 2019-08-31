import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event extends Task {
    protected Calendar start, end;
    public Event(String desc, Calendar start){
        super(desc);
        this.start = start;
        end = null;
    }

    public Event(String desc, Calendar start, Calendar end){
        super(desc);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy, hh:mm aaa");
        if(end != null){
            return "[E]" + super.toString() + " (from: " + sdf.format(start.getTime()) + " to: " + sdf.format(end.getTime()) + ")";
        }
        return "[E]" + super.toString() + " (at: " + sdf.format(start.getTime()) + ")";
    }
}
