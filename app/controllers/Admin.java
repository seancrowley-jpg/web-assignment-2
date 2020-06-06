package controllers;

import models.Assessment;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Admin extends Controller
{
  //Renders the admin page and lists th
  public static void index()
  {
    Logger.info("Rendering Admin");
    List<Assessment> assessments = Assessment.findAll();
    List<Member> members = Member.findAll();
    render("admin.html", assessments);
  }
}
