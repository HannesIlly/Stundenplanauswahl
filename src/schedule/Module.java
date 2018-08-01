package schedule;

import java.util.*;

public class Module {
    private Map<String, Set<Lecture>> lectures;
    private int weight;
    private String name;

    public Module(String name, int weight) {
        this(name, new HashMap<String, Set<Lecture>>(), weight);
    }

    public Module(String name, Map<String, Set<Lecture>> lectures, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException(weight + " in module " + name + " is not an integer number >= 0");
        }
        this.name = name;
        this.lectures = lectures;
        this.weight = weight;
    }

    public Module(String name, Lecture lecture) {
        this(name, 0);
        this.lectures.put(lecture.getName(), new HashSet<>());
        addLecture(lecture);
    }

    public Collection<Set<Lecture>> getLectures() {
        return lectures.values();
    }

    public void addLecture(Lecture lecture) {
        if (lectures.containsKey(lecture.getName())) {
            lectures.get(lecture.getName()).add(lecture);
            lecture.addWeight(weight);
        }
    }

    public String getName() {
        return this.name;
    }

    public Set<Set<Lecture>> getLectureCombinations() {
        Set<Set<Lecture>> lectureCombinations = new HashSet<>();
        lectureCombinations.add(new HashSet<>());

        for (Set<Lecture> lectureSet : getLectures()) {
            Set<Set<Lecture>> newCombinations = new HashSet<>();
            for (Set<Lecture> currentCombination : lectureCombinations) {
                for (Lecture lecture : lectureSet) {
                    Set<Lecture> newCombination = new HashSet<>(currentCombination);
                    newCombination.add(lecture);
                    newCombinations.add(newCombination);
                }
            }
            lectureCombinations = newCombinations;
        }
        return lectureCombinations;
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
