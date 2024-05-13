package com.group16.vesthub;

import javax.sql.DataSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;



@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class DataController {
    
    private DatabaseAdapter dbAdapter;
    public DataController(DataSource dataSource) {
        dbAdapter = new DatabaseAdapter(dataSource);
    }


    @PostMapping("/api/login")
    public boolean receiveDataFromSignIn(@RequestBody String data) {

        System.out.println("Received data from frontend: " + data); 
        
        return true;
    }

    @PostMapping("/api/register")
    public boolean receiveDataFromSignUp(@RequestBody String data) {        
        try 
        {
            // Initialize ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            
            // Parse JSON string to User object
            User user = mapper.readValue(data, User.class);

            // Print received data
            System.out.println("Received data from frontend: " + data);
            if (dbAdapter.checkUserExists(user.getEmail())) 
            {
                return false;
            }
            //insert database
            dbAdapter.insertUser(user.getName(), user.getSurname(), user.getEmail(), "", user.getPassword(), "", "", "", false);
            
            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
      
    }


}

