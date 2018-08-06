package schedule;

import commands.LectureCommand;
import commands.ModuleCommand;

import java.io.*;
import java.util.*;

public class ScheduleIO {

    /**
     * Reads a new Schedule from a csv file in which the data has to be in the following order:
     * Commands will be converted into lower case, names will not.
     * The order of modules and lectures does not matter.
     * <p>
     * "modul",name,name_vorlesung1,name_vorlesung2,...,gewicht or <p>
     * "vorlesung",name,block_enum1,block_enum2,...,gewicht
     *
     * @param path the path of the schedule file
     * @return the schedule that was extracted from the file
     */
    public static Schedule readSchedule(String path, String separator) throws IllegalArgumentException, IOException {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader in = new BufferedReader(fileReader);

        Set<Module> modules = new HashSet<>();
        Set<Lecture> lectures = new HashSet<>();

        String line = in.readLine();
        int lineCount = 1;
        String[] data = null;
        while (line != null) {
            data = line.split(separator);
            if ("".equals(line) || line.startsWith("//")) {// skip empty line or commentary
            } else if ("modul".equalsIgnoreCase(data[0])) {
                ModuleCommand module = new ModuleCommand(line, separator);
                modules.add(module.getModule());
            } else if ("vorlesung".equalsIgnoreCase(data[0])) {
                LectureCommand lecture = new LectureCommand(line, separator);
                lectures.add(lecture.getLecture());
            } else {
                String marking = "  ";
                for (int i = 0; i < data[0].length(); i++) {
                    marking += "^";
                }
                throw new IllegalArgumentException("The argument \"" + data[0] + "\" is not supported. (line " + lineCount + ")\n> " + line + "\n" + marking);
            }
            line = in.readLine();
            lineCount++;
        }
        matchLectureToModule(modules, lectures);

        return new Schedule(modules);
    }

    /**
     * Adds all lectures to their modules and creates modules for each lecture that has none.
     *
     * @param modules  the set of modules
     * @param lectures the set of lectures
     * @throws IllegalArgumentException if in every module, there are no lectures registered which don't exist in the module
     */
    private static void matchLectureToModule(Set<Module> modules, Set<Lecture> lectures) throws IllegalArgumentException {
        // add every lecture to its module
        for (Module module : modules) {
            for (Lecture lecture : lectures) {
                module.addLecture(lecture); //only adds the lecture if the name is already known to the module
            }
        }
        // if a module has a reference to a lecture it doesn't contain, throw exception
        for (Module module : modules) {
            for (Set<Lecture> set : module.getLectures()) {
                if (set.isEmpty()) {
                    throw new IllegalArgumentException("The module " + module.getName() + " contains a nonexistent lecture");
                }
            }
        }
        // create a module for every lecture that has no module yet
        for (Lecture lecture : lectures) {
            boolean moduleExists = false;
            for (Module module : modules) {
                for (Set<Lecture> set : module.getLectures()) {
                    if (set.contains(lecture)) {
                        moduleExists = true;
                        break;
                    }
                }
            }
            if (!moduleExists) {
                // if the module already existed, but with another lecture in it, it will be added to the lecture
                if (!modules.add(new Module(lecture.getName(), lecture))) {
                    for (Module module : modules) {
                        module.addLecture(lecture);
                    }
                }
            }
        }
    }



}
