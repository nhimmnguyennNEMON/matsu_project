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
public class Subject extends BaseModel {

    private String subject_code;
    private String subject_name;
    private int author_id;
    private int status;

    public Subject() {
    }
    
    public Subject(int id) {
        this.id = id;
    }

    public Subject(String subject_code, String subject_name, int author_id, int status) {
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.author_id = author_id;
        this.status = status;
    }

    public Subject(String subject_code, String subject_name, int author_id, int status, int id) {
        super(id);
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.author_id = author_id;
        this.status = status;
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

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
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

    @Override
    public String toString() {
        return "Subject{" + "subject_id=" + id + ", subject_code=" + subject_code + ", subject_name="
                + subject_name + ", author_id=" + author_id + ", status=" + status + '}';
    }

}
