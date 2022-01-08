import java.util.ArrayList;

public class Course {
    protected String code;
    protected ArrayList<Section> sections;
    protected boolean isPriority;

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

    public Course(Course course, ArrayList<Section> sections) {
        this.code = course.code;
        this.sections = new ArrayList<>(sections);
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
