package CourseScheduler.Backend.Course;

import CourseScheduler.Backend.Section.Section;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table
public class Course {
    @Id
    @SequenceGenerator(name="course_sequence", sequenceName="course_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="course_sequence")
    private int id;
    private String code;
    private ArrayList<Section> sections;
    private boolean isPriority;

    public Course () {
        code = new String();
        sections = new ArrayList<>();
        isPriority = false;
    }

    public Course(String code, boolean isPriority) {
        this.code = code;
        sections = new ArrayList<>();
        this.isPriority = isPriority;
    }

    public Course(Course course, Section section, Section dependency) {
        this.code = course.code;
        this.sections = new ArrayList<>(Arrays.asList(new Section(section, new ArrayList<>(Arrays.asList(dependency)))));
        this.isPriority = course.isPriority();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public Section getSections(int i) {
        return sections.get(i);
    }

    public int getSectionsLength() {
        return sections.size();
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean isPriority) {
        this.isPriority = isPriority;
    }

    public String toString() {
        String s = code + "\n-----\n";
        for (Section section : sections) {
            s += section.toString();
        }
        s += "-----\n";
        return s;
    }
}
