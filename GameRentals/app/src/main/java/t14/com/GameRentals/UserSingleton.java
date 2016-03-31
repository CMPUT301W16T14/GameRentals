package t14.com.GameRentals;

/**
 *
 *
 *
 * @see: MainActivity
 *
 * Created by JL on 3/30/2016.
 */
public class UserSingleton {
    private User user;

    private static UserSingleton instance;

    protected UserSingleton() {
        super();
    }

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }


    public void setUser(User logged_in_user) {
        user = logged_in_user;
    }
    public User getUser() {
        return user;
    }
}
