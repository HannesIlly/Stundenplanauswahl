package schedule;

import java.util.*;

public class Module {
    private Map<String,Set<Lecture>> lectures;
    private int weight;
    private String name;

    public Module(String name, int weight) {
        this(name, new HashMap<String,Set<Lecture>>(), weight);
    }

    public Module(String name, Map<String, Set<Lecture>> lectures, int weight) {
        if(weight < 0) {
            throw new IllegalArgumentException(weight + " in module " + name + " is not an integer number >= 0");
        }
        this.name = name;
        this.lectures = lectures;
        this.weight = weight;
    }

    public Module(String name, Lecture lecture) {
        this(name, 0);
        Set<Lecture> set = new HashSet<>();
        set.add(lecture);
        this.lectures.put(lecture.getName(), set);

    }

    public Collection<Set<Lecture>> getLectures() {
        return lectures.values();
    }

    public void addLecture(Lecture lecture) {
        if (lectures.containsKey(lecture.getName())) {
            lectures.get(lecture.getName()).add(lecture);
        }
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getSumOfWeights() {
        int weight = 0;
        for (Set<Lecture> set : getLectures()) {
            for (Lecture l : set) {
                weight += this.weight + l.getWeight();
            }
        }
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(name, module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        String s = name + ":\n";
        for (Set<Lecture> set : this.getLectures()) {
            s += set.toString();
        }
        s += "\n";
        return s;
    }
}
