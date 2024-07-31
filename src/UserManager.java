import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User>users=new ArrayList<>();

    public UserManager() {
    }

    public UserManager(List<User> users) {
        this.users = users;
    }

    public void register(User user){
        users.add(user);
    }
    public User login(String email, String password){
       for (User user:users){
           if(user.getEmail().equals(email)&&user.getPassword().equals(password)){
               return user;
           }
       }
       return null;
    }
    public void main()
}
