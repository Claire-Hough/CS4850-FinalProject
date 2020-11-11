/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;

/**
 *
 * @author Claire
 */
public class UserStore {
    
    //private static final Logger log = LoggerFactory.getLogger(UserStore.class);
    
    private String filePath;
    
    public UserStore(String filePath) {
        //log.info("<init>: filePath = {}", filePath);
        this.filePath = filePath;
    }
    
    public List<User> load() {
        //log.info("load: enter method");
        
        List<User> userList = new ArrayList<>();
        
        try {
            File file = new File(filePath);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                
                data = data.replace("(", "");
                data = data.replace(")", "");
                
                String[] tokens = data.split(",");
                
                User user = new User(tokens[0], tokens[1].replace(" ", ""));
                userList.add(user);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read in file.");
            e.printStackTrace();
            //log.warn("Couldn't read in file: {}", filePath);
        }
        
        return userList;
    }
    
    public void save(List<User> userList) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file, false);
            
            for (User user : userList) {
                fileWriter.write("(" + user.getUserId() + ", " + user.getPassword() + ")\n");
            }
            
            fileWriter.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read in file.");
            e.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Couldn't read in file.");
            ex.printStackTrace();
        }
    }
    
}
