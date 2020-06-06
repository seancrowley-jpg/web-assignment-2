package controllers;

import play.*;
import play.mvc.*;

public class About extends Controller
{
  //Renders the about html page
  public static void index()
  {
    Logger.info("Rendering about");
    render("about.html");
  }
}
