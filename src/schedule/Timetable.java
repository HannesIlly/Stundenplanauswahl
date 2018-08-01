package schedule;

import java.util.*;

public class Timetable implements Comparable<Timetable> {
    private Set<Set<Lecture>> lectures = new HashSet<>();
    private Lecture[] timetable;
    private int weights = 0;

    public Timetable() {
        timetable = new Lecture[30];
    }

    /**
     * copy-constructor
     *
     * @param t the timetable that is copied
     */
    public Timetable(Timetable t) {
        this.timetable = t.timetable.clone();
        this.weights = t.weights;
        this.lectures = new HashSet<>(t.lectures);
    }



    /**
     * checks, if the given set of lectures can be added without replacing any other lecture.
     *
     * @param lectures the set of lectures
     * @return {@code true} if the set can be added without replacing anything
     */
    public boolean canAddLectures(Set<Lecture> lectures) {
        for (Lecture lecture : lectures) {
            for (Block block : lecture.getBlocks()) {
                if (timetable[block.ordinal()] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * adds a set of lectures to the timetable and if necessary other modules are replaced.
     *
     * @param lectures the set of lectures that are added
     */
    public void addLectures(Set<Lecture> lectures) {
        if (this.lectures.add(lectures)) {
            for (Lecture lecture : lectures) {
                for (Block block : lecture.getBlocks()) {
                    Lecture oldLecture = timetable[block.ordinal()];
                    if (oldLecture != null) {
                        removeLecture(oldLecture);
                    }
                    timetable[block.ordinal()] = lecture; //replace the lecture

                }
                weights += lecture.getWeight();
            }
        }
    }

    /**
     * removes a lecture from the timetable. All other modules of the corresponding lecture set (module) are removed too.
     *
     * @param lecture the lecture that will be removed
     */
    private void removeLecture(Lecture lecture) {
        for (Set<Lecture> lectureSet : lectures) {
            for (Lecture currentLecture : lectureSet) {
                if (currentLecture.equals(lecture)) {
                    for (Lecture l : lectureSet) {
                        for (Block block : l.getBlocks()) {
                            timetable[block.ordinal()] = null;
                        }
                        weights -= l.getWeight();

                    }
                    lectures.remove(lectureSet);
                    return;
                }
            }
        }
    }

    public int getWeights() {
        return this.weights;
    }

    public void print() {
        String line;
        int colWidth = 15;
        for (int block = 0; block < 6; block++) {
            printSeparatorLine(colWidth);
            line = "|";
            for (int tag = 0; tag < 5; tag++) {
                line += String.format("%-" + colWidth + "s", timetable[tag * 6 + block] == null ? "" : timetable[tag * 6 + block].getName());
                line += "|";
            }
            System.out.println(line);
            printEmptyLine(colWidth);
            if (block == 2) {
                printSeparatorLine(colWidth);
            }
        }
        printSeparatorLine(colWidth);
    }

    private void printSeparatorLine(int colWidth) {
        String line = "|";
        for (int tag = 0; tag < 5; tag++) {
            line += String.format("%-" + colWidth + "s", "-").replace(' ', '-');
            line += "|";
        }
        System.out.println(line);
    }

    private void printEmptyLine(int colWidth) {
        String line = "|";
        for (int tag = 0; tag < 5; tag++) {
            line += String.format("%-" + colWidth + "s", "-").replace('-', ' ');
            line += "|";
        }
        System.out.println(line);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable t = (Timetable) o;
        return weights == t.weights &&
                Objects.equals(timetable, t.timetable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timetable, weights);
    }

    @Override
    public int compareTo(Timetable t) {
        if (t.weights - this.weights != 0) {
            return t.weights - this.weights;
        }
        return -1;
    }
}
