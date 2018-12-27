/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class DAO_Supplier implements Model_DAO<Supplier>{

    Connection connection;
    //KONSTRUKTOR
    public DAO_Supplier() {
        connection = Database.KoneksiDB();
    }
    
    String INSERT   = "INSERT INTO supplier(KdSpl,NmSpl,Alamat,Telp,Email) VALUES (?,?,?,?,?)";
    String UPDATE   = "UPDATE supplier SET NmSpl=?, Alamat=?, Telp=?, Email=? WHERE KdSpl=?";
    String DELETE   = "DELETE FROM supplier WHERE KdSpl=?";
    String SELECT   = "SELECT * FROM supplier";
    String CARI     = "SELECT (KdSpl) FROM supplier WHERE KdSpl=?";
    String SEARCH   = "SELECT * FROM supplier WHERE KdSpl LIKE ? OR NmSpl LIKE ? OR Alamat LIKE ? OR Telp LIKE ? OR Email LIKE ?";
    String COUNTER  = "SELECT MAX(KdSpl) AS kode FROM supplier";
            
    @Override
    public int autonumber(Supplier object) {
        PreparedStatement statement = null;
        int nomor = 0;
        try {
            statement       = connection.prepareStatement(COUNTER);
            ResultSet rs    = statement.executeQuery();
            if(rs.next()) {
                nomor   = rs.getInt("kode")+1;
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        return nomor;
    }

    @Override
    public void insert(Supplier object) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CARI);
            statement.setInt(1, object.getKode());
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                //JKA DATA SUDAH ADA DI DATABASE
                JOptionPane.showMessageDialog(null, "Data Sudah Pernah Disimpan!");
            }
            else {
                PreparedStatement statement2 = null;
                statement2 = connection.prepareStatement(INSERT);
                statement2.setInt(1, object.getKode());
                statement2.setString(2,object.getNama());
                statement2.setString(3,object.getAlamat());
                statement2.setString(4,object.getTelp());
                statement2.setString(5,object.getEmail());
                statement2.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Berhasil Menyimpan Data!");
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        finally {
            try {
                statement.close();
            }
            catch (SQLException x) {
                Logger.getLogger(DAO_Supplier.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }

    @Override
    public void update(Supplier object) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CARI);
            statement.setInt(1, object.getKode());
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
                statement = connection.prepareStatement(UPDATE);
                statement.setString(1,object.getNama());
                statement.setString(2,object.getAlamat());
                statement.setString(3,object.getTelp());
                statement.setString(4,object.getEmail());
                statement.setInt(5, object.getKode());
                statement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Kode Belum Pernah Disimpan!");
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        finally {
            try {
                statement.close();
            }
            catch (SQLException x) {
                Logger.getLogger(DAO_Supplier.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }
    
    @Override
    public void delete(Supplier object) {
        //METHOD DELETE HASIL OVERRIDE TIDAK DIGUNAKAN
    }
    
    @Override
    public void delete(String id) {
        //METHOD TIPE STRING INI TIDAK DIGUNAKAN
    }
    
    @Override
    public void delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
        }
        catch(Exception z) {
            z.printStackTrace();
        }
        finally {
            try {
                statement.close();
            }
            catch (SQLException x) {
                Logger.getLogger(DAO_Supplier.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }
    
    @Override
    public List<Supplier> getAll() {
        List<Supplier> list = null;
        PreparedStatement statement =null;
        try {
            list            = new ArrayList<Supplier>();
            statement       = connection.prepareStatement(SELECT);
            ResultSet rs    = statement.executeQuery();
            while(rs.next()) {
                Supplier s   = new Supplier();
                s.setKode(rs.getInt("KdSpl"));
                s.setNama(rs.getString("NmSpl"));
                s.setAlamat(rs.getString("Alamat"));
                s.setTelp(rs.getString("Telp"));
                s.setEmail(rs.getString("Email"));
                list.add(s);
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Supplier> getCari(String key) {
        List<Supplier> list = null;
        PreparedStatement statement =null;
        try {
            list            = new ArrayList<Supplier>();
            statement       = connection.prepareStatement(SEARCH);
            statement.setString(1, "%"+key+"%");
            statement.setString(2, "%"+key+"%");
            statement.setString(3, "%"+key+"%");
            statement.setString(4, "%"+key+"%");
            statement.setString(5, "%"+key+"%");
            ResultSet rs    = statement.executeQuery();
            while(rs.next()) {
                Supplier s   = new Supplier();
                s.setKode(rs.getInt("KdSpl"));
                s.setNama(rs.getString("NmSpl"));
                s.setAlamat(rs.getString("Alamat"));
                s.setTelp(rs.getString("Telp"));
                s.setEmail(rs.getString("Email"));
                list.add(s);
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        return list;
    }  
}
