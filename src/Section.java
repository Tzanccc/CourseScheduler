import java.util.ArrayList;

public class Section {
    private String code;
    private Course course;
    private ArrayList<int[]> times;
    private boolean hasDependencies;
    private ArrayList<Section> dependencies;

    public Section(String code) {
        this.code = code;
    }

    public ArrayList<int[]> getTimes() {
        return times;
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
}
