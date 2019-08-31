import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Deadline extends Task {
    protected Calendar by;
    public Deadline(String desc, Calendar by){
        super(desc);
        this.by = by;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy, hh:mm aaa");
        return "[D]" + super.toString() + " (by: " + sdf.format(by.getTime()) + ")";
    }
}
