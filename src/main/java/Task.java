public class Task {
    private String name;
    private boolean status;
    public Task(String name) {
        this.name = name;
        this.status = false;
    }

    public void setDone() {
        this.status = true;
    }

    public String getName() {
        return this.name;
    }

    public boolean getStatus() {
        return this.status;
    }
}
