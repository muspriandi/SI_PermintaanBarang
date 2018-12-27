/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.Barang;
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
public class DAO_Barang implements Model_DAO<Barang>{

    Connection connection;
    //KONSTRUKTOR
    public DAO_Barang() {
        connection = Database.KoneksiDB();
    }
    
    String INSERT   = "INSERT INTO barang(KdBrg,NmBrg,Satuan,Harga) VALUES (?,?,?,?)";
    String UPDATE   = "UPDATE barang SET NmBrg=?, Satuan=?, Harga=? WHERE KDBrg=?";
    String DELETE   = "DELETE FROM barang WHERE KdBrg=?";
    String SELECT   = "SELECT * FROM barang";
    String CARI     = "SELECT (KdBrg) FROM barang WHERE KdBrg=?";
    String SEARCH   = "SELECT * FROM barang WHERE KdBrg LIKE ? OR NmBrg LIKE ? OR Satuan LIKE ? OR Harga LIKE ?";
    String COUNTER  = "SELECT MAX(KdBrg) AS kode FROM barang";
            
    @Override
    public int autonumber(Barang object) {
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
    public void insert(Barang object) {
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
                statement2.setString(3,object.getSatuan());
                statement2.setInt(4,object.getHarga());
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
                Logger.getLogger(DAO_Barang.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }

    @Override
    public void update(Barang object) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CARI);
            statement.setInt(1, object.getKode());
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
                statement = connection.prepareStatement(UPDATE);
                statement.setString(1,object.getNama());
                statement.setString(2,object.getSatuan());
                statement.setInt(3,object.getHarga());
                statement.setInt(4, object.getKode());
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
                Logger.getLogger(DAO_Barang.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }
    
    @Override
    public void delete(Barang object) {
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
                Logger.getLogger(DAO_Barang.class.getName()).log(Level.SEVERE,null,x);
            }
        }
    }
    
    @Override
    public List<Barang> getAll() {
        List<Barang> list = null;
        PreparedStatement statement =null;
        try {
            list            = new ArrayList<Barang>();
            statement       = connection.prepareStatement(SELECT);
            ResultSet rs    = statement.executeQuery();
            while(rs.next()) {
                Barang b    = new Barang();
                b.setKode(rs.getInt("KdBrg"));
                b.setNama(rs.getString("NmBrg"));
                b.setSatuan(rs.getString("Satuan"));
                b.setHarga(rs.getInt("Harga"));
                list.add(b);
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Barang> getCari(String key) {
        List<Barang> list = null;
        PreparedStatement statement =null;
        try {
            list            = new ArrayList<Barang>();
            statement       = connection.prepareStatement(SEARCH);
            statement.setString(1, "%"+key+"%");
            statement.setString(2, "%"+key+"%");
            statement.setString(3, "%"+key+"%");
            statement.setString(4, "%"+key+"%");
            ResultSet rs    = statement.executeQuery();
            while(rs.next()) {
                Barang b    = new Barang();
                b.setKode(rs.getInt("KdBrg"));
                b.setNama(rs.getString("NmBrg"));
                b.setSatuan(rs.getString("Satuan"));
                b.setHarga(rs.getInt("Harga"));
                list.add(b);
            }
        }
        catch (Exception z) {
            z.printStackTrace();
        }
        return list;
    }  
}
