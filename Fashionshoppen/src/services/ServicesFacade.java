/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


/**
 *
 * @author jonaspedersen
 */
public final class ServicesFacade {
    
    private static ServicesFacade instance = null;
    public ServerRequest sr;
    public GetUserCallback userCallback;
    
    public ServicesFacade(){
        sr = new ServerRequest();
    }
    
        public static ServicesFacade getInstance(){
        
        if(instance == null){
            instance = new ServicesFacade();
        }
        
        return instance;
    }
        
    public void browseProductName(String name){
        sr.browseProductName(name);
    }
    
    public void browseCategory(String category, String name){
        sr.browseCategory(category, name);
    }
        
    
}
