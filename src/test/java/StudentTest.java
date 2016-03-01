import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Student firstStudent = new Student("Sally", "1900/01/01");
    Student secondStudent = new Student("Sally", "1900/01/01");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_addsInstanceOfStudentToDatabase() {
    Student newStudent = new Student("Sally", "1900/01/01");
    newStudent.save();
    Student savedStudent = Student.all().get(0);
    assertTrue(newStudent.equals(savedStudent));
  }



}
