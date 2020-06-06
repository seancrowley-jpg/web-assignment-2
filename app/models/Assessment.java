package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Date;


@Entity
public class Assessment extends Model {
    public Calendar date = Calendar.getInstance();
    public Date time;
    public double weight;
    public double chest;
    public double thigh;
    public double upperarm;
    public double waist;
    public double hips;
    public String comment;

    public Assessment(Date time, double weight, double chest, double thigh, double upperarm, double waist, double hips)
    {
        this.time = date.getTime();
        this.weight = weight;
        this.chest = chest;
        this.thigh = thigh;
        this.upperarm = upperarm;
        this.waist = waist;
        this.hips = hips;

    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}