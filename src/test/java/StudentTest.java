import org.junit.*;
import static org.junit.Assert.*;

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



}
