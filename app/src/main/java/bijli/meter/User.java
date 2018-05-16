package bijli.meter;

/**
 * Created by prashant on 13/3/18.
 */

public class User {
    public String userAge;
    public String userEmail;
    public String userName;
    public String userPhone;
    public String userId;
    public User(String userId, String userAge,String userEmail, String userName,String userPhone){
        this.userAge=userAge;
        this.userEmail=userEmail;
        this.userName=userName;
        this.userPhone=userPhone;
        this.userId=userId;
    }
}
