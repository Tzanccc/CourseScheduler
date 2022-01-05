import java.util.ArrayList;

public class Course {
    private String code;
    private ArrayList<Section> sections;
    private boolean isPriority;

    public Course(String code) {
        this.code = code;
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
}
