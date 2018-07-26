package schedule;

import java.io.IOException;

public class Main {
    public static final boolean DEBUG = true;

    public static void main(String[] args) {
        try {
            Schedule schedule = ScheduleIO.readSchedule("test.txt");
            schedule.printSchedule();
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
