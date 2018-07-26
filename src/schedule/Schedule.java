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

    public void printSchedule() {
        calculateTimetables();

        System.out.println("Anzahl: " + timetables.size());
        System.out.println("Bestes Ergebnis:        " + ((TreeSet<Timetable>) timetables).first().getWeights());
        System.out.println("Schlechtestes Ergebnis: " + ((TreeSet<Timetable>) timetables).last().getWeights());
        System.out.println();
        for (Timetable t : timetables) {
            System.out.println("Gesamtgewicht: " + t.getWeights());
            t.print();
            System.out.println("\n");
        }
    }

    private void calculateTimetables() {
        for (Module m : modules) {
            for (Set<Lecture> lectures : m.getLectures()) {
                Set<Timetable> newTimetables = new TreeSet<>();
                for (Lecture l : lectures) {
                    for (Timetable t : timetables) {
                        Timetable oldTimetable = new Timetable(t);
                        if (!oldTimetable.addLecture(l)) {
                            Timetable newTimetable = new Timetable(t);
                            newTimetable.addlectureReplace(l);
                            newTimetable.addWeight(m.getWeight());
                            newTimetables.add(newTimetable);
                        }
                        oldTimetable.addWeight(m.getWeight());
                        newTimetables.add(oldTimetable);
                    }
                }
                timetables = newTimetables;
            }
        }
    }






}