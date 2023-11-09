package com.example.project_java2;

import Data_Access_Object.FilmDAO;
import model.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Film_DAO implements FilmDAO {
    List<Film> list = new ArrayList<>();

    public Film_DAO() {
    }

    @Override
    public List<Film> showAll(){
        try {
                String selectSql = "select *,\n" +
                        "       count(ri.borrowed_film_id) as borrowed,\n" +
                        "       f.Freigabe - count(ri.borrowed_film_id) as availability,\n" +
                        "       if(f.Freigabe - count(ri.borrowed_film_id) > 0, true, false) as available\n" +
                        "       from film f\n" +
                        "join genre g on g.id = f.Genre_id\n" +
                        "left join spielt s on f.id = s.Film_id\n" +
                        "left join hauptdarsteller h on h.id = s.Hauptdarsteller_id\n"+
                        "left join rental_info ri on f.id = ri.borrowed_film_id\n" +
                        "group by f.id";
                ResultSet rs = DBConnect.getConnection().createStatement().executeQuery(selectSql);

                list = new ArrayList<Film>();
                while (rs.next()) {
                    Film her = new Film();
                    her.setId(rs.getInt("id"));
                    her.setTitle(rs.getString("Titel"));
                    her.setGenre(rs.getString("Name")); // Name of genre
                    her.setRelease_date(rs.getDate("Erscheinungsdatum").toLocalDate());
                    her.setImage(rs.getString("Bild"));
                    her.setDescription(rs.getString("Beschreibung"));
                    her.setPrice(rs.getInt("Preis"));
                    her.setAvailability(rs.getInt("availability"));
                    her.setBorrowed(rs.getInt("borrowed"));
                    her.setCast(rs.getString("h.Name")); // Name of cast

                    list.add(her);
                }
            }catch(ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }
            return list;
        };
    @Override
    public boolean addNew(Film film) {
        try{
        String insertSql = "INSERT INTO film (Genre_id, Titel, Erscheinungsdatum, Preis, Freigabe)"+ "VALUES('"+film.getGenre_id()+"','"+film.getTitle()+"','"+film.getRelease_date()+"','"+film.getPrice()+"','"+film.getAvailability()+"')" ;
        DBConnect.getConnection().createStatement().executeUpdate(insertSql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list.add(film);
    }

    @Override
    public boolean upDatebyDoubleClicking(int id, Object newValue, Object dbfield) {
        String updateSql = "UPDATE film SET " + dbfield + " = '" + newValue + "' WHERE id=" + id;
        try {DBConnect.getConnection().createStatement().executeUpdate(updateSql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    Search film(s) with the title
     */
    @Override
    public Film lookUpWithTitle(String title) {
        Film kipo = new Film();
        try {
            String querySql = "select *,\n" +
                    "       count(ri.borrowed_film_id) as borrowed,\n" +
                    "       f.Freigabe - count(ri.borrowed_film_id) as availability,\n" +
                    "       if(f.Freigabe - count(ri.borrowed_film_id) > 0, true, false) as available\n" +
                    "from film f\n" +
                    "         join genre g on g.id = f.Genre_id\n" +
                    "        left join spielt s on f.id = s.Film_id\n" +
                    "        left join hauptdarsteller h on h.id = s.Hauptdarsteller_id\n" +
                    "         left join rental_info ri on f.id = ri.borrowed_film_id\n" +
                    "where Titel like ?";
            PreparedStatement searchStatement = DBConnect.getConnection().prepareStatement(querySql);
            searchStatement.setString(1, title + "%"); // 'title%'
            ResultSet rs = searchStatement.executeQuery();
            if (rs.next()) {
                kipo.setId(rs.getInt("id"));
                kipo.setTitle(rs.getString("Titel"));
                kipo.setGenre(rs.getString("Name")); // Name of genre
                kipo.setRelease_date(rs.getDate("Erscheinungsdatum").toLocalDate());
                kipo.setImage(rs.getString("Bild"));
                kipo.setDescription(rs.getString("Beschreibung"));
                kipo.setGenre(rs.getString("Name"));
                kipo.setPrice(rs.getInt("Preis"));
                kipo.setAvailability(rs.getInt("availability"));
                kipo.setBorrowed(rs.getInt("borrowed"));
                kipo.setCast(rs.getString("lastName")); // Name of cast
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return kipo;
    }

    @Override
    public boolean del(int id) {
        try {
            String sqlDel = "DELETE FROM film WHERE id='"+id+"'";
            DBConnect.getConnection().createStatement().executeUpdate(sqlDel);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    Search film(s) with the lastname of the actor/actress
     */
    @Override
    public List<Film> lookUpWithLastName(String lastname) {
        List<Film> list = new ArrayList<>();

        try {
            String querySql = "select *,\n" +
                    "       count(ri.borrowed_film_id) as borrowed,\n" +
                    "       f.Freigabe - count(ri.borrowed_film_id) as availability,\n" +
                    "       if(f.Freigabe - count(ri.borrowed_film_id) > 0, true, false) as available\n" +
                    "from film f\n" +
                    "        join genre g on g.id = f.Genre_id\n" +
                    "        left join spielt s on f.id = s.Film_id\n" +
                    "        left join hauptdarsteller h on h.id = s.Hauptdarsteller_id\n" +
                    "        left join rental_info ri on f.id = ri.borrowed_film_id\n" +
                    "where h.lastName like ? group by Film_id";

            PreparedStatement searchStatement = DBConnect.getConnection().prepareStatement(querySql);
            searchStatement.setString(1, lastname + "%");
            ResultSet rs = searchStatement.executeQuery();
            while (rs.next()) {
                Film kipo = new Film();
                kipo.setId(rs.getInt("id"));
                kipo.setTitle(rs.getString("Titel"));
                kipo.setGenre(rs.getString("Name")); // Name of genre
                kipo.setRelease_date(rs.getDate("Erscheinungsdatum").toLocalDate());
                kipo.setImage(rs.getString("Bild"));
                kipo.setDescription(rs.getString("Beschreibung"));
                kipo.setPrice(rs.getInt("Preis"));
                kipo.setAvailability(rs.getInt("availability"));
                kipo.setCast(rs.getString("lastName")); // Name of cast

                list.add(kipo);
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Film> findAllWithYear(int releaseDate) {
        List<Film> list = new ArrayList<>();
        try {
            String selectSql = "select *,\n" +
                    "       count(ri.borrowed_film_id) as borrowed,\n" +
                    "       f.Freigabe - count(ri.borrowed_film_id) as availability,\n" +
                    "       if(f.Freigabe - count(ri.borrowed_film_id) > 0, true, false) as available\n" +
                    "from film f\n" +
                    "         join genre g on g.id = f.Genre_id\n" +
                    "        left join spielt s on f.id = s.Film_id\n" +
                    "        left join hauptdarsteller h on h.id = s.Hauptdarsteller_id\n" +
                    "         left join rental_info ri on f.id = ri.borrowed_film_id\n" +
                    "where Erscheinungsdatum like ? group by f.id";
            PreparedStatement searchStatement = DBConnect.getConnection().prepareStatement(selectSql);
            searchStatement.setString(1, releaseDate + "%" );
            ResultSet rs = searchStatement.executeQuery();
            while (rs.next()) {
                Film kipo = new Film();
                kipo.setId(rs.getInt("id"));
                kipo.setTitle(rs.getString("Titel"));
                kipo.setGenre(rs.getString("Name")); // Name of genre
                kipo.setRelease_date(rs.getDate("Erscheinungsdatum").toLocalDate());
                kipo.setImage(rs.getString("Bild"));
                kipo.setDescription(rs.getString("Beschreibung"));
                kipo.setPrice(rs.getInt("Preis"));
                kipo.setAvailability(rs.getInt("availability"));
                kipo.setCast(rs.getString("lastName")); // Name of cast
                list.add(kipo);
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Film> findAllWithGenre(String genre) {
        List<Film> list = new ArrayList<>(); try {
            String selectSql = "select *,\n" +
                    "       count(ri.borrowed_film_id) as borrowed,\n" +
                    "       f.Freigabe - count(ri.borrowed_film_id) as availability,\n" +
                    "       if(f.Freigabe - count(ri.borrowed_film_id) > 0, true, false) as available\n" +
                    "from film f\n" +
                    "         join genre g on g.id = f.Genre_id\n" +
                    "        left join spielt s on f.id = s.Film_id\n" +
                    "        left join hauptdarsteller h on h.id = s.Hauptdarsteller_id\n" +
                    "         left join rental_info ri on f.id = ri.borrowed_film_id\n" +
                    "where g.Name like ? group by f.id";
            PreparedStatement searchStatement = DBConnect.getConnection().prepareStatement(selectSql);
            searchStatement.setString(1, genre + "%");
            ResultSet rs = searchStatement.executeQuery();
            while (rs.next()) {
                Film kipo = new Film();
                kipo.setId(rs.getInt("id"));
                kipo.setTitle(rs.getString("Titel"));
                kipo.setGenre(rs.getString("Name")); // Name of genre
                kipo.setRelease_date(rs.getDate("Erscheinungsdatum").toLocalDate());
                kipo.setImage(rs.getString("Bild"));
                kipo.setDescription(rs.getString("Beschreibung"));
                kipo.setPrice(rs.getInt("Preis"));
                kipo.setAvailability(rs.getInt("availability"));
                kipo.setCast(rs.getString("Vorname" + "lastName")); // Name of cast
                list.add(kipo);
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

}
