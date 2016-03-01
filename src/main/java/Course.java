import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Course {
  private String description;
  private String number;
  private int id;

  public String getDescription() {
    return description;
  }

  public String getNumber() {
    return number;
  }

  public int getId() {
    return id;
  }

  public Course (String description, String number) {
    this.description = description;
    this.number = number;
  }

  public static  List<Course> all() {
    String sql = "SELECT id, description, number FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  @Override
  public boolean equals(Object otherCourse) {
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getDescription().equals(newCourse.getDescription()) &&
      this.getNumber().equals(newCourse.getNumber()) &&
      this.getId() == newCourse.getId();
    }
  }

  public void save() {
    String sql = "INSERT INTO courses (description, number) VALUES (:description, :number)";
     try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", description)
        .addParameter("number", number)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    String sql = "SELECT id, description, number FROM courses WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Course course = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Course.class);
      return course;
    }
  }

  public void updateDescription(String description) {
    String sql ="UPDATE courses SET description = :description WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("description", description)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateNumber(String number) {
    String sql ="UPDATE courses SET number = :number WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("number", number)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    String sql ="DELETE FROM courses WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addStudent (Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id) VALUES (:student_id, :course_id)";
      con.createQuery(sql)
      .addParameter("course_id", this.getId())
      .addParameter("student_id", student.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT student_id FROM students_courses WHERE course_id = :course_id";
      List<Integer> studentIds = con.createQuery(sql)
      .addParameter("course_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Student> students = new ArrayList<Student>();

        for (Integer studentId : studentIds) {
          String studentQuery = "SELECT * FROM students WHERE id = :studentId";
          Student student = con.createQuery(studentQuery)
          .addParameter("studentId", studentId)
          .executeAndFetchFirst(Student.class);
          students.add(student);
        }
        return students;
      }
    }
  }
