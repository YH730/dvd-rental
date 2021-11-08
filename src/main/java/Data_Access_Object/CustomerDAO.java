package Data_Access_Object;

import com.example.project_java2.Customer;
import com.example.project_java2.Membership;

import java.util.List;

public interface CustomerDAO {

    List<Customer> showAll();

    boolean addNew(Customer customer);

    boolean del(int id);

    boolean upDatebyDoubleClicking(int id, Object newValue, Object dbfield);

    List<Customer> lookUpWithLastName(String lastname);

    List<Customer> findAllWithStatus(Membership status);

}
