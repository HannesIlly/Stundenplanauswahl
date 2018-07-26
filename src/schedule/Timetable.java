package schedule;

import java.util.*;

public class Timetable implements Comparable<Timetable> {
    private Lecture[] timetable;
    private int weights = 0;

    public Timetable() {
        timetable = new Lecture[30];
    }

    public Timetable(Timetable t) {
        //copy-constructor
        this.timetable = t.timetable.clone();
        this.weights = t.weights;
    }

    /**
     * Adds a lecture to the timetable, if all required blocks are free.
     *
     * @param l the lecture
     * @return {@code true} if the lecture was added successfully, {@code false} if any of the required blocks is already used
     */
    public boolean addLecture(Lecture l) {
        for (Block block : l.getBlocks()) {
            if (timetable[block.ordinal()] != null) {
                return false;
            }
        }
        for (Block block : l.getBlocks()) {
            timetable[block.ordinal()] = l;
        }
        weights += l.getWeight();
        return true;
    }

    /**
     * Adds a lecture to the timetable, replacing other lectures if necessary.
     *
     * @param l the lecture that is added
     * @return {@code true} if the lecture was added without replacing any other lecture, {@code false} if lectures were replaced <p>
     * same return value as in {@code boolean addLecture(Lecture l)}
     */
    public boolean addlectureReplace(Lecture l) {
        boolean nolecturesReplaced = true;
        for (Block block : l.getBlocks()) {
            Lecture removedLecture = timetable[block.ordinal()];
            if (removedLecture != null) {
                nolecturesReplaced = false;
                removeLecture(removedLecture);
            }
            timetable[block.ordinal()] = l;
            weights += l.getWeight();
        }
        return nolecturesReplaced;
    }

    private void removeLecture(Lecture l) {
        for (Block b : l.getBlocks()) {
            timetable[b.ordinal()] = null;
        }
        weights -= l.getWeight();
    }

    public int getWeights(){
        return this.weights;
    }

    public void addWeight(int moduleWeight) {
        this.weights += moduleWeight;
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
