package schedule;

import java.io.IOException;

public class Main {
    public static final boolean DEBUG = false;

    public static void main(String[] args) {
        try {
            String file = args.length > 0 ? args[0] : "schedule.txt";
            try {
                int limit = args.length > 1 ? Integer.parseInt(args[1]) : 10;
            } catch(NumberFormatException e) {
                throw new IllegalArgumentException("The limit of displayed timetables cannot be set to " + args[1]);
            }

            Schedule schedule = ScheduleIO.readSchedule(file);
            schedule.printSchedule(10);
            //TODO add commands for output(schedule, timetable(...))



        } catch(IllegalArgumentException | IOException e) {
            if (DEBUG) {
                e.printStackTrace();
            } else {
                System.err.println(e.getMessage());
            }
        }

    }
}
