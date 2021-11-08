package com.example.project_java2;

import Data_Access_Object.RentalInfoDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** SQL start:
 * select *,
 *        count(r.borrowed_film_id) as currently_borrowed,
 *        f.Freigabe - count(r.borrowed_film_id) as availability,
 *        if(f.Freigabe - count(r.borrowed_film_id) > 0, true, false) as available
 * from film f
 *     join rental_info r
 * where r.return_date IS NULL
 *   AND f.id = r.borrowed_film_id
 * group by f.id;
 */

public class RentalInfoList implements RentalInfoDAO {
    List<RentalInfo> list;

    @Override
    public List<RentalInfo> showAll() {
        try{
            String selectSql = "select *,\n" +
                    "       count(r.borrowed_film_id) as currently_borrowed,\n" +
                    "       f.Freigabe - count(r.borrowed_film_id) as availability,\n" +
                    "       if(f.Freigabe - count(r.borrowed_film_id) > 0, true, false) as available\n" +
                    "from film f\n" +
                    "join rental_info r\n" +
                    "join customer c on c.id = r.id\n"+
                    "where r.return_date IS NULL\n" +
                    "  AND f.id = r.borrowed_film_id\n" +
                    "group by c.id";
            ResultSet rs = DBConnect.getConnection().createStatement().executeQuery(selectSql);

            list = new ArrayList<RentalInfo>();
            while(rs.next()){
                RentalInfo rentalInfo = new RentalInfo();
                rentalInfo.setCustomerId(rs.getInt("c.id"));
                rentalInfo.setFilmTitle(rs.getString("Titel"));
                rentalInfo.setLast_visit(LocalDate.parse(rs.getString("last_visit")));
                rentalInfo.setQty(rs.getInt("currently_borrowed"));
                rentalInfo.setAvailableQty(rs.getInt("availability"));
                list.add(rentalInfo);
            }
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return list;
    };

    @Override
    public boolean addNew(RentalInfo borrow) throws Exception {
        /* need to create customer first
         * then use the just created id
         * insert it as id into borrowed info (last_vist date ...) */

        if (!isBorrowAble(borrow)) {
            throw new Exception("DVD is not available or the customer is yet added");
        } else {
            try {
                String insertSql = "insert into rental_info (id, last_visit, borrowed_film_id) VALUE (?, ?, ?)";
                PreparedStatement searchStatement = DBConnect.getConnection().prepareStatement(insertSql);
                searchStatement.setInt(1, borrow.getCustomerId());
                searchStatement.setDate(2, Date.valueOf(borrow.getLast_visit()));
                searchStatement.setInt(3, borrow.getFilmId());
                searchStatement.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return list.add(borrow);
        }
    }
    @Override
    public boolean del(int id) {
        try {
            String sqlDel = "DELETE FROM rental_info WHERE id="+id;
            DBConnect.getConnection().createStatement().executeUpdate(sqlDel);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean upDatebyDoubleClicking(int id, Object newValue, Object dbfield) {
        String updateSql = "UPDATE rental_info SET " + dbfield + " = '" + newValue + "' WHERE id=" + id;
        try {DBConnect.getConnection().createStatement().executeUpdate(updateSql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<RentalInfo> lookUpWithCustomerId(int customerId) {

        try{
            String sqlSelect = "select *,\n" +
                    "       count(r.borrowed_film_id) as currently_borrowed,\n" +
                    "       f.Freigabe - count(r.borrowed_film_id) as availability,\n" +
                    "       if(f.Freigabe - count(r.borrowed_film_id) > 0, true, false) as available\n" +
                    "from film f\n" +
                    "         join rental_info r\n" +
                    "         join customer c on c.id = r.id\n" +
                    "where r.return_date IS NULL\n" +
                    "  AND f.id = r.borrowed_film_id\n" +
                    "    And c.id =" + customerId + "\n" +
                    "group by f.id";
            ResultSet rs = DBConnect.getConnection().createStatement().executeQuery(sqlSelect);
            list = new ArrayList<RentalInfo>();
            while (rs.next()){
                RentalInfo ri = new RentalInfo();
                ri.setCustomerId(rs.getInt("c.id"));
                ri.setFilmTitle(rs.getString("Titel"));
                ri.setLast_visit(LocalDate.parse(rs.getString("last_visit")));
                ri.setQty(rs.getInt("currently_borrowed"));
                ri.setAvailableQty(rs.getInt("availability"));
                list.add(ri);
            }
        }catch(ClassNotFoundException | SQLException e ){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean isBorrowAble(RentalInfo borrowable) {
        for (RentalInfo r: list) {
            if(r.getFilm().isBorrowable()) return true;
        }
        return true;
    }
}
