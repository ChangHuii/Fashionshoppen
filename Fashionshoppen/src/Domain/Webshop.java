/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;


import java.util.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.*;

/**
 *
 * @author jonaspedersen
 */
public final class Webshop
{

    private static Webshop instance = null;
    Catalog catalog;
    Customer customer;
    Employee employee;
    Item item;
    Product product;
    User user;
    ServicesFacade sf;
    
    MessageDigest md;

    public Webshop()
    {

        catalog = new Catalog();
        sf = new ServicesFacade();

    }

    public static Webshop getInstance()
    {

        if (instance == null)
        {
            instance = new Webshop();
        }

        return instance;
    }
    

    public ArrayList createProductsArray(){
        ArrayList products = catalog.showProducts();
        return products;
        
    }
    
       public void createProduct(String name, String category, String gender, Double price){
        sf.createProduct(name, category, gender, price);
    }
       
       public void deleteProduct(int productId){
        sf.deleteProduct(productId);
    }
    
        public void editProductName(int productId, String productName){
        sf.editProductName(productId, productName);
    }
    
    public void editProductCategory(int productId, String productCategory){
        sf.editProductCategory(productId, productCategory);
    }
    
    public void editProductGender(int productId, String productGender){
        sf.editProductGender(productId, productGender);
    }
    
    public void editProductPrice(int productId, Double price){
        sf.editProductPrice(productId, price);
    }
    
    public void editProductPicture(int productId, String imagePath){
        sf.editProductPicture(productId, imagePath);
    }
    
    public void browseCategory(String category, String name)
    {
        sf.browseCategory(category, name);
    }

    public void browseProductName(String name)
    {
        sf.browseProductName(name);
    }

    public void registerCustomer(String firstName, String lastName, String email, String password)
    {

        String encryptedPass = encryptPassword(password);
        customer = new Customer(firstName, lastName, email, encryptedPass);
        customer.registerUser(customer);
    }

    public User checkUserType(String email, String password)
    {
        Customer returnedCustomer = null;
        Employee returnedEmployee = null;
        String encryptedPass = encryptPassword(password);
        customer = new Customer(email, encryptedPass);
        returnedCustomer = (Customer) customer.loginUser(customer);

        if (returnedCustomer == null)
        {
            employee = new Employee(email, encryptedPass);
            returnedEmployee = (Employee) employee.loginUser(employee);
            return returnedEmployee;
        }
        else
        {
            return returnedCustomer;
        }

    }
    public String encryptPassword(String password)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.digest(passBytes);
            byte[] digest = md.digest(passBytes);

            for (int i = 0; i < digest.length; i++)
            {
                sb.append(Integer.toHexString(0xff & digest[i]));
            }

        }
        catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Webshop.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
    
    public void displayProduct(Product product){
        this.product = product;
    }
    
    public void createProduct(Product product){
        
    }

    public Product getProduct()
    {
        return product;
    }   

    public void addItem(int product_id, int amount)
    {
        if (customer == null)
        {
            customer = new Customer(" ", " ");
            System.out.println("created random GUEST customer");
        }
        
        if (customer.getOrder() == null)
        {
            customer.setOrder(new Order(customer.getUser_id(), customer.getAddress(), new Item(product_id, amount)));
            System.out.println("Order was created and item added");
        }
        else
        {
            customer.getOrder().addItem(new Item(product_id, amount));
            System.out.println("item was added to basket");
        }
    }

    public void createOrder()
    {

    }
}
