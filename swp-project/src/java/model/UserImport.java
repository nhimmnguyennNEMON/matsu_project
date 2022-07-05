/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pallgree
 */
public class UserImport extends BaseModel {
    private String team;
    private String full_name;
    private String roll_number;
    private String email;
    private boolean leader;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        return "UserImport{" + "team=" + team + ", full_name=" + full_name + ", roll_number=" + roll_number + ", email=" + email + ", leader=" + leader + '}';
    }
    
    
    
    
    
    
    
    
}
