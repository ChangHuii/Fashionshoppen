package Domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.ServicesFacade;

/**
 *
 * @author aleksander
 */
public abstract class User implements UserManager
{
    private int user_id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    ServicesFacade sf = new ServicesFacade();
    MessageDigest md;

    public User(String firstName, String lastName, String email, String password)
    {
        this.user_id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }
    
    public User(String email, String password)
    {
        this("", "", email, password);
        
    }
    
    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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
    public User checkUserType(User user)
    {
        Customer returnedCustomer;
        Employee returnedEmployee;
        String encryptedPass = encryptPassword(password);
        Customer customer = new Customer(email, encryptedPass);
        returnedCustomer = (Customer) customer.loginUser(customer);

        if (returnedCustomer == null)
        {
            Employee employee = new Employee(email, encryptedPass);
            returnedEmployee = (Employee) employee.loginUser(employee);
            return returnedEmployee;
        }
        else
        {
            return returnedCustomer;
        }

    }
}
