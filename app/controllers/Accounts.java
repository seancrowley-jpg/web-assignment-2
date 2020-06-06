package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;


public class Accounts extends Controller
{
    //Renders the signup page
    public static void signup()
    {
        render("signup.html");
    }

    //Renders the login page
    public static void login()
    {
        render("login.html");
    }

    /*Uses the parameters passed in to create a new member object
    then save the member and redirects to star page*/
    public static void register(String name, String email, String password, String address, String gender, double height, double startingWeight)
    {
        Logger.info("Registering new user " + email);
        Member member = new Member(name, email, password, address, gender, height, startingWeight);
        member.save();
        redirect("/");
    }

    /*Finds the trainer or member attempting to sign via the email entered.
      If the members email exists and the method checkPassword is true.
      Authentication is successful and the user is logged in*/
    public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        Trainer trainer =Trainer.findByEmail(email);
        if ((member != null) && (member.checkPassword(password))) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        }
        else if ( (trainer != null) && (trainer.checkPassword(password)) )
        {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect ("/trainer");
        }
        else {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }

    //Clears the logged in user.
    public static void logout()
    {
        session.clear();
        redirect ("/");
    }

    /*Returns a member object using session.get initialises a string
    called member id which is then passed into the .findById method.
     */
    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }

    /*Returns a trainer object using session.get initialises a string
    called trainer id which is then passed into the .findById method.
     */
    public static Trainer getLoggedInTrainer()
    {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerid = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerid));
        } else {
            login();
        }
        return trainer;
    }
}