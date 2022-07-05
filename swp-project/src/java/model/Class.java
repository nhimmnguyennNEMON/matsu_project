/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author quang
 */
public class Class extends BaseModel {

    private String class_code;
    private User trainer_id;
    private Subject subject_id;
    private int class_year;
    private int class_term;
    private boolean block5_class;
    private int status;

    public Class() {
    }

    public Class(int id) {
        super(id);
    }

    public Class(int id,String class_code, User trainer_id, Subject subject_id, int class_year, int class_term, boolean block5_class, int status) {
        super(id);
        this.class_code = class_code;
        this.trainer_id = trainer_id;
        this.subject_id = subject_id;
        this.class_year = class_year;
        this.class_term = class_term;
        this.block5_class = block5_class;
        this.status = status;
    }
    
    public Class(String class_code, User trainer_id, Subject subject_id, int class_year, int class_term, boolean block5_class, int status) {
        this.class_code = class_code;
        this.trainer_id = trainer_id;
        this.subject_id = subject_id;
        this.class_year = class_year;
        this.class_term = class_term;
        this.block5_class = block5_class;
        this.status = status;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public User getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(User trainer_id) {
        this.trainer_id = trainer_id;
    }

    public Subject getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Subject subject_id) {
        this.subject_id = subject_id;
    }

    public int getClass_year() {
        return class_year;
    }

    public void setClass_year(int class_year) {
        this.class_year = class_year;
    }

    public int getClass_term() {
        return class_term;
    }

    public void setClass_term(int class_term) {
        this.class_term = class_term;
    }

    public boolean isBlock5_class() {
        return block5_class;
    }

    public void setBlock5_class(boolean block5_class) {
        this.block5_class = block5_class;
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
        return "Class{" + "ID=" + id + ", class_code=" + class_code + "trainer_id=" + trainer_id + ", subject_id=" + subject_id
                + ", class_year=" + class_year + ", class_term=" + class_term + ", block5_class="
                + block5_class + ", status=" + status + '}';
    }

}
