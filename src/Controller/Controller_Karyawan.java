/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_Karyawan;
import DAO.Model_DAO;
import Model.Karyawan;
import View.MKaryawan;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Controller_Karyawan {
    MKaryawan form;
    Model_DAO<Karyawan> model;
    List<Karyawan> list;
    String[] header;
    
    //KONSTRUKTOR
    public Controller_Karyawan(MKaryawan form) {
        this.form   = form;
        model       = new DAO_Karyawan();
        list        = model.getAll();
        header      = new String[]{"Kode Karyawan", "Nama Karyawan", "Departemen"};
        
        form.getTblKar().setShowGrid(true);
        form.getTblKar().setShowVerticalLines(true);
        form.getTblKar().setGridColor(Color.gray);
    }
    
    //METHOD UNTUK MENAMPILKAN KODE
    public void tampilurutankode() {
        Karyawan k = new Karyawan();
        model.autonumber(k);
        form.getTxtKdKar().setText(String.valueOf(model.autonumber(k)));
    }
    
    //METHOD UNTUK MEMBERSIHKAN OBJEK INPUT
    public void reset() {
        form.getTxtKdKar().setText("");
        form.getTxtNmKar().setText("");
        form.getTxtDept().setText("");
        form.getTxtcari().setText("");
        tampilurutankode();
        isiTable();
    }
    
    //METHOD UNTUK MENAMPILKAN DATA KE JTABEL
    public void isiTable() {
        list = model.getAll();
        
        //SCRIPT AGAR JTABLE TIDAK DAPAT DIEDIT
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        
        Object[] data = new Object[header.length];
        for(Karyawan k : list) {
            data[0] = k.getKode();
            data[1] = k.getNama();
            data[2] = k.getDept();
            tblModel.addRow(data);
        }
        form.getTblKar().setModel(tblModel);
    }
    
    //METHOD UNTUK MELETAKKAN DATA KEDALAM TEXTBOX BERDASARKAN DATA YANG DIPILIH
    public void isiField(int row) {
        form.getTxtKdKar().setText(String.valueOf(list.get(row).getKode()));
        form.getTxtNmKar().setText(list.get(row).getNama());
        form.getTxtDept().setText(list.get(row).getDept());
    }
    
    //METHOD UNTUK MENCARI DATA BERDASARKAN INPUTAN
    public void isiTableCari() {
        list = model.getCari(form.getTxtcari().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header);
        Object[] data = new Object[header.length];
        for(Karyawan k : list) {
            data[0] = k.getKode();
            data[1] = k.getNama();
            data[2] = k.getDept();
            tblModel.addRow(data);
        }
        form.getTblKar().setModel(tblModel);
    }
    
    //METHOD UNTUK SIMPAN DATA
    public void insert() {
        Karyawan k = new Karyawan();
        k.setKode(Integer.parseInt(form.getTxtKdKar().getText()));
        k.setNama(form.getTxtNmKar().getText());
        k.setDept(form.getTxtDept().getText());
        model.insert(k);
    }
    
    //METHOD UNTUK MENGUBAH DATA
    public void update() {
        Karyawan k = new Karyawan();
        k.setKode(Integer.parseInt(form.getTxtKdKar().getText()));
        k.setNama(form.getTxtNmKar().getText());
        k.setDept(form.getTxtDept().getText());
        model.update(k);
    }
    
    //METHOD UNTUK MENGHAPUS DATA
    public void delete() {
        if(!form.getTxtKdKar().getText().trim().isEmpty()) {
            int id  = Integer.parseInt(form.getTxtKdKar().getText());
            model.delete(id);
        }
        else {
            JOptionPane.showMessageDialog(form, "Pilih Data Yang Akan Dihapus Terlebih Dahulu");
        }
    }
}
