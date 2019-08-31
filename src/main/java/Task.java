import java.io.Serializable;

public abstract class Task implements Serializable {
    private String desc;
    private boolean status;

    public Task(String desc) {
        this.desc = desc;
        this.status = false;
    }

    public void setDone() {
        this.status = true;
    }

    public String getDesc() {
        return this.desc;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String toString() {
        return ("[" + (status ? "✓" : "✗") + "] " + desc);
    }
}
