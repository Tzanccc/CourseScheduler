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
        // To implement priorities (breaks), build a schedule containing the priorities and use that as the 4th parameter
        generateScheduleHelper(generatedSchedule, courses, 0, new Schedule(priority));
        return generatedSchedule;
    }

    private static void generateScheduleHelper(ArrayList<Schedule> schedules, ArrayList<Course> courses, int depth, Schedule workingSchedule) {
        // The function makes an important assumption that none of the elements are null, and all courses have at least one section
        if (depth == courses.size()) {
            schedules.add(workingSchedule);
            return;
        }

        Course currCourse = courses.get(depth);
        for (int i = 0; i < currCourse.getSectionsLength(); i++) {
            /*
            Dependency check behaviour:
            If main section fails: don't add any dependency sections, skip to next section
            If single dependency section fails: skip to next dependency section
            If all dependency sections fail: don't add main section, skip to next main section
             */
            if (!Schedule.hasConflict(currCourse.getSections(i), workingSchedule)) continue;
            if (currCourse.getSections(i).hasDependencies()) {
                generateScheduleHelper(schedules, courses, depth, workingSchedule, currCourse.getSections(i), currCourse.getSections(i).getDependencies());
            } else {
                Schedule copySchedule = new Schedule(workingSchedule);
                copySchedule.addCourse(new Course(currCourse, currCourse.getSections(i), null));
                generateScheduleHelper(schedules, courses, depth + 1, copySchedule);
            }
        }
    }

    private static void generateScheduleHelper(ArrayList<Schedule> schedules, ArrayList<Course> courses, int depth, Schedule workingSchedule, Section currSection, ArrayList<Section> dependencies) {
        for (Section dependency : dependencies) {
            Schedule copySchedule = new Schedule(workingSchedule);
            if (!Schedule.hasConflict(dependency, workingSchedule)) continue;
            copySchedule.addCourse(new Course(currSection.getCourse(), currSection, dependency));
            generateScheduleHelper(schedules, courses, depth + 1, copySchedule);
        }
    }

    public static boolean hasConflict(Section section, Schedule workingSchedule) {
        for (int i = 0; i < workingSchedule.length(); i++) {
            Course temp = workingSchedule.getCourses(i);
            for (int j = 0; j < temp.getSectionsLength(); j++) {
                if (!section.hasConflict(temp.getSections(j))) return false;
            }
        }
        return true;
    }
}
