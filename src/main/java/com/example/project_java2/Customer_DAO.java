package com.example.project_java2;

import Data_Access_Object.CustomerDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer_DAO implements CustomerDAO {

    List<Customer> list;

    public Customer_DAO() {
    }

    @Override
    public List<Customer> showAll() {
        try {
            String selectQuery = "SELECT * FROM customer"; // LEFT JOIN rental_info ri ON customer.id = ri.id LEFT JOIN film f on f.id = ri.borrowed_film_id";
            ResultSet rs = DBConnect.getConnection().createStatement().executeQuery(selectQuery);

            list = new ArrayList<Customer>();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstname"));
                customer.setLastName(rs.getString("name"));
                customer.setEMail(rs.getString("email"));
                customer.setMembershipStatus(rs.getString("membershipstatus"));
                list.add(customer);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addNew(Customer customer) {
        try {
            String sqlInsert = "INSERT INTO customer (name, firstname, email, membershipstatus)"+ " VALUES('" + customer.getLastName() + "','" + customer.getFirstName() + "','" + customer.getEMail()+ "','" + customer.getMembershipStatus() + "')" ;
            DBConnect.getConnection().createStatement().executeUpdate(sqlInsert);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list.add(customer);
    }

    @Override
    public boolean del(int id) {
        try {
            String sqlDel = "DELETE FROM customer WHERE id='"+id+"'";
            DBConnect.getConnection().createStatement().executeUpdate(sqlDel);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean upDatebyDoubleClicking(int id, Object newValue, Object dbfield) {
        try {
            DBConnect.getConnection().createStatement().executeUpdate("UPDATE customer SET " + dbfield + " = '" + newValue + "' WHERE id=" + id);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Customer> lookUpWithLastName(String lastname) {
        List<Customer> lookUpWithLastNamelist = new ArrayList<>();

        try {
            PreparedStatement searchStatement = DBConnect.getConnection().prepareStatement("SELECT * FROM customer WHERE name = ?");
            searchStatement.setString(1, lastname);
            ResultSet rs = searchStatement.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstname"));
                customer.setLastName(rs.getString("name"));
                customer.setEMail(rs.getString("email"));
                customer.setMembershipStatus(rs.getString("membershipstatus"));
                lookUpWithLastNamelist.add(customer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lookUpWithLastNamelist;
    }

    @Override
    public List<Customer> findAllWithStatus(Membership status) {
        List<Customer> listWithStatus = new ArrayList<>();
        try {
            String sqlSelect = "SELECT * from customer where membershipstatus = ?";
            PreparedStatement searchStatement = DBConnect.getConnection().prepareStatement(sqlSelect);
            searchStatement.setString(1, String.valueOf(status));
            ResultSet rs = searchStatement.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstname"));
                customer.setLastName(rs.getString("name"));
                customer.setEMail(rs.getString("email"));
                customer.setMembershipStatus(rs.getString("membershipstatus"));
                listWithStatus.add(customer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listWithStatus;
    }
}

