package Data_Access_Object;

import com.example.project_java2.Film;
import model.State;

import java.time.LocalDate;
import java.util.List;

public interface FilmDAO {

    List<Film> showAll();

    boolean addNew(Film film);

    boolean upDatebyDoubleClicking(int id, Object newValue, Object dbfield);

    Film lookUpWithTitle(String title);

    boolean del(int id);

    List<Film> lookUpWithLastName(String lastname);

    List<Film> findAllWithYear(int releaseDate);

    List<Film> findAllWithGenre(String genre);

}
