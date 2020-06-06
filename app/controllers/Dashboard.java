package controllers;

import java.util.*;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller {

    /*
    Method that renders the dashboard.lists a members assessments
    and calculated the embers BMI based on the latest assessment
    or starting weight if no assessments exists yet.
     */
    public static void indexMember() {
        Logger.info("Rendering Dashboard");
        Member member = Accounts.getLoggedInMember();
        List<Assessment> assessments = member.assessments;
        if ( assessments.size() > 0 )
            member.BMI = calculateBMI(member,member.latestAssessment());
        else
            member.BMI = calculateStartingBMI(member);
        if ( assessments.size() > 0 )
            member.ideal =isIdealBodyWeight(member,member.latestAssessment());
        render("dashboard.html", member, assessments);
    }

    /*Renders the trainer view and lists all members
      and adds them to the trainer members arrayList.
     */
    public static void indexTrainer() {
        Logger.info("Rendering Dashboard");
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> members = Member.findAll();
        trainer.members.addAll(members);
        render("trainer.html",trainer, members);
    }

    /*
    Method that deletes a members assessment
     */
    public static void deleteAssessment(Long id,Long assessmentid) {
        Logger.info("Deleting Assessment");
        Member member = Member.findById(id);
        Assessment assessment = Assessment.findById(assessmentid);
        member.assessments.remove(assessment);
        Logger.info("Removing" + member.name);
        member.save();
        assessment.delete();
        redirect("/dashboard");
    }

    /*
    Method that adds an assessment for a member. Uses the parameter passed in
    to create a new assessment object and adds it to the assessments arraylist.
    Added to index zero of the array so in order for the assessments to be displayed
    chronologically
     */
    public static void addAssessment(Date time, double weight, double chest, double thigh, double upperarm, double waist, double hips) {
        Member member = Accounts.getLoggedInMember();
        Assessment assessment = new Assessment(time, weight, chest, thigh, upperarm, waist, hips);
        member.assessments.add(0,assessment);
        member.save();
        Logger.info("Adding Assessment for: " + member.name);
        redirect("/dashboard");
    }
    /*
    public static boolean compareWeight() {
        boolean trendUp = false;
        Member member = Accounts.getLoggedInMember();
        List<Double> trendWeight = new ArrayList<>();
        for (Assessment assessment : member.assessments) {
            trendWeight.add(assessment.weight);
            if ( > assessment.weight )
                trendUp = true;
        }
        return trendUp;
    }

     */

    // The following Methods have benn implemented from the Programming Assignment


    //Calculates the BMI of the member
    public static double calculateBMI(Member member, Assessment assessment) {
        return assessment.weight / (member.height * member.height);

    }

    //Calculates the BMI with the starting weight
    public static double calculateStartingBMI(Member member) {
        return member.startingWeight / (member.height * member.height);

    }


    //Returns a String with the BMI category based on the value passed in
    public static String determineBMICategory(double bmiValue) {
        String bmiCategory;
        if ( bmiValue < 16 ) {
            bmiCategory = ("SEVERELY UNDERWEIGHT");
            return bmiCategory;
        } else if ( (bmiValue >= 16) && (bmiValue < 18.5) ) {
            bmiCategory = ("UNDERWEIGHT");
            return bmiCategory;
        } else if ( (bmiValue >= 18.5) && (bmiValue < 25) ) {
            bmiCategory = ("NORMAL");
            return bmiCategory;
        } else if ( (bmiValue >= 25) && (bmiValue < 30) ) {
            bmiCategory = ("OVERWEIGHT");
            return bmiCategory;
        } else if ( (bmiValue >= 30) && (bmiValue < 35) ) {
            bmiCategory = ("MODERATELY OBESE");
            return bmiCategory;
        } else if ( bmiValue >= 35 ) {
            bmiCategory = ("SEVERELY OBESE");
            return bmiCategory;
        } else return null;
    }

    /*Returns a boolean based on the weight of the members assessment
      using the divine formula . If the assessments weight matches the
      value calculated using the formula it returns true*/
    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {
        double devine = 50 + 0.9 * ((member.height * 100) - 152);
        double devineF = 45.5 + 0.9 * ((member.height * 100) - 152);
        if ( (member.gender.equals("M")) && (Math.round(devine) != Math.round(assessment.weight)) ) {
            return false;
        } else if ( (member.gender.equals("M")) && (Math.round(devine) == Math.round(assessment.weight)) ) {
            return true;
        }
        if ( ((member.gender.equals("F")) || (member.gender.equals("Unspecified"))) && (Math.round(devineF) != Math.round(assessment.weight)) ) {
            return false;
        } else if ( ((member.gender.equals("F")) || (member.gender.equals("Unspecified"))) && (Math.round(devineF) == Math.round(assessment.weight)) ) {
            return true;
        }
        else return false;
    }
}

