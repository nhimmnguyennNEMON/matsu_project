/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class EvaluationCriteria extends BaseModel {
    private int criteria_id;
    private Iteration iteration_id;
    private int evaluation_weight;
    private boolean team_evaluation;
    private int criteria_order;
    private int max_loc;
    private boolean status;

    public EvaluationCriteria() {
    }

    public EvaluationCriteria(int criteria_id, Iteration iteration_id, int evaluation_weight, boolean team_evaluation, int criteria_order, int max_loc, boolean status) {
        this.criteria_id = criteria_id;
        this.iteration_id = iteration_id;
        this.evaluation_weight = evaluation_weight;
        this.team_evaluation = team_evaluation;
        this.criteria_order = criteria_order;
        this.max_loc = max_loc;
        this.status = status;
    }

    public EvaluationCriteria(Iteration iteration_id, boolean team_evaluation, int criteria_order, int max_loc, boolean status) {
        this.iteration_id = iteration_id;
        this.team_evaluation = team_evaluation;
        this.criteria_order = criteria_order;
        this.max_loc = max_loc;
        this.status = status;
    }

    public int getCriteria_id() {
        return criteria_id;
    }

    public void setCriteria_id(int criteria_id) {
        this.criteria_id = criteria_id;
    }

    public Iteration getIteration_id() {
        return iteration_id;
    }

    public void setIteration_id(Iteration iteration) {
        this.iteration_id = iteration;
    }

    public int getEvaluation_weight() {
        return evaluation_weight;
    }

    public void setEvaluation_weight(int evaluation_weight) {
        this.evaluation_weight = evaluation_weight;
    }

    public boolean getTeam_evaluation() {
        return team_evaluation;
    }

    public void setTeam_evaluation(boolean team_evaluation) {
        this.team_evaluation = team_evaluation;
    }

    public int getCriteria_order() {
        return criteria_order;
    }

    public void setCriteria_order(int criteria_order) {
        this.criteria_order = criteria_order;
    }

    public int getMax_loc() {
        return max_loc;
    }

    public void setMax_loc(int max_loc) {
        this.max_loc= max_loc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Evaluation_Criteria{" + "criteria_id=" + criteria_id + ", iteration=" + iteration_id + ", evalution_weight=" + evaluation_weight + ", team_evalution="
                + "" + team_evaluation + ", criteria_order=" + criteria_order + ", max_loc=" + max_loc + ", status=" + status + '}';
    }
    
}
