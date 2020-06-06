package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trainer extends Model {
    public String email;
    public String name;
    public String address;
    public String gender;
    public String password;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Member> members = new ArrayList<Member>();


    public Trainer(String email, String name,String address, String gender, String password)
    {
        this.email = email;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.password = password;
    }

    //Method that returns a trainer with the associated email
    public static Trainer findByEmail(String email) {
        return find("email", email).first();
    }

    //Checks if the password entered matches the password of the trainer
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
