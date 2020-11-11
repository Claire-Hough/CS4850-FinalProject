/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Claire
 */
public class MyService {

    private List<User> userList = new ArrayList<>();
    private User loggedInUser;
    private UserStore userStore;

    public MyService(){
        this(new UserStore("C:\\Users\\Claire\\Downloads\\users.txt"));
    }
    
    public MyService(UserStore userStore){
        this.userStore = userStore;
    }
    
    public void init() {
        //userList.add(new User("Tom", "Tom11"));
        //userList.add(new User("David", "David22"));
        //userList.add(new User("Beth", "Beth33"));
        
        this.userList = userStore.load();
    }

    public LoginStatus login(String userId, String password) {

        // check if there is another user logged in already
        if (loggedInUser != null) {
            return LoginStatus.BLOCKED;
        }

        // check if params are correct
        if (userId == null || userId.length() == 0 || password == null || password.length() == 0) {
            return LoginStatus.ERROR;
        }

        // loop through userList to try to find user, then compare password if found
        for (User user : userList) {
            if (userId.equals(user.getUserId()) && password.equals(user.getPassword())) {
                loggedInUser = user;
                return LoginStatus.ACCEPTED;
            }
        }

        // if userId or password don't match userList, login attempt is rejected
        return LoginStatus.REJECTED;
    }

    public boolean logout() {
        // if someone is logged in then log them out
        if (loggedInUser != null) {
            loggedInUser = null;
            return true;
        } // if there is no one logged in, command is invalid
        else {
            return false;
        }

    }

    public String send() {
        // can't send messages if no one is logged in
        if (loggedInUser == null) {
            return null;
        }// send back userId of logged in user
        else {
            return loggedInUser.getUserId();
        }
    }

    public NewUserStatus newUser(String userId, String password) {

        // unable to create new user if someone is currently logged in
        if (loggedInUser != null) {
            return NewUserStatus.BLOCKED;
        }

        // check if params are correct
        if (userId == null || userId.length() == 0 || userId.length() > 32 || password == null || password.length() < 4 || password.length() > 8) {
            return NewUserStatus.ERROR;
        }

        // check that userId is unique
        for (User user : userList) {
            if (userId.equals(user.getUserId())) {
                return NewUserStatus.NOT_UNIQUE;
            }
        }

        // user is added to list if it passes all previous checks
        User user = new User(userId, password);
        userList.add(user);
        return NewUserStatus.ACCEPTED;
    }
}
