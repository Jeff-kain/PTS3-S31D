/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.Models;

/**
 *
 * @author Rob
 */
public final class User {
    
    private static int id;
    private static String name;
    
    public User(){
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setId(int Id) {
        id = Id;
    }
    
    public void setName(String Name) {
        name = Name;
    }
}
