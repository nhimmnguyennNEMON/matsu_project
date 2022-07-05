/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author SY NGUYEN
 */
public class Iteration extends BaseModel{
    
    private String iteration_name;
    private String duration;
    private int status_iter;
    private int subject_id;
    private String subject_code;
    private String subject_name;
    private int status_subject;

    public Iteration() {
    }
    
    public Iteration(int id) {
        super(id);
    }

    public Iteration(String iteration_name, String duration, int status_iter, int subject_id, String subject_code, String subject_name, int status_subject) {
        this.iteration_name = iteration_name;
        this.duration = duration;
        this.status_iter = status_iter;
        this.subject_id = subject_id;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.status_subject = status_subject;
    }

    public Iteration(String iteration_name, String duration, int status_iter, int subject_id, String subject_code, String subject_name, int status_subject, int id) {
        super(id);
        this.iteration_name = iteration_name;
        this.duration = duration;
        this.status_iter = status_iter;
        this.subject_id = subject_id;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.status_subject = status_subject;
    }

    public Iteration(String iteration_name, String duration, int status_iter, String subject_code, String subject_name) {
        this.iteration_name = iteration_name;
        this.duration = duration;
        this.status_iter = status_iter;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
    }

    public Iteration(String iteration_name, String duration, int status_iter, String subject_code) {
        this.iteration_name = iteration_name;
        this.duration = duration;
        this.status_iter = status_iter;
        this.subject_code = subject_code;
    }

    public String getIteration_name() {
        return iteration_name;
    }

    public void setIteration_name(String iteration_name) {
        this.iteration_name = iteration_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getStatus_iter() {
        return status_iter;
    }

    public void setStatus_iter(int status_iter) {
        this.status_iter = status_iter;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getStatus_subject() {
        return status_subject;
    }

    public void setStatus_subject(int status_subject) {
        this.status_subject = status_subject;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return "Iteration{" + "id=" + id + ", iteration_name=" + iteration_name + ", duration=" + 
                duration + ", status_iter=" + status_iter + ", subject_id=" + 
                subject_id + ", subject_code=" + subject_code + ", subject_name=" + 
                subject_name + ", status_subject=" + status_subject + '}';
    }

    
    
    
}
