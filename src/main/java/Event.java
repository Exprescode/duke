import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event extends Task {
    protected Calendar start, end;

    /**
     * Event class constructor init object variables without end date time.
     * @param desc  Event description.
     * @param start Event start date time.
     */
    public Event(String desc, Calendar start){
        super(desc);
        this.start = start;
        end = null;
    }

    /**
     * Event class constructor init object variables with end date time.
     * @param desc  Event description.
     * @param start Event start date time.
     * @param end   Event end date time.
     */
    public Event(String desc, Calendar start, Calendar end){
        super(desc);
        this.start = start;
        this.end = end;
    }

    /**
     * Format Event object variables into human readable text.
     * @return  String with event details.
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy, hh:mm aaa");
        if(end != null){
            return "[E]" + super.toString() + " (from: " + sdf.format(start.getTime()) + " to: " + sdf.format(end.getTime()) + ")";
        }
        return "[E]" + super.toString() + " (at: " + sdf.format(start.getTime()) + ")";
    }
}
