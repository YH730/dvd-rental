package Data_Access_Object;

import com.example.project_java2.RentalInfo;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface RentalInfoDAO {

    List<RentalInfo> showAll();

    boolean addNew(RentalInfo borrow) throws Exception;

    boolean del(int id);

    boolean upDatebyDoubleClicking(int id, Object newValue, Object dbfield);

    List<RentalInfo> lookUpWithCustomerId(int customerId); // customer Id

    boolean isBorrowAble(RentalInfo borrowable);

}