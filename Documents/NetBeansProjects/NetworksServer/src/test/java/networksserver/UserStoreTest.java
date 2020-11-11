/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Claire
 */
public class UserStoreTest {

    private UserStore userStore;

    public UserStoreTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.txt").getFile());
        System.out.println( file.getCanonicalPath());
        this.userStore = new UserStore(file.getCanonicalPath());

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of load method, of class UserStore.
     */
    @Test
    public void testLoad() {
        List<User> result = userStore.load();

        assertEquals(3, result.size());

        assertEquals("Claire", result.get(0).getUserId());
        assertEquals("Claire55", result.get(0).getPassword());

        assertEquals("Todd", result.get(1).getUserId());
        assertEquals("Todd34", result.get(1).getPassword());
    }

    /**
     * Test of save method, of class UserStore.
     */
    @Test
    public void testSave() {
        List<User> userList = new ArrayList<>();
        
        User user1 = new User("Claire", "Claire55");
        userList.add(user1);
        
        User user2 = new User("Todd", "Todd34");
        userList.add(user2);
        
        User user3 = new User("Jacob", "Jacob66");
        userList.add(user3);
        
        userStore.save(userList);
        
        userList = userStore.load();
        assertEquals(3, userList.size());
    }

}
