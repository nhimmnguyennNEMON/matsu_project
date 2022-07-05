
package model;

/**
 *
 * @author SY NGUYEN
 */
public class Contact extends BaseModel{
    
    private String fullname;
    private String email;
    private String mobile;
    private int categoryid;
    private String message;
    private String response;
    private int status;

    public Contact() {
    }

    public Contact(String fullname, String email, String mobile, int categoryid, String message, String response, int status) {
        this.fullname = fullname;
        this.email = email;
        this.mobile = mobile;
        this.categoryid = categoryid;
        this.message = message;
        this.response = response;
        this.status = status;
    }

    public Contact(String fullname, String email, String mobile, int categoryid, String message, String response, int status, int id) {
        super(id);
        this.fullname = fullname;
        this.email = email;
        this.mobile = mobile;
        this.categoryid = categoryid;
        this.message = message;
        this.response = response;
        this.status = status;
    }

    public Contact(String fullname, String email, String mobile, String message) {
        this.fullname = fullname;
        this.email = email;
        this.mobile = mobile;
        this.categoryid = categoryid;
        this.message = message;
        this.status = status;
    }

    

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
        return "Contact{" + "fullname=" + fullname + ", email=" + email + ", mobile=" + mobile + ", categoryid=" + categoryid + ", message=" + message + ", response=" + response + ", status=" + status + '}';
    }
    
    
}
