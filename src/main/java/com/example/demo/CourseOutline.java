@Service
public class CourseService{
    private static final Logger LOG = LogFactory.getLogger(CourseService.class);
    private list<Course> courses = new ArrayList<>();   

    @Tool(name = "CL_get_tutorial", description="gets a list of courses")
    public List<Course> getCourses() {
        return courses;
    } 

    @Tool(name = "CL_get_tutorial", description = "get a single course by course title")
    public Course getCourse(String title){
        return courses.stream()
            .filter(course -> course.title().equals(title))
            .findFirst()
            .orElse(null);
    }

    @PostConstruct
    public void init() {
        course.add(List.of(
            new Course("Tool annotation", "https://docs.spring.io/spring-ai/reference/api/tools.html"),
            new Course("PostConstruct annotation", "https://medium.com/must-read-articles-in-a-minute/preconstruct-and-postconstruct-annotation-12ff7c868f8b")
        ));
    }`
}