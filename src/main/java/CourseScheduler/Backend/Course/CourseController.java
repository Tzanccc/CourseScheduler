package CourseScheduler.Backend.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourse() {
        return courseService.getCourse();
    }

    @PostMapping
    public Course addNewCourse(Course course) {
        return courseService.addNewCourse(course);
    }

    @DeleteMapping(path = "{courseID}")
    public void deleteCourse(@PathVariable("courseID") int id) {
        courseService.deleteCourse(id);
    }

//    @PutMapping(path = "{courseID}")
//    public void updateCourse(@PathVariable("courseID") int id, String name) {
//        courseService.updateCourse(id, name);
//    }

}
