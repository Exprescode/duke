import java.io.Serializable;

public abstract class Task implements Serializable {
    private String desc;
    private boolean status;

    /**
     * Task constructor intit Task obj & variables.
     * @param desc Task description.
     */
    public Task(String desc) {
        this.desc = desc;
        this.status = false;
    }

    /**
     * Marks task as done.
     */
    public void setDone() {
        this.status = true;
    }

    /**
     * Retrieve task description.
     * @return  Task description as string.
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * Format task variables/details into text.
     * @return Task details in text as String.
     */
    public String toString() {
        return ("[" + (status ? "✓" : "✗") + "] " + desc);
    }
}
