/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksserver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;

/**
 *
 * @author Claire
 */
public class NetworksServerTest {
    
    private UserStore mockUserStore = Mockito.mock(UserStore.class);
    private MyService mockMyService = Mockito.mock(MyService.class);
    
    private NetworksServer networksServer;
    
    
    public NetworksServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        networksServer = new NetworksServer(mockMyService); 
    }
    
    @After
    public void tearDown() {
        networksServer = null;
    }

    /**
     * Test of parseInput method, of class NetworksServer.
     */
    @Test
    public void test_send_accepted() {
        Mockito.when(mockMyService.send()).thenReturn( "Tom" );
        
        String message = "send test";
        String results = networksServer.parseInput( message );
        
        Mockito.verify(mockMyService).send();
        assertEquals("Tom: test", results);
    }
    
    @Test
    public void test_send_rejected() {
        Mockito.when(mockMyService.send()).thenReturn( null );
        
        String message = "send test";
        String results = networksServer.parseInput( message );
        
        Mockito.verify(mockMyService).send();
        assertEquals("A user must be logged in before the send command can be used.", results);
    }
    
    @Test
    public void test_login_accepted() {
        Mockito.when(mockMyService.login(any(), any())).thenReturn( LoginStatus.ACCEPTED );
        
        String message = "login Tom Tom11";
        String results = networksServer.parseInput( message );
        
        Mockito.verify(mockMyService).login(any(), any());
        assertEquals("Tom is now logged in.", results);
    }
    
    @Test
    public void test_login_blocked() {
        Mockito.when(mockMyService.login(any(), any())).thenReturn( LoginStatus.BLOCKED );
        
        String message = "login Tom Tom11";
        String results = networksServer.parseInput( message );
        
        assertEquals("Cannot login while another user is logged in.", results);
    }
    
    @Test
    public void test_login_error_user_null() {
        Mockito.when(mockMyService.login(any(), any())).thenReturn( LoginStatus.ERROR );
        
        String message = "login " + null + " Tom11";
        String results = networksServer.parseInput( message );
        
        assertEquals("Invalid command parameters: login requires a userId and password.", results);
    }
    
    @Test
    public void test_login_error_user_empty() {
        Mockito.when(mockMyService.login(any(), any())).thenReturn( LoginStatus.ERROR );
        
        String message = "login   Tom11";
        String results = networksServer.parseInput( message );
        
        assertEquals("Invalid command parameters: login requires a userId and password.", results);
    }
    
    @Test
    public void test_login_error_password_null() {
        Mockito.when(mockMyService.login(any(), any())).thenReturn( LoginStatus.ERROR );
        
        String message = "login  Tom " + null;
        String results = networksServer.parseInput( message );
        
        assertEquals("Invalid command parameters: login requires a userId and password.", results);
    }
    
    @Test
    public void test_login_error_password_empty() {
        Mockito.when(mockMyService.login(any(), any())).thenReturn( LoginStatus.ERROR );
        
        String message = "login Tom  ";
        String results = networksServer.parseInput( message );
        
        assertEquals("Invalid command parameters: login requires a userId and password.", results);
    }
    
    @Test
    public void test_login_rejected() {
        Mockito.when(mockMyService.login(any(), any())).thenReturn( LoginStatus.REJECTED );
        
        String message = "login Tom Tom22";
        String results = networksServer.parseInput( message );
        
        assertEquals("The submitted userId or password is incorrect.", results);
    }
    
    @Test
    public void test_login_method_error() {
        Mockito.when(mockMyService.login(any(), any())).thenReturn( null );
        
        String message = "login doesnt matter";
        String results = networksServer.parseInput( message );
        
        assertEquals("There was an error with the login method.", results);
    }
    
    @Test
    public void test_new_user_accepted() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.ACCEPTED );
        
        String message = "newuser Claire Claire22";
        String results = networksServer.parseInput( message );
        
        assertEquals("Claire is now a user.", results);
    }
    
    @Test
    public void test_new_user_blocked() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.BLOCKED );
        
        String message = "newuser Claire Claire22";
        String results = networksServer.parseInput( message );
        
        assertEquals("Cannot create a new user while someone is logged in.", results);
    }
    
    @Test
    public void test_new_user_error_user_id_null() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.ERROR );
        
        String message = "newuser " + null + " Claire22";
        String results = networksServer.parseInput( message );
        
        assertEquals("The userId must be less than 32 characters, and the password must be between 4 and 8 characters.", results);
    }
    
    @Test
    public void test_new_user_error_user_id_empty() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.ERROR );
        
        String message = "newuser  Claire22";
        String results = networksServer.parseInput( message );
        
        assertEquals("The userId must be less than 32 characters, and the password must be between 4 and 8 characters.", results);
    }
    
    @Test
    public void test_new_user_error_user_id_too_long() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.ERROR );
        
        String message = "newuser 123456789012345678901234567890123 Claire22";
        String results = networksServer.parseInput( message );
        
        assertEquals("The userId must be less than 32 characters, and the password must be between 4 and 8 characters.", results);
    }
    
    @Test
    public void test_new_user_error_password_null() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.ERROR );
        
        String message = "newuser Claire " + null;
        String results = networksServer.parseInput( message );
        
        assertEquals("The userId must be less than 32 characters, and the password must be between 4 and 8 characters.", results);
    }
    
    @Test
    public void test_new_user_error_password_too_short() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.ERROR );
        
        String message = "newuser Claire 123";
        String results = networksServer.parseInput( message );
        
        assertEquals("The userId must be less than 32 characters, and the password must be between 4 and 8 characters.", results);
    }
    
    @Test
    public void test_new_user_error_password_too_long() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.ERROR );
        
        String message = "newuser Claire 123456789";
        String results = networksServer.parseInput( message );
        
        assertEquals("The userId must be less than 32 characters, and the password must be between 4 and 8 characters.", results);
    }
    
    @Test
    public void test_new_user_user_id_not_unique() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( NewUserStatus.NOT_UNIQUE );
        
        String message = "newuser Tom blah";
        String results = networksServer.parseInput( message );
        
        assertEquals("The userId you submitted is already taken. Choose a different userId.", results);
    }
    
    @Test
    public void test_new_user_method_error() {
        Mockito.when(mockMyService.newUser(any(), any())).thenReturn( null );
        
        String message = "newuser doesnt matter";
        String results = networksServer.parseInput( message );
        
        assertEquals("There was an error with the newuser method.", results);
    }
    
    @Test
    public void test_logout_accepted() {
        Mockito.when(mockMyService.logout()).thenReturn( true );
        
        String message = "logout";
        String results = networksServer.parseInput( message );
        
        assertEquals("Logout successful.", results);
    }
    
    @Test
    public void test_logout_rejected() {
        Mockito.when(mockMyService.logout()).thenReturn( false );
        
        String message = "logout";
        String results = networksServer.parseInput( message );
        
        assertEquals("There is no one logged in.", results);
    }

    /**
     * Test of start method, of class NetworksServer.
     */
    @Ignore
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        int port = 0;
        NetworksServer instance = new NetworksServer();
        instance.start(port);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stop method, of class NetworksServer.
     */
    @Ignore
    @Test
    public void testStop() throws Exception {
        System.out.println("stop");
        NetworksServer instance = new NetworksServer();
        instance.stop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class NetworksServer.
     */
    @Ignore
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        NetworksServer.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
