package commands;

import schedule.Lecture;
import schedule.Module;

import java.util.*;

public class ModuleCommand extends Command {
    private String name;
    private int weight = 0;
    private Map<String, Set<Lecture>> lectures;

    public ModuleCommand(String line, String separator) {
        super(line, separator);

        if (args.length < 3) {
            String message;
            message = args.length < 2 ? "Name and lectures expected for module" : "lectures expected for module";
            message += "\n> " + line;
            throw new IllegalArgumentException(message);
        }

        this.name = args[1];
        this.lectures = new HashMap<String, Set<Lecture>>();
        int position = 2;
        for (; position < args.length - 1; position++) {
            lectures.put(args[position], new HashSet<>());
        }
        try {
            weight = Integer.parseInt(args[args.length - 1]);
        } catch (NumberFormatException e1) {
            lectures.put(args[args.length - 1], new HashSet<>());
        }
    }

    public Module getModule() {
        return new Module(name, lectures, weight);
    }


}
