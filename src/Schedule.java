import java.util.ArrayList;

public class Schedule {
    private ArrayList<Course> courses;

    public Schedule() {
        courses = new ArrayList<>();
    }

    public Schedule(Schedule schedule) {
        this.courses = schedule.getCourses();
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
        /*
        permutationsImpl(collections, res, 0, new LinkedList<T>());

        private static <T> void permutationsImpl(List<Collection<T>> ori, Collection<List<T>> res, int d, List<T> current) {
            // if depth equals number of original collections, final reached, add and return
            if (d == ori.size()) {
                res.add(current);
                return;
            }

            // iterate from current collection and copy 'current' element N times, one for each element
            Collection<T> currentCollection = ori.get(d);
            for (T element : currentCollection) {
                List<T> copy = Lists.newLinkedList(current);
                // need to check for conflicts here, continue if conflict detected
                copy.add(element);
                permutationsImpl(ori, res, d + 1, copy);
            }
        }
         */
        ArrayList<Schedule> generatedSchedule = new ArrayList<>();

        // To implement priorities (free time), build a schedule containing the priorities and use that as the 4th parameter
        generateScheduleHelper(generatedSchedule, courses, 0, new Schedule());

        return generatedSchedule;
    }

    private static void generateScheduleHelper(ArrayList<Schedule> schedules, ArrayList<Course> courses, int depth, Schedule workingSchedule) {
        if (depth == courses.size()) {
            schedules.add(workingSchedule);
            return;
        }

        Course currCourse = courses.get(depth);
        for (int i = 0; i < currCourse.getSectionsLength(); i++) {
            Schedule copySchedule = new Schedule(workingSchedule);
            // Dependency check goes here
            if (currCourse.getSections(i).hasDependencies()) generateScheduleHelper(currCourse.getSections(i).getDependencies(), copySchedule);
            if (!Schedule.hasConflict(currCourse.getSections(i), copySchedule)) continue;
            copySchedule.addCourse(new Course(currCourse.getSections(i).getCourse(), currCourse.getSections(i)));
            generateScheduleHelper(schedules, courses, depth + 1, copySchedule);
        }
    }

    private static void generateScheduleHelper(ArrayList<Section> sections, Schedule workingSchedule) {
        for (Section section : sections) {
            if (!Schedule.hasConflict(section, workingSchedule)) continue;
            workingSchedule.addCourse(new Course(section.getCourse(), section));
        }
    }

    public static boolean hasConflict(Section section, Schedule workingSchedule) {
//        if (section.hasDependencies()) {
//            for (int i = 0; i < section.getDependenciesLength(); i++) {
//                if(!hasConflict(section.getDependencies(i), workingSchedule)) return false;
//            }
//        }

        //Dependencies will be handled in generateSchedule

        for (int i = 0; i < workingSchedule.length(); i++) {
            Course temp = workingSchedule.getCourses(i);
            for (int j = 0; j < temp.getSectionsLength(); j++) {
                if (!temp.getSections(j).hasConflict(section)) return false;
            }
        }
        return true;
    }
}
