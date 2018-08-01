package schedule;

import java.util.Objects;
import java.util.Set;

public class Lecture {
    private String name;
    private Set<Block> blocks;
    private int weight;

    public Lecture(String name, Set<Block> blocks, int weight) throws IllegalArgumentException {
        if(weight < 0) {
            throw new IllegalArgumentException(weight + " in lecture " + name + " is not an integer number >= 0");
        }
        this.name = name;
        this.blocks = blocks;
        this.weight = weight;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight;
    }

    public void addWeight(int weight) {
        this.weight += weight;
    }

    @Override
    public String toString() {
        return "[" + name + "," + blocks.toString() + "," + weight + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return Objects.equals(name, lecture.name) &&
                Objects.equals(blocks, lecture.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, blocks);
    }
}
