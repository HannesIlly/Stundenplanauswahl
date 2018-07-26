package commands;

public abstract class Command {
    protected String line;
    protected String[] args;

    public Command(String line) {
        this.line = line;
        args = line.split(",");
    }

    public String getLine() {
        return line;
    }
}