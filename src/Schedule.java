import java.util.ArrayList;
import java.util.Arrays;

public class Schedule {
    private ArrayList<Course> courses;

    public Schedule() {
        this.courses = new ArrayList<>();
    }

    public Schedule(ArrayList<Course> courses) {
        this.courses = new ArrayList<>(courses);
    }

    public Schedule(Schedule schedule) {
        this.courses = new ArrayList<>(schedule.getCourses());
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Course getCourses(int i) {
        return courses.get(i);
    }

    public int length() {
        return courses.size();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public String toString() {
        String s = "///////\n";
        for (Course course : courses) {
            s += course.toString();
        }
        s += "///////\n";
        return s;
    }

    public static ArrayList<Schedule> generateSchedule(ArrayList<Course> courses, ArrayList<Course> priority) {
        ArrayList<Schedule> generatedSchedule = new ArrayList<>();
        // To implement priorities (free time), build a schedule containing the priorities and use that as the 4th parameter
        generateScheduleHelper(generatedSchedule, courses, 0, new Schedule(priority));
        return generatedSchedule;
    }

    private static void generateScheduleHelper(ArrayList<Schedule> schedules, ArrayList<Course> courses, int depth, Schedule workingSchedule) {
        if (depth == courses.size()) {
            schedules.add(workingSchedule);
            return;
        }

        Course currCourse = courses.get(depth);
        outerLoop:
        for (int i = 0; i < currCourse.getSectionsLength(); i++) {
            Schedule copySchedule = new Schedule(workingSchedule);
            /*
            Dependency check behaviour:
            If main section fails: don't add any dependency sections, skip to next section
            If single dependency section fails: skip to next dependency section
            If all dependency sections fail: don't add main section, skip to next main section
             */
            if (!Schedule.hasConflict(currCourse.getSections(i), copySchedule)) continue;
            if (currCourse.getSections(i).hasDependencies()) {
                for (Section section : currCourse.getSections(i).getDependencies()) {
                    Course copyCourse = new Course(currCourse, new ArrayList<>(Arrays.asList(currCourse.getSections(i))));
                    if (!Schedule.hasConflict(section, workingSchedule)) continue;
                    copyCourse.getSections(0).setDependencies(new ArrayList<>(Arrays.asList(section)));
                    copySchedule.addCourse(copyCourse);
                    generateScheduleHelper(schedules, courses, depth + 1, copySchedule);
                }
            } else {
                copySchedule.addCourse(new Course(currCourse, new ArrayList<>(Arrays.asList(currCourse.getSections(i)))));
                generateScheduleHelper(schedules, courses, depth + 1, copySchedule);
            }

//            if (currCourse.getSections(i).hasDependencies()) { //generateScheduleHelper(currCourse.getSections(i).getDependencies(), copySchedule);
//                for (Section section : currCourse.getSections(i).getDependencies()) {
//                    if (!Schedule.hasConflict(section, workingSchedule)) continue outerLoop;
//                    workingSchedule.addCourse(new Course(section.getCourse(), new ArrayList<>(Arrays.asList(section))));
//                }
//            }
//            if (!Schedule.hasConflict(currCourse.getSections(i), copySchedule)) continue;
//            copySchedule.addCourse(new Course(currCourse, new ArrayList<>(Arrays.asList(currCourse.getSections(i)))));
//            generateScheduleHelper(schedules, courses, depth + 1, copySchedule);
        }
    }

    private static void generateScheduleHelper(Course course, ArrayList<Section> dependencies, int depth, Schedule workingSchedule) {
        for (Section section : dependencies) {
            Schedule copySchedule = new Schedule(workingSchedule);
            if (Schedule.hasConflict(section, copySchedule)) continue;
            course.getSections(0).setDependencies(new ArrayList<>(Arrays.asList(section)));
            copySchedule.addCourse(course);
            generateScheduleHelper();
        }
    }

//    private static void generateScheduleHelper(ArrayList<Section> sections, Schedule workingSchedule) {
//        for (Section section : sections) {
//            if (!Schedule.hasConflict(section, workingSchedule)) continue;
//            workingSchedule.addCourse(new Course(section.getCourse(), section));
//        }
//    }

    public static boolean hasConflict(Section section, Schedule workingSchedule) {
//        if (section.hasDependencies()) {
//            for (int i = 0; i < section.getDependenciesLength(); i++) {
//                if(!hasConflict(section.getDependencies(i), workingSchedule)) return false;
//            }
//        }

        // Dependencies will be handled in generateSchedule

        for (int i = 0; i < workingSchedule.length(); i++) {
            Course temp = workingSchedule.getCourses(i);
            for (int j = 0; j < temp.getSectionsLength(); j++) {
                if (!temp.getSections(j).hasConflict(section)) return false;
            }
        }
        return true;
    }
}
