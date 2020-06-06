package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

public class AccountSettings extends Controller {


    //Renders the account settings.
    public static void index()
    {
        Member member = Accounts.getLoggedInMember();
        Logger.info("Rendering AccountSettings");
        render("accountsettings.html", member);
    }

    /*
    Method used to change an existing members details. The data passed in is
    fed into the setter methods from the Member model and then saved.
     */
    public static void changeDetails(String name, String email, String password, String address,
                                     String gender, double height, double startingWeight)
    {
        Member member = Accounts.getLoggedInMember();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setAddress(address);
        member.setGender(gender);
        member.setHeight(height);
        member.setStartingWeight(startingWeight);
        member.save();
        redirect("/accountsettings");

    }
}
