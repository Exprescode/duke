import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args){
        Matcher end_date_matcher = Pattern.compile("^(\\d{2}/\\d{2}/\\d{4} \\d{4}) ?(\\d{2}/\\d{2}/\\d{4} \\d{4})?$").matcher("12/12/1212 1212");
        boolean found = end_date_matcher.find();
        String grp = end_date_matcher.group(2);
        int count = end_date_matcher.groupCount();
        System.out.print("hi");
    }
}
