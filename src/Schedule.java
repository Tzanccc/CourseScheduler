import java.util.ArrayList;

public class Schedule {
    private ArrayList<Course> courses;

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Course getCourses(int i) {
        return courses.get(i);
    }

    public int length() {
        return courses.size();
    }

    public static ArrayList<Schedule> generateSchedule(ArrayList<Course> courses, ArrayList<Course> priorityCourses) {
        ArrayList<Schedule> generatedSchedule = new ArrayList<>();

        return generatedSchedule;
    }

    public static boolean hasConflict(Section section, Schedule workingSchedule) {

//        if (section.hasDependencies()) {
//            for (int i = 0; i < section.getDependenciesLength(); i++) {
//                if(!hasConflict(section.getDependencies(i), workingSchedule)) return false;
//            }
//        }
        /*
        Dependencies will be handled in generateSchedule
         */

        for (int i = 0; i < workingSchedule.length(); i++) {

        }
        return true;
    }
}
