import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ScheduleTester {

    @Test
    public void testTwoCoursesNoConflict() {
        Course cs200 = new Course("cs200", false);
        ArrayList<Section> cs200Section = new ArrayList<Section>(Arrays.asList(new Section("lec001", cs200), new Section("lec002", cs200)));
        cs200Section.get(0).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 1100, 1215})));
        cs200Section.get(1).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 930, 1045}, new int[]{3, 1430, 1545})));
        cs200.setSections(cs200Section);

        Course math221 = new Course("math221", false);
        ArrayList<Section> math221Section = new ArrayList<>(Arrays.asList(new Section("lec001", math221), new Section("lec002", math221)));
        math221Section.get(0).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 1430, 1545}, new int[]{4, 1430, 1545})));
        math221Section.get(1).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{1, 1320, 1410}, new int[]{2, 1320, 1410}, new int[]{3, 1320, 1410})));
        math221.setSections(math221Section);

        ArrayList<Schedule> schedules = Schedule.generateSchedule(new ArrayList<Course>(Arrays.asList(cs200, math221)), new ArrayList<>());
        assertEquals("cs200", schedules.get(0).getCourses(0).getCode());
        assertEquals("lec001", schedules.get(0).getCourses(0).getSections(0).getCode());
        assertEquals("math221", schedules.get(0).getCourses(1).getCode());
        assertEquals("lec001", schedules.get(0).getCourses(1).getSections(0).getCode());
        assertEquals("lec002", schedules.get(1).getCourses(1).getSections(0).getCode());
        assertEquals("lec002", schedules.get(2).getCourses(0).getSections(0).getCode());
        assertEquals("lec001", schedules.get(2).getCourses(1).getSections(0).getCode());
        assertEquals("lec002", schedules.get(3).getCourses(1).getSections(0).getCode());
    }

    @Test
    public void testTwoCoursesWithConflict() {
        Course cs200 = new Course("cs200", false);
        ArrayList<Section> cs200Section = new ArrayList<Section>(Arrays.asList(new Section("lec001", cs200), new Section("lec002", cs200)));
        cs200Section.get(0).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 1100, 1215}, new int[]{4, 930, 1045})));
        cs200Section.get(1).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 1430, 1545})));
        cs200.setSections(cs200Section);

        Course math221 = new Course("math221", false);
        ArrayList<Section> math221Section = new ArrayList<>(Arrays.asList(new Section("lec001", math221), new Section("lec002", math221)));
        math221Section.get(0).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 1430, 1545}, new int[]{4, 1430, 1545})));
        math221Section.get(1).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{1, 1320, 1410}, new int[]{3, 1320, 1410}, new int[]{5, 1320, 1410})));
        math221.setSections(math221Section);

        ArrayList<Schedule> schedules = Schedule.generateSchedule(new ArrayList<Course>(Arrays.asList(cs200, math221)), new ArrayList<>());
        assertEquals(3, schedules.size());
        assertEquals("lec001", schedules.get(0).getCourses(0).getSections(0).getCode());
        assertEquals("lec001", schedules.get(0).getCourses(1).getSections(0).getCode());
        assertEquals("lec002", schedules.get(2).getCourses(0).getSections(0).getCode());
        assertEquals("lec002", schedules.get(2).getCourses(1).getSections(0).getCode());
    }

    @Test
    public void testTwoCoursesWithDependencyWithConflict() {
        Course cs200 = new Course("cs200", false);
        ArrayList<Section> cs200Lec001Dependencies = new ArrayList<>(Arrays.asList(new Section("lab001", cs200)));
        cs200Lec001Dependencies.get(0).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 1430, 1545}, new int[]{4, 1430, 1545})));
        ArrayList<Section> cs200Section = new ArrayList<>(Arrays.asList(new Section("lec001", cs200, cs200Lec001Dependencies)));
        cs200Section.get(0).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{4, 1100, 1215})));
        cs200.setSections(cs200Section);

        Course math221 = new Course("math221", false);
        ArrayList<Section> math221Lec001Dependencies = new ArrayList<>(Arrays.asList(new Section("dis001", math221), new Section("dis002", math221)));
        math221Lec001Dependencies.get(0).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 1430, 1545}, new int[]{4, 1430, 1545})));
        math221Lec001Dependencies.get(1).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{2, 955, 1045}, new int[]{4, 955, 1045})));
        ArrayList<Section> math221Section = new ArrayList<>(Arrays.asList(new Section("lec001", math221, math221Lec001Dependencies)));
        math221Section.get(0).setTimes(new ArrayList<int[]>(Arrays.asList(new int[]{1, 1320, 1410}, new int[]{3, 1320, 1410})));
        math221.setSections(math221Section);

        ArrayList<Schedule> schedules = Schedule.generateSchedule(new ArrayList<Course>(Arrays.asList(math221)), new ArrayList<Course>());
//        assertEquals(1, schedules.size());
        System.out.println(schedules.size());
        for (Schedule schedule : schedules) {
            System.out.println("Schedule " + schedule.length() + "\n" + schedule);
        }
    }

}
