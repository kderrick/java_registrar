import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Course firstCourse = new Course("History", "101");
    Course secondCourse = new Course("History", "101");
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_addsInstanceOfCourseToDatabase() {
    Course newCourse = new Course("History", "101");
    newCourse.save();
    Course savedCourse = Course.all().get(0);
    assertTrue(newCourse.equals(savedCourse));
  }

  @Test
  public void save_assignsIdToObject() {
    Course newCourse = new Course("History", "101");
    newCourse.save();
    Course savedCourse = Course.all().get(0);
    assertEquals(newCourse.getId(), savedCourse.getId());
  }

  @Test
  public void find_locatesAllInstancesOfClassInDatabaseUsingId() {
    Course newCourse = new Course("History", "101");
    newCourse.save();
    Course savedCourse = Course.find(newCourse.getId());
    assertTrue(newCourse.equals(savedCourse));
  }

  @Test
  public void updateDescription_updatesDescriptionOfObject() {
    Course newCourse = new Course("History", "101");
    newCourse.save();
    newCourse.updateDescription("Chemistry");
    assertEquals(Course.all().get(0).getDescription(), ("Chemistry"));
  }

  @Test
  public void updateNumber_updatesNumberOfObject() {
    Course newCourse = new Course("History", "101");
    newCourse.save();
    newCourse.updateNumber("102");
    assertEquals(Course.all().get(0).getNumber(), ("102"));
  }

  @Test
  public void deleteCourse_deleteCourseObject() {
    Course newCourse = new Course("History", "101");
    newCourse.save();
    newCourse.delete();
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void addStudent_addsStudentToCourse() {
    Course newCourse = new Course("History", "101");
    newCourse.save();

    Student newStudent = new Student("Sally", "1900/01/01");
    newStudent.save();

    newCourse.addStudent(newStudent);
    Student savedStudent = newCourse.getStudents().get(0);
    assertTrue(newStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_getsCourseStudentsByCourseID() {
    Course newCourse = new Course("History", "101");
    newCourse.save();

    Student newStudent = new Student("Sally", "1900/01/01");
    newStudent.save();

    newCourse.addStudent(newStudent);
    List savedStudents = newCourse.getStudents();
    assertEquals(savedStudents.size(), 1);
  }
}
