import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("students", Student.all());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String student_name = request.queryParams("student_name");
      String enroll_date = request.queryParams("enroll_date");
      Student newStudent = new Student(student_name, enroll_date);
      newStudent.save();
      response.redirect("/students");
      return null;
    });

    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String description = request.queryParams("description");
      String number = request.queryParams("number");
      Course newCourse = new Course(description, number);
      newCourse.save();
      response.redirect("/courses");
      return null;
    });

    //
    // get("/result", (request, response) -> {
    //   String textInput = request.queryParams("textInput");
    //
    //   //call business logic functions here
    //   String result = textInput;
    //
    //   HashMap model = new HashMap();
    //   model.put("template", "templates/output.vtl");
    //   model.put("result", String.format(result));
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
      //additional pages would go here
  }

  //public static 'Returntype' 'FuncName' (Paramtype param) {}  //first business logic function

}
