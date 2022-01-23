package CourseScheduler.Backend.Schedule;

import CourseScheduler.Backend.Course.Course;
import CourseScheduler.Backend.Section.Section;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;

public class Schedule {

    @Id
    @SequenceGenerator(name="schedule_sequence", sequenceName="schedule_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="schedule_sequence")
    int id;
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
        // To implement priorities (breaks), schedules are built upon the list of priority courses and sections
        generateScheduleHelper(generatedSchedule, courses, 0, new Schedule(priority));
        return generatedSchedule;
    }

    // The function makes an important assumption that none of the elements are null, and all courses have at least one section
    private static void generateScheduleHelper(ArrayList<Schedule> schedules, ArrayList<Course> courses, int depth, Schedule workingSchedule) {
        // All courses have been iterated through, add workingSchedule to list of schedules
        if (depth == courses.size()) {
            schedules.add(workingSchedule);
            return;
        }

        // Iterating through list of courses
        Course currCourse = courses.get(depth);
        // Iterating through sections of courses
        for (int i = 0; i < currCourse.getSectionsLength(); i++) {
            // Check for conflicts with existing sections in workingSchedule
            if (!Schedule.hasConflict(currCourse.getSections(i), workingSchedule)) continue;
            // If section has dependencies, run helper method to handle dependencies
            if (currCourse.getSections(i).hasDependencies()) {
                generateScheduleHelper(schedules, courses, depth, workingSchedule, currCourse.getSections(i), currCourse.getSections(i).getDependencies());
            } else {
                Schedule copySchedule = new Schedule(workingSchedule);
                copySchedule.addCourse(new Course(currCourse, currCourse.getSections(i), null));
                // Recursive call with incremented depth
                generateScheduleHelper(schedules, courses, depth + 1, copySchedule);
            }
        }
    }

    // Helper method to handle section dependencies
    private static void generateScheduleHelper(ArrayList<Schedule> schedules, ArrayList<Course> courses, int depth, Schedule workingSchedule, Section currSection, ArrayList<Section> dependencies) {
        // Iterates through dependencies
        for (Section dependency : dependencies) {
            Schedule copySchedule = new Schedule(workingSchedule);
            // Checks dependency sections for conflicts
            if (!Schedule.hasConflict(dependency, workingSchedule)) continue;
            copySchedule.addCourse(new Course(currSection.getCourse(), currSection, dependency));
            // Recursive call to the main method with incremented depth
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
