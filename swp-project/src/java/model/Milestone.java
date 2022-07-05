/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author quang
 */
public class Milestone extends BaseModel {

    private String milestone_name;
    private Iteration iter;
    private Class class_id;
    private String from_date;
    private String to_date;
    private String desc;
    private int status;

    public Milestone() {
    }

    public Milestone(String milestone_name, Iteration iter, Class class_id, String from_date, String to_date, String desc, int status) {
        this.milestone_name = milestone_name;
        this.iter = iter;
        this.class_id = class_id;
        this.from_date = from_date;
        this.to_date = to_date;
        this.desc = desc;
        this.status = status;
    }

    public Milestone(String milestone_name, Iteration iter, Class class_id, String from_date, String to_date, String desc, int status, int id) {
        super(id);
        this.milestone_name = milestone_name;
        this.iter = iter;
        this.class_id = class_id;
        this.from_date = from_date;
        this.to_date = to_date;
        this.desc = desc;
        this.status = status;
    }

    public String getMilestone_name() {
        return milestone_name;
    }

    public void setMilestone_name(String milestone_name) {
        this.milestone_name = milestone_name;
    }

    public Iteration getIter() {
        return iter;
    }

    public void setIter(Iteration iter) {
        this.iter = iter;
    }

    public Class getClass_id() {
        return class_id;
    }

    public void setClass_id(Class class_id) {
        this.class_id = class_id;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Milestone{" + "milestone_name=" + milestone_name + ", iter=" 
                + iter + ", class_id=" + class_id
                + ", from_date=" + from_date + ", to_date=" + to_date + ", status="
                + status + ", desc=" + desc + '}';
    }

}
