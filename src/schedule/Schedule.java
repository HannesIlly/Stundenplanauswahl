package schedule;

import java.util.Set;
import java.util.TreeSet;

public class Schedule {
    Set<Module> modules;
    Set<Timetable> timetables;

    public Schedule(Set<Module> modules) {
        this.modules = modules;
        this.timetables = new TreeSet<>();
        timetables.add(new Timetable());
    }

    public void printSchedule(int limit) {
        calculateTimetables();

        System.out.println("Anzahl: " + timetables.size() + " (Ausgabelimit: " + limit + ")");
        int best = ((TreeSet<Timetable>) timetables).first().getWeights();
        System.out.print("Bestes Ergebnis:        " + best);
        int count = 0;
        for (Timetable t : timetables) {
            if (t.getWeights() < best) {
                break;
            }
            count++;
        }
        System.out.println(" (" + count + " mal)");
        System.out.println("Schlechtestes Ergebnis: " + ((TreeSet<Timetable>) timetables).last().getWeights());
        System.out.println();
        for (Timetable t : timetables) {
            System.out.println("Gesamtgewicht: " + t.getWeights());
            t.print();
            System.out.println("\n");
            limit--;
            if (limit <= 0) {
                return;
            }
        }
    }

    private void calculateTimetables() {
        for (Module m : modules) {
            Set<Timetable> newTimetables = new TreeSet<>();
            for (Set<Lecture> lecturesToAdd : m.getLectureCombinations()) {
                Set<Timetable> currentTimetables = new TreeSet<>();
                for (Timetable timetable : timetables) {
                    if (!timetable.canAddLectures(lecturesToAdd)) {
                        currentTimetables.add(timetable);
                    }
                    Timetable newTimetable = new Timetable(timetable);
                    newTimetable.addLectures(lecturesToAdd);
                    currentTimetables.add(newTimetable);
                }
                newTimetables.addAll(currentTimetables);
            }
            this.timetables = newTimetables;
        }
    }



}