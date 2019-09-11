import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Deadline extends Task {
    protected Calendar by;

    /**
     * Deadline constructor init deadline variables.
     * @param desc  Deadline description.
     * @param by    Deadline date time.
     */
    public Deadline(String desc, Calendar by){
        super(desc);
        this.by = by;
    }

    /**
     * Return deadline in string format
     * @return  Give formatted string of deadline variables.
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy, hh:mm aaa");
        return "[D]" + super.toString() + " (by: " + sdf.format(by.getTime()) + ")";
    }
}
