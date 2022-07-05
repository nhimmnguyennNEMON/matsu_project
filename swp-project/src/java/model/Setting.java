
package model;

/**
 *
 * @author admin
 */
public class Setting extends BaseModel {
    private int setting_id;
    private String setting_name;
    private String setting_value;
    private int type_id;
    private int order;
    private int status;
    private String note;
    

    public Setting() {
    }

    public Setting(String setting_name, String setting_value, int type_id, int order, int status, String note) {
        this.setting_name = setting_name;
        this.setting_value = setting_value;
        this.type_id = type_id;
        this.order = order;
        this.status = status;
        this.note = note;
    }

    public Setting(int setting_id, String setting_name, String setting_value, int type_id, int order, int status, String note) {
        this.setting_id = setting_id;
        this.setting_name = setting_name;
        this.setting_value = setting_value;
        this.type_id = type_id;
        this.order = order;
        this.status = status;
        this.note = note;
    }

    public Setting(int setting_id, String setting_name, String setting_value, int type_id, int order, String note) {
        this.setting_id = setting_id;
        this.setting_name = setting_name;
        this.setting_value = setting_value;
        this.type_id = type_id;
        this.order = order;
        this.note = note;
    }

    public int getType_Id() {
        return type_id;
    }

    public void setType_Id(int type_Id) {
        this.type_id = type_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    

    

    public int getSetting_Id() {
        return setting_id;
    }

    public void setSetting_Id(int setting_id) {
        this.setting_id = setting_id;
    }

    public String getSetting_Name() {
        return setting_name;
    }

    public void setSetting_Name(String setting_name) {
        this.setting_name = setting_name;
    }

    public String getSetting_Value() {
        return setting_value;
    }

    public void setSetting_Value(String setting_value) {
        this.setting_value = setting_value;
    }

    

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "setting{" + "setting_id=" + setting_id + ", setting_name=" + setting_name + ", setting_value=" + setting_value + ", type_id=" + type_id + ", order=" + order + ", status=" + status + ", note=" + note + '}';
    }

   
    
    
}
