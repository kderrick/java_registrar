import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Student {
  private String student_name;
  private String enroll_date;
  private int id;

  public String getName() {
    return student_name;
  }

  public String getDate() {
    return enroll_date;
  }

  public int getId() {
    return id;
  }

  public Student (String name, String date) {
    this.student_name = name;
    this.enroll_date = date;
  }

  public static  List<Student> all() {
    String sql = "SELECT student_name, enroll_date FROM students";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }
}
