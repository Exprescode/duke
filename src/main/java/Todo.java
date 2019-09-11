public class Todo extends Task {

    /**
     * Initialize Todo object.
     * @param desc Todo description.
     */
    public Todo(String desc) {
        super(desc);
    }

    /**
     * Format Todo variables into text as String.
     * @return  Todo details in text as String.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
