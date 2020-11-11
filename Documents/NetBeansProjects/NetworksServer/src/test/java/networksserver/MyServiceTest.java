/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksserver;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.mockito.Mockito;

/**
 *
 * @author Claire
 */
public class MyServiceTest {

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    MyService myService;

    public MyServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<User> userList = new ArrayList<>();
        
        User user1 = new User("David", "David22");
        userList.add(user1);
        
        User user2 = new User("Tom", "Tom11");
        userList.add(user2);
        
        Mockito.when(mockUserStore.load()).thenReturn(userList);
        
        this.myService = new MyService(mockUserStore);
        this.myService.init();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class MyService.
     */
    @Test
    public void testLoginAccepted() {
        String userId = "David";
        String password = "David22";

        LoginStatus expResult = LoginStatus.ACCEPTED;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginRejectedPassword() {
        String userId = "David";
        String password = "David11";

        LoginStatus expResult = LoginStatus.REJECTED;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_login_rejected_unknown_userid() {
        String userId = "Dav";
        String password = "David22";

        LoginStatus expResult = LoginStatus.REJECTED;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_login_error_userid_null() {
        String userId = null;
        String password = "David22";

        LoginStatus expResult = LoginStatus.ERROR;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_login_error_userid_empty() {
        String userId = "";
        String password = "David22";

        LoginStatus expResult = LoginStatus.ERROR;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
    }

    @Test
    public void test_login_error_password_null() {
        String userId = "David";
        String password = null;

        LoginStatus expResult = LoginStatus.ERROR;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_login_error_password_empty() {
        String userId = "David";
        String password = "";

        LoginStatus expResult = LoginStatus.ERROR;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_previous_login() {
        String userId = "David";
        String password = "David22";
        
        LoginStatus expResult = LoginStatus.ACCEPTED;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
        
        LoginStatus expResult2 = LoginStatus.BLOCKED;
        LoginStatus result2 = myService.login(userId, password);
        assertEquals(expResult2, result2);
    }
    
    /**
     * Test of logout method, of class MyService.
     */
    @Test
    public void test_logout_works() {
        String userId = "David";
        String password = "David22";

        LoginStatus expResult = LoginStatus.ACCEPTED;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);
        
        boolean expResult2 = true;
        boolean result2 = myService.logout();
        assertEquals(expResult2, result2);
    }
    
    @Test
    public void test_logout_when_no_one_logged_in() {
        boolean expResult = false;
        boolean result = myService.logout();
        assertEquals(expResult, result);
    }

    /**
     * Test of send method, of class MyService.
     */
    @Test
    public void test_send_accepted() {
        String userId = "David";
        String password = "David22";
        
        LoginStatus expResult = LoginStatus.ACCEPTED;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);        
        
        String expResult2 = "David";
  
        String result2 = myService.send();
        assertEquals(expResult2, result2);
    }
    
    @Test
    public void test_send_rejected_no_one_logged_in() {    
        String expResult = null;
  
        String result = myService.send();
        assertEquals(expResult, result);
    }

    /**
     * Test of newUser method, of class MyService.
     */
    @Test
    public void test_newUser_accepted() {
        String userId = "Claire";
        String password = "Claire55";
        
        NewUserStatus expResult = NewUserStatus.ACCEPTED;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_userid_null() {
        String userId = null;
        String password = "Claire55";
        
        NewUserStatus expResult = NewUserStatus.ERROR;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_userid_empty() {
        String userId = "";
        String password = "Claire55";
        
        NewUserStatus expResult = NewUserStatus.ERROR;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_userid_too_long() {
        String userId = "123456789012345678901234567890123";
        String password = "Claire55";
        
        NewUserStatus expResult = NewUserStatus.ERROR;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_password_null() {
        String userId = "Claire";
        String password = null;
        
        NewUserStatus expResult = NewUserStatus.ERROR;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_password_too_short() {
        String userId = "Claire";
        String password = "123";
        
        NewUserStatus expResult = NewUserStatus.ERROR;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_password_too_long() {
        String userId = "Claire";
        String password = "123456789";
        
        NewUserStatus expResult = NewUserStatus.ERROR;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_password_short_okay() {
        String userId = "Claire";
        String password = "1234";
        
        NewUserStatus expResult = NewUserStatus.ACCEPTED;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_password_long_okay() {
        String userId = "Claire";
        String password = "12345678";
        
        NewUserStatus expResult = NewUserStatus.ACCEPTED;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_user_id_not_unique() {
        String userId = "Tom";
        String password = "blah";
        
        NewUserStatus expResult = NewUserStatus.NOT_UNIQUE;
        NewUserStatus result = myService.newUser(userId, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_newUser_while_logged_in() {
        String userId = "David";
        String password = "David22";

        LoginStatus expResult = LoginStatus.ACCEPTED;
        LoginStatus result = myService.login(userId, password);
        assertEquals(expResult, result);

        userId = "doesnt";
        password = "matter";
        
        NewUserStatus expResult2 = NewUserStatus.BLOCKED;
        NewUserStatus result2 = myService.newUser(userId, password);
        assertEquals(expResult2, result2);
    }
}
