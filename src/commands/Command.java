package commands;

public abstract class Command {
    protected String line;
    protected String[] args;

    public Command(String line, String separator) {
        this.line = line;
        args = line.split(separator);
    }

    public String getLine() {
        return line;
    }
}