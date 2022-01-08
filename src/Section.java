import java.util.ArrayList;

public class Section {
    private String code;
    private Course course;
    private ArrayList<int[]> times;
    private boolean hasDependencies;
    private ArrayList<Section> dependencies;

    public Section(String code, Course course) {
        this.code = code;
        this.course = course;
        this.times = new ArrayList<>();
        this.hasDependencies = false;
        this.dependencies = new ArrayList<>();
    }

    public Section(String code, Course course, ArrayList<Section> dependencies) {
        this.code = code;
        this.course = course;
        this.times = new ArrayList<>();
        this.hasDependencies = true;
        this.dependencies = dependencies;
    }

    public Section(Section section, ArrayList<Section> dependencies) {
        this.code = section.code;
        this.course = section.course;
        this.times = section.times;
        this.hasDependencies = section.hasDependencies;
        this.dependencies = dependencies;
    }

//    public Section(Section section) {
//        this.code = section.code;
//        this.course = section.course;
//        this.times = section.times;
//        this.hasDependencies = section.hasDependencies;
//        this.dependencies = section.dependencies;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<int[]> getTimes() {
        return times;
    }

    public int[] getTimes(int i) {
        return times.get(i);
    }

    public int getTimesLength() {
        return times.size();
    }

    public void setTimes(ArrayList<int[]> times) {
        this.times = times;
    }

    public void addTimes(int[] time) {
        times.add(time);
    }

    public boolean hasDependencies() {
        return hasDependencies;
    }

    public ArrayList<Section> getDependencies() {
        return dependencies;
    }

    public Section getDependencies(int i) {
        return dependencies.get(i);
    }

    public int getDependenciesLength() {
        return dependencies.size();
    }

    public void setDependencies(ArrayList<Section> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependencies(Section section) {
        dependencies.add(section);
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public boolean hasConflict(Section other) {
        if (other.hasDependencies) {
            for (Section dependency : other.getDependencies()) {
                if (!hasConflict(dependency)) return false;
            }
        }
        for (int i = 0; i < times.size(); i++) {
            for (int j = 0; j < other.getTimesLength(); j++) {
                if (times.get(i)[0] == other.getTimes(j)[0]) {
                    if ((times.get(i)[1] <= other.getTimes(j)[1] && other.getTimes(j)[1] <= times.get(i)[2]) || (times.get(i)[1] <= other.getTimes(j)[2] && other.getTimes(j)[2] <= times.get(i)[2])) return false;
                    if ((other.getTimes(j)[1] <= times.get(i)[1] && times.get(i)[1] <= other.getTimes(j)[2]) || (other.getTimes(j)[1] <= times.get(i)[2] && times.get(i)[2] <= other.getTimes(j)[2])) return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        String s = "-" + code + "-\n";
        for (int[] time : times) {
            switch (time[0]) {
                case 1:
                    s += "Monday";
                    break;
                case 2:
                    s += "Tuesday";
                    break;
                case 3:
                    s += "Wednesday";
                    break;
                case 4:
                    s += "Thursday";
                    break;
                case 5:
                    s += "Friday";
                    break;
                case 6:
                    s += "Saturday";
                    break;
                case 7:
                    s += "Sunday";
                    break;
                default:
                    s += "Error: invalid day";
            }
            s += " " + time[1]/100 + ":" + time[1]%100 + " - " + time[2]/100 + ":" + time[2]%100 + "\n";
        }
        if (hasDependencies) {
            for (Section dependency : dependencies) {
                s += dependency.toString();
            }
        }
        s += "---\n";
        return s;
    }
}
