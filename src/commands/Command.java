package commands;

public abstract class Command {
    protected String line;
    protected String[] args;

    public Command(String line, String separator) {
        this.line = line;
        String[] arguments = line.split(separator);
        String[] newArguments = new String[arguments.length];
        int j = 0;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] != null && !arguments[i].equals("")) {
                newArguments[j++] = arguments[i];
            }
        }
        args = new String[j];
        for (int i = 0; i < args.length; i++) {
            args[i] = newArguments[i];
        }
    }

    public String getLine() {
        return line;
    }
}