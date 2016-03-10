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

    public void setList(ArrayList<User> list) {
        this.list = list;
    }
}
