import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }

    @Test
    public void parserTest(){
        Command cmd = null;
        try {
            cmd = Parser.parse("event donation drive 2019 /at 20/10/2019 1000 20/10/2019 1600");
        } catch (DukeFormatException | DukeEmptyException e) {
            e.printStackTrace();
        }
        EventCommand ec = (EventCommand) cmd;
        assertEquals(ec.desc, "donation drive 2019");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy, hh:mm aaa");
        assertEquals(sdf.format(ec.start.getTime()), "20 October 2019, 10:00 AM");
        assertEquals(sdf.format(ec.end.getTime()), "20 October 2019, 04:00 PM");
    }

    @Test
    public void taskListFindTest(){
        TaskList tl = new TaskList();
        Todo t1 = new Todo("Buy textbook.");
        Todo t2 = new Todo("Return John pen.");
        Todo t3 = new Todo("Complete CS2113T week 6 tutorial.");
        Todo t4 = new Todo("Borrow book.");
        Todo t5 = new Todo("Exercise");
        tl.add(t1);
        tl.add(t2);
        tl.add(t3);
        tl.add(t4);
        tl.add(t5);
        String expect = "1.[T][✗] Buy textbook.\n2.[T][✗] Complete CS2113T week 6 tutorial.\n3.[T][✗] Borrow book.";
        String actual = tl.find("o o");
        assertEquals(expect, actual);
    }

}
