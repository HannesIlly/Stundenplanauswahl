package schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static final boolean DEBUG = true;

    public static void main(String[] args) {
        try {
            String file = args.length > 0 ? args[0] : "schedule.txt";
            try {
                int limit = args.length > 1 ? Integer.parseInt(args[1]) : 10;
            } catch(NumberFormatException e) {
                throw new IllegalArgumentException("The limit of displayed timetables cannot be set to " + args[1]);
            }
            String separator = args.length > 2 ? args[2] : ",";

            Schedule schedule = ScheduleIO.readSchedule(file, separator);
            schedule.printSchedule(10);

            /*
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line = in.readLine();
            while (true) {

                //TODO process commands

                line = in.readLine();
                break;//TODO remove later or add quit-command
            }
            in.close();
            */

        } catch(IllegalArgumentException | IOException e) {
            if (DEBUG) {
                e.printStackTrace();
            } else {
                System.err.println(e.getMessage());
            }
        }

    }
}
