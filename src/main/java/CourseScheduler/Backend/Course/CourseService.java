package CourseScheduler.Backend.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourse() {
        return courseRepository.findAll();
    }

    public Course addNewCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(int id) {
        if (courseRepository.existsById(id)) {
            throw new IllegalArgumentException("does not exist");
        } else {
            courseRepository.deleteById(id);
        }
    }

//    public void updateCourse() {
//
//    }
}
