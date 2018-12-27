/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.FPB;
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
public class DAO_FPB implements Model_DAO<FPB>{
    
    Connection connection;
    //KONSTRUKTOR
    public DAO_FPB() {
        connection = Database.KoneksiDB();
    }

    @Override
    public int autonumber(FPB object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //METHOD AUTONUMBERSTRING UNTUK MENGEMBALIKAN NILAI STRING
    public String autonumberstring() {
        PreparedStatement statement;
        int nomor_berikutnya = 0;
        String urutan = "";
        
        try
        {
            //String COUNTER = "SELECT IFNULL(MAX(CONVERT(RIGHT(NoFPB,4),SIGNED INTEGER)),0) AS kode,"
            //        + "IFNULL(LENGHT(MAX(CONVERT(RIGHT(NoFPB,5)+1,SIGNED INTEGER))),0) AS panjang FROM fpb";
            
            String COUNTER = "SELECT IFNULL(MAX(CONVERT(RIGHT(NoFPB,4),SIGNED INTEGER)),0) AS kode,"
                    + "IFNULL(MAX(CONVERT(RIGHT(NoFPB,5)+1,SIGNED INTEGER)),0) AS panjang FROM fpb";
            
            statement = connection.prepareStatement(COUNTER);
            ResultSet rs2 = statement.executeQuery();
            if(rs2.next())
            {
                nomor_berikutnya = rs2.getInt("kode")+1;
                if(rs2.getInt("kode") !=0)
                {
                    if(rs2.getInt("panjang") == 1)
                    {
                        urutan = "FPB"+"000"+nomor_berikutnya;
                    }
                    else
                    {
                        if(rs2.getInt("panjang") == 2)
                        {
                            urutan = "FPB"+"00"+nomor_berikutnya;
                        }
                        else
                        {
                            if(rs2.getInt("panjang") == 3)
                            {
                                urutan = "FPB"+"0"+nomor_berikutnya;
                            }
                            else
                            {
                                if(rs2.getInt("panjang") == 4)
                                {
                                    urutan = "FPB"+nomor_berikutnya;
                                }
                            }
                        }
                    }
                }
                else
                {
                    urutan = "FPB"+"0001";
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
            }   
        }
        catch (Exception z)
        {
            z.printStackTrace();
        }
        return urutan;
    } 

    @Override
    public void insert(FPB object) {
        PreparedStatement statement;
        try 
        {
            String SELECT = "SELECT * FROM fpb WHERE NoFPB=?";
            statement = connection.prepareStatement(SELECT);
            statement.setString(1, object.getNoFPB());
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                //JKA DATA SUDAH ADA DI DATABASE
                JOptionPane.showMessageDialog(null, "Data Sudah Pernah Disimpan!");
            }
            else
            {
                //INSERT KE DALAM TABEL FPB
                PreparedStatement statement2;
                String INSERT = "INSERT INTO fpb(NoFPB,TglFPB,KdKar) VALUES (?,?,?)";
                statement2 = connection.prepareStatement(INSERT);
                statement2.setString(1, object.getNoFPB());
                statement2.setString(2, object.getTglFPB());
                statement2.setInt(3, object.getKdKar());
                statement2.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }
        }
        catch (Exception z)
        {
            z.printStackTrace();
        }
    }

    //METHOD UNTUK INSERT DATA KE TABEL MINTA
    public void insert_minta(FPB object) {
        PreparedStatement statement;
        try 
        {
            //INSERT KE DALAM TABEL MINTA
            String INSERTMINTA = "INSERT INTO minta(NoFPB,KdBrg,HargaFPB,JmlMinta) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(INSERTMINTA);
            statement.setString(1, object.getNoFPB());
            statement.setInt(2, object.getKdBrg());
            statement.setInt(3, object.getHarga());
            statement.setInt(4, object.getJumlahMinta());
            statement.executeUpdate();
        }
        catch (Exception z) {
            z.printStackTrace();
        }
    }
    
    @Override
    public void update(FPB object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(FPB object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FPB> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FPB> getCari(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //METHOD UNTUK MENAMBILKAN KODE KARYAWAN KE DALAM COMBO BOX
    public List<FPB> isicombokaryawan()
    {
        PreparedStatement statement;
        List<FPB> list = null;
        try
        {
            list = new ArrayList();
            String TAMPILKARYAWAN = "SELECT KdKar FROM karyawan ORDER BY KdKar";
            statement = connection.prepareStatement(TAMPILKARYAWAN);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                FPB f = new FPB();
                f.setKdKar(rs.getInt("KdKar"));
                list.add(f);
            }
        }
        catch (Exception z)
        {
            z.printStackTrace();
        }
        return list;
    }
    
    //METHOD UNTUK MENGAMBIL DATA DARI TABEL KARYAWAN
    public List<FPB> getAllKar(Integer kode)
    {
        PreparedStatement statement;
        List<FPB> list = null;
        try
        {
            list = new ArrayList();
            String CARIKARYAWAN = "SELECT * FROM karyawan WHERE KdKar=?";
            statement = connection.prepareStatement(CARIKARYAWAN);
            statement.setInt(1, kode);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
               FPB f = new FPB();
               f.setNmKar(rs.getString("NmKar"));
               f.setDept(rs.getString("Dept"));
               list.add(f);
            }
            else
            {
               JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
            }
        }
        catch (Exception z)
        {
            z.printStackTrace();
        }
        return list;
    }
    
    //METHOD UNTUK MENAMBILKAN KODE BARANG KE DALAM COMBO BOX
    public List<FPB> isicombobarang()
    {
        PreparedStatement statement;
        List<FPB> list = null;
        try
        {
            list = new ArrayList();
            String TAMPILBARANG = "SELECT KdBrg FROM barang ORDER BY KdBrg";
            statement = connection.prepareStatement(TAMPILBARANG);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                FPB f = new FPB();
                f.setKdBrg(rs.getInt("KdBrg"));
                list.add(f);
            }
        }
        catch (Exception z)
        {
            z.printStackTrace();
        }
        return list;
    }
    
    //METHOD UNTUK MENGAMBIL DATA DARI TABEL BARANG
    public List<FPB> getAllBrg(Integer kode)
    {
        PreparedStatement statement;
        List<FPB> list = null;
        try
        {
            list = new ArrayList();
            String CARIBARANG = "SELECT * FROM barang WHERE KdBrg=?";
            statement = connection.prepareStatement(CARIBARANG);
            statement.setInt(1, kode);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
               FPB f = new FPB();
               f.setNmBrg(rs.getString("NmBrg"));
               f.setSatuan(rs.getString("Satuan"));
               f.setHarga(rs.getInt("Harga"));
               list.add(f);
            }
            else
            {
               JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
            }
        }
        catch (Exception z)
        {
            z.printStackTrace();
        }
        return list;
    }
}
