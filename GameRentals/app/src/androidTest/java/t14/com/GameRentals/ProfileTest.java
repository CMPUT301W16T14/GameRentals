package t14.com.GameRentals;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by apple on 16/3/14.
 */
public class ProfileTest extends ApplicationTestCase<Application> {
    public ProfileTest() {
        super(Application.class);
    }


    public void testProfileData() {
        User lq = new User("Qi Liang","lq@126.com","123");
        assertEquals("Qi Liang", lq.getUserName());
        assertEquals("lq@126.com", lq.getEmail());
        lq.setUserName("LQ666");
        lq.setEmail("abc@126.com");
        assertEquals("LQ666",lq.getUserName());
        assertEquals("abc@126.com", lq.getEmail());
    }


    /*
    public void testViewOtherProfiles() {
        User user1 = new User("user1","email1@myspace.gog","123456");
        User lq = new User("Qi Liang","lq@126.com","123");
        User user2 = new User("user2","email2@myspace.gog","135246");
        user1.viewUser(lq);
        user1.viewUser(user2);

		assertEquals(lq, user1.getUsers("Qi Liang"));
        assertTrue(user1.getUsers("Qi Liang").getEmail.equals("lq@126.com"));
    }
              */

}
