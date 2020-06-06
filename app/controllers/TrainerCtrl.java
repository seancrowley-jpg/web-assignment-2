package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class TrainerCtrl extends Controller {
    /*
    Method for deleting member objects and removing them from the
    members arraylist
     */
    public static void deleteMember(Long id) {
        Logger.info("Deleting a Playlist");
        Trainer trainer = Accounts.getLoggedInTrainer();
        Member member = Member.findById(id);
        trainer.members.remove(member);
        Logger.info("Removing" + member.name);
        trainer.save();
        member.delete();
        redirect("/trainer");
    }

    /*
    Method the is used to view a members profile
    from the trainer menu. Calculates the members
    BMI.
     */
    public  static void viewMember(Long id)
    {
        Member member = Member.findById(id);
        List<Assessment> assessments = member.assessments;
        if ( assessments.size() > 0 )
            member.BMI = Dashboard.calculateBMI(member,member.latestAssessment());
        else
            member.BMI = Dashboard.calculateStartingBMI(member);
        if ( assessments.size() > 0 )
            member.ideal =Dashboard.isIdealBodyWeight(member,member.latestAssessment());
        Logger.info("Rendering trainerdashboard.html");
        render("trainerdashboard.html",member,assessments);
    }

    /*
    Method for adding a comment to a members assessment.
    Assessment is assigned to the id of the assessment
    being commented on and a setter is used to change the
    comment from blank to a String.
     */
    public static void addComment(Long id, String comment)
    {
        Assessment assessment= Assessment.findById(id);
        assessment.setComment(comment);
        assessment.save();
        Logger.info("Adding comment");
        redirect("/trainer");
    }

}
