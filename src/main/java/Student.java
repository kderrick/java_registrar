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

  public Student (String student_name, String enroll_date) {
    this.student_name = student_name;
    this.enroll_date = enroll_date;
  }

  public static  List<Student> all() {
    String sql = "SELECT id, student_name, enroll_date FROM students";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  @Override
  public boolean equals(Object otherStudent) {
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
      this.getDate().equals(newStudent.getDate()) &&
      this.getId() == newStudent.getId();
    }
  }

  public void save() {
    String sql = "INSERT INTO students (student_name, enroll_date) VALUES (:student_name, :enroll_date)";
     try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("student_name", student_name)
        .addParameter("enroll_date", enroll_date)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
    String sql = "SELECT id, student_name, enroll_date FROM students WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Student student = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void updateName(String student_name) {
    String sql ="UPDATE students SET student_name = :student_name WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("student_name", student_name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateDate(String enroll_date) {
    String sql ="UPDATE students SET enroll_date = :enroll_date WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("enroll_date", enroll_date)
      .addParameter("id", id)
      .executeUpdate();
    }
  }
}
