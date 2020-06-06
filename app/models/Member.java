package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class Member extends Model {
    public String name;
    public String email;
    public String password;
    public String address;
    public String gender;
    public double height;
    public double startingWeight;
    public double BMI;
    public boolean ideal;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessment> assessments = new ArrayList<Assessment>();


    public Member(String name, String email, String password, String address,
                  String gender, double height, double startingWeight){
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.height = height;
        this.startingWeight = startingWeight;
    }

    //Method that returns a member with the associated email
    public static Member findByEmail(String email) {
        return find("email", email).first();
    }

    //Checks if the password entered matches the password of the member
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    //returns the assessment at position zero of the array
    public Assessment latestAssessment() {
        Assessment assessment = assessments.get(0);
        return assessment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setStartingWeight(double startingWeight) {
        this.startingWeight = startingWeight;
    }

    public void trendTest()
    {
    }


}