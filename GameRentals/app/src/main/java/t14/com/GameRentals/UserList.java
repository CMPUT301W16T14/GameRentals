package t14.com.GameRentals;

import java.util.ArrayList;

/**
 * Created by margaret on 16/3/7.
 */
public class UserList {
    ArrayList<User> list;

    public UserList() {
        list = new ArrayList<User>();
    }

    public ArrayList<User> getList() {
        return list;
    }

    public void addUser(User user) {
        list.add(user);
    }

    public User getUser(String UserName){
        int i;
        for (i = 0; i < list.size(); i++){
            if (list.get(i).getUserName() == UserName)
                return list.get(i);
        }
        return null;
    }
}
