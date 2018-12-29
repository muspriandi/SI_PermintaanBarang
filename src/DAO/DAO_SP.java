/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.SP;
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
public class DAO_SP implements Model_DAO<SP>{
    
    Connection connection;
    //KONSTRUKTOR
    public DAO_SP() {
        connection = Database.KoneksiDB();
    }

    @Override
    public int autonumber(SP object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //METHOD AUTONUMBERSTRING UNTUK MENGEMBALIKAN NILAI STRING
    public String autonumberstring() {
        PreparedStatement statement;
        int nomor_berikutnya = 0;
        String urutan = "";
        
        try
        {
            //String COUNTER = "SELECT IFNULL(MAX(CONVERT(RIGHT(NoSP,4),SIGNED INTEGER)),0) AS kode,"
            //        + "IFNULL(LENGHT(MAX(CONVERT(RIGHT(NoSP,5)+1,SIGNED INTEGER))),0) AS panjang FROM fpb";
            
            String COUNTER = "SELECT IFNULL(MAX(CONVERT(RIGHT(NoSP,4),SIGNED INTEGER)),0) AS kode,"
                    + "IFNULL(MAX(CONVERT(RIGHT(NoSP,5)+1,SIGNED INTEGER)),0) AS panjang FROM suratpesan";
            
            statement = connection.prepareStatement(COUNTER);
            ResultSet rs2 = statement.executeQuery();
            if(rs2.next())
            {
                nomor_berikutnya = rs2.getInt("kode")+1;
                if(rs2.getInt("kode") !=0)
                {
                    if(rs2.getInt("panjang") == 1)
                    {
                        urutan = "SP"+"000"+nomor_berikutnya;
                    }
                    else
                    {
                        if(rs2.getInt("panjang") == 2)
                        {
                            urutan = "SP"+"00"+nomor_berikutnya;
                        }
                        else
                        {
                            if(rs2.getInt("panjang") == 3)
                            {
                                urutan = "SP"+"0"+nomor_berikutnya;
                            }
                            else
                            {
                                if(rs2.getInt("panjang") == 4)
                                {
                                    urutan = "SP"+nomor_berikutnya;
                                }
                            }
                        }
                    }
                }
                else
                {
                    urutan = "SP"+"0001";
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
    public void insert(SP object) {
        PreparedStatement statement;
        try 
        {
            String SELECT = "SELECT * FROM suratpesan WHERE NoSP=?";
            statement = connection.prepareStatement(SELECT);
            statement.setString(1, object.getNoSp());
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                //JKA DATA SUDAH ADA DI DATABASE
                JOptionPane.showMessageDialog(null, "Data Sudah Pernah Disimpan!");
            }
            else
            {
                //INSERT KE DALAM TABEL SP
                PreparedStatement statement2;
                String INSERT = "INSERT INTO suratpesan(NoSP,TglSP,NoFPB,KdSpl) VALUES (?,?,?,?)";
                statement2 = connection.prepareStatement(INSERT);
                statement2.setString(1, object.getNoSp());
                statement2.setString(2, object.getTglSP());
                statement2.setString(3, object.getNoFPB());
                statement2.setInt(4, object.getKdSpl());
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
    public void insert_pesan(SP object) {
        PreparedStatement statement;
        try 
        {
            //INSERT KE DALAM TABEL MINTA
            String INSERTMINTA = "INSERT INTO pesan(NoSP,KdBrg,HargaPesan,JmlPesan) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(INSERTMINTA);
            statement.setString(1, object.getNoSp());
            statement.setInt(2, object.getKdBrg());
            statement.setInt(3, object.getHarga());
            statement.setInt(4, object.getJumlahPesan());
            statement.executeUpdate();
        }
        catch (Exception z) {
            z.printStackTrace();
        }
    }
    
    @Override
    public void update(SP object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SP object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SP> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SP> getCari(String key) {
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
    public List<SP> isicombofpb()
    {
        PreparedStatement statement;
        List<SP> list = null;
        try
        {
            list = new ArrayList();
            String TAMPILFPB = "SELECT NoFPB FROM fpb ORDER BY NoFPB";
            statement = connection.prepareStatement(TAMPILFPB);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                SP f = new SP();
                f.setNoFPB(rs.getString("NoFPB"));
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
    public List<SP> getAllFpb(String kode)
    {
        PreparedStatement statement;
        List<SP> list = null;
        try
        {
            list = new ArrayList();
            String CARIFPB = "SELECT * FROM fpb WHERE NoFPB=?";
            statement = connection.prepareStatement(CARIFPB);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
               SP f = new SP();
               f.setTglFPB(rs.getString("TglFPB"));
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
    public List<SP> isicombobarang(String kode)
    {
        PreparedStatement statement;
        List<SP> list = null;
        try
        {
            list = new ArrayList();
            String TAMPILBARANG = "SELECT KdBrg FROM minta WHERE NoFPB=? ORDER BY KdBrg";
            statement = connection.prepareStatement(TAMPILBARANG);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                SP f = new SP();
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
    public List<SP> getAllBrg(Integer kode)
    {
        PreparedStatement statement;
        List<SP> list = null;
        try
        {
            list = new ArrayList();
            String CARIBARANG = "SELECT A.*,B.* FROM BARANG A,MINTA B WHERE A.KdBrg = B.KdBrg AND A.KdBrg=?";
            statement = connection.prepareStatement(CARIBARANG);
            statement.setInt(1, kode);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
               SP f = new SP();
               f.setNmBrg(rs.getString("NmBrg"));
               f.setHarga(rs.getInt("Harga"));
               f.setJumlahMinta(rs.getInt("JmlMinta"));
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
    
    //METHOD UNTUK MENAMBILKAN KODE KARYAWAN KE DALAM COMBO BOX
    public List<SP> isicombospl()
    {
        PreparedStatement statement;
        List<SP> list = null;
        try
        {
            list = new ArrayList();
            String TAMPILSPL = "SELECT KdSpl FROM supplier ORDER BY KdSpl";
            statement = connection.prepareStatement(TAMPILSPL);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                SP f = new SP();
                f.setKdSpl(rs.getInt("KdSpl"));
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
    public List<SP> getAllsSpl(String kode)
    {
        PreparedStatement statement;
        List<SP> list = null;
        try
        {
            list = new ArrayList();
            String CARIFPB = "SELECT * FROM supplier WHERE KdSpl=?";
            statement = connection.prepareStatement(CARIFPB);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
               SP f = new SP();
               f.setNmSpl(rs.getString("NmSpl"));
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
