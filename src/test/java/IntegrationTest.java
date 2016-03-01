import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();




  @Test
  public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("Registrar");
  }


  @Test
  public void addStudent() {
    goTo("http://localhost:4567/students");
    fill("#student_name").with("John");
    fill("#enroll_date").with("1900/01/01");
    submit(".btn");
    assertThat(pageSource()).contains("John");
    assertThat(pageSource()).contains("1900");
  }

  @Test
  public void addCourse() {
    goTo("http://localhost:4567/courses");
    fill("#description").with("History");
    fill("#number").with("101");
    submit(".btn");
    assertThat(pageSource()).contains("History");
    assertThat(pageSource()).contains("101");
  }

  @Test
  public void addCourseToStudent() {
    Student newStudent = new Student("Jim", "12/12/2012");
    newStudent.save();
    Course newCourse = new Course("History", "101");
    newCourse.save();
    String studentPath = String.format("http://localhost:4567/students/%d", newStudent.getId());
    goTo(studentPath);
    assertThat(pageSource()).contains("Jim");
    assertThat(pageSource()).contains("History");
  }
  /*
  @Test
  public void negativeNumber() {
    goTo("http://localhost:4567");
    fill("#userChange").with("-87");
    submit(".btn");
    assertThat(pageSource()).contains("Please enter a positive value");
  }
*/

}
