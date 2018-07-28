package commands;

import java.util.HashSet;
import java.util.Set;
import schedule.Block;
import schedule.Lecture;

public class LectureCommand extends Command {
    private String name;
    private Set<Block> blocks;
    private int weight = 0;

    public LectureCommand(String line, String separator) {
        super(line, separator);

        if (args.length < 3) {
            String message;
                message = args.length < 2 ? "Name and blocks expected for lecture" : "Blocks expected for lecture";
                message += "\n> " + line;
            throw new IllegalArgumentException(message);
        }

        this.name = args[1];
        this.blocks = new HashSet<>();
        int position = 2;
        try {
            for (;position < args.length; position++) {
                blocks.add(Block.valueOf(args[position].toLowerCase()));
            }
        } catch (IllegalArgumentException e) {
            try {
                weight = Integer.parseInt(args[position]);
            } catch (NumberFormatException e1) {
                throw new IllegalArgumentException(args[position] + " cannot be converted in a block (mo1,...,mo6,di1,...) or a weight (Integer numbers > 0)");
            }
        }
    }

    public Lecture getLecture() {
        return new Lecture(name, blocks, weight);
    }

}
