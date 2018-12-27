/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.Karyawan;
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
public class DAO_Karyawan implements Model_DAO<Karyawan>{

    Connection connection;
    //KONSTRUKTOR
    public DAO_Karyawan() {
        connection = Database.KoneksiDB();
    }
    
    String INSERT   = "INSERT INTO karyawan(KdKar,NmKar,Dept) VALUES (?,?,?)";
    String UPDATE   = "UPDATE karyawan SET NmKar=?, Dept=? WHERE KdKar=?";
    String DELETE   = "DELETE FROM karyawan WHERE KdKar=?";
    String SELECT   = "SELECT * FROM karyawan";
    String CARI     = "SELECT (KdKar) FROM karyawan WHERE KdKar=?";
    String SEARCH   = "SELECT * FROM karyawan WHERE KdKar LIKE ? OR NmKar LIKE ? OR Dept LIKE ?";
    String COUNTER  = "SELECT MAX(KdKar) AS kode FROM karyawan";
            
    @Override
    public int autonumber(Karyawan object) {
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
    public void insert(Karyawan object) {
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
                statement2.setString(3,object.getDept());
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
                Logger.getLogger(DAO_Karyawan.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }

    @Override
    public void update(Karyawan object) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CARI);
            statement.setInt(1, object.getKode());
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
                statement = connection.prepareStatement(UPDATE);
                statement.setString(1,object.getNama());
                statement.setString(2,object.getDept());
                statement.setInt(3, object.getKode());
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
                Logger.getLogger(DAO_Karyawan.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }
    
    @Override
    public void delete(Karyawan object) {
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
                Logger.getLogger(DAO_Karyawan.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }
    
    @Override
    public List<Karyawan> getAll() {
        List<Karyawan> list = null;
        PreparedStatement statement =null;
        try {
            list            = new ArrayList<Karyawan>();
            statement       = connection.prepareStatement(SELECT);
            ResultSet rs    = statement.executeQuery();
            while(rs.next()) {
                Karyawan k    = new Karyawan();
                k.setKode(rs.getInt("KdKar"));
                k.setNama(rs.getString("NmKar"));
                k.setDept(rs.getString("Dept"));
                list.add(k);
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Karyawan> getCari(String key) {
        List<Karyawan> list = null;
        PreparedStatement statement =null;
        try {
            list            = new ArrayList<Karyawan>();
            statement       = connection.prepareStatement(SEARCH);
            statement.setString(1, "%"+key+"%");
            statement.setString(2, "%"+key+"%");
            statement.setString(3, "%"+key+"%");
            ResultSet rs    = statement.executeQuery();
            while(rs.next()) {
                Karyawan k    = new Karyawan();
                k.setKode(rs.getInt("KdKar"));
                k.setNama(rs.getString("NmKar"));
                k.setDept(rs.getString("Dept"));
                list.add(k);
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        return list;
    }  
}
