/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_Supplier;
import DAO.Model_DAO;
import Model.Supplier;
import View.MSupplier;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Controller_Supplier {
    MSupplier form;
    Model_DAO<Supplier> model;
    List<Supplier> list;
    String[] header;
    
    //KONSTRUKTOR
    public Controller_Supplier(MSupplier form) {
        this.form   = form;
        model       = new DAO_Supplier();
        list        = model.getAll();
        header      = new String[]{"Kode Supplier", "Nama Supplier", "Alamat", "Telepon", "Email"};
        
        form.getTblSpl().setShowGrid(true);
        form.getTblSpl().setShowVerticalLines(true);
        form.getTblSpl().setGridColor(Color.gray);
    }
    
    //METHOD UNTUK MENAMPILKAN KODE
    public void tampilurutankode() {
        Supplier s = new Supplier();
        model.autonumber(s);
        form.getTxtKdSpl().setText(String.valueOf(model.autonumber(s)));
    }
    
    //METHOD UNTUK MEMBERSIHKAN OBJEK INPUT
    public void reset() {
        form.getTxtKdSpl().setText("");
        form.getTxtNmSpl().setText("");
        form.getTxtAlamat().setText("");
        form.getTxtTelp().setText("");
        form.getTxtEmail().setText("");
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
        for(Supplier s : list) {
            data[0] = s.getKode();
            data[1] = s.getNama();
            data[2] = s.getAlamat();
            data[3] = s.getTelp();
            data[4] = s.getEmail();
            tblModel.addRow(data);
        }
        form.getTblSpl().setModel(tblModel);
    }
    
    //METHOD UNTUK MELETAKKAN DATA KEDALAM TEXTBOX BERDASARKAN DATA YANG DIPILIH
    public void isiField(int row) {
        form.getTxtKdSpl().setText(String.valueOf(list.get(row).getKode()));
        form.getTxtNmSpl().setText(list.get(row).getNama());
        form.getTxtAlamat().setText(list.get(row).getAlamat());
        form.getTxtTelp().setText(list.get(row).getTelp());
        form.getTxtEmail().setText(list.get(row).getEmail());
    }
    
    //METHOD UNTUK MENCARI DATA BERDASARKAN INPUTAN
    public void isiTableCari() {
        list = model.getCari(form.getTxtcari().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header);
        Object[] data = new Object[header.length];
        for(Supplier s : list) {
            data[0] = s.getKode();
            data[1] = s.getNama();
            data[2] = s.getAlamat();
            data[3] = s.getTelp();
            data[4] = s.getEmail();
            tblModel.addRow(data);
        }
        form.getTblSpl().setModel(tblModel);
    }
    
    //METHOD UNTUK SIMPAN DATA
    public void insert() {
        Supplier s = new Supplier();
        s.setKode(Integer.parseInt(form.getTxtKdSpl().getText()));
        s.setNama(form.getTxtNmSpl().getText());
        s.setAlamat(form.getTxtAlamat().getText());
        s.setTelp(form.getTxtTelp().getText());
        s.setEmail(form.getTxtEmail().getText());
        model.insert(s);
    }
    
    //METHOD UNTUK MENGUBAH DATA
    public void update() {
        Supplier s = new Supplier();
        s.setKode(Integer.parseInt(form.getTxtKdSpl().getText()));
        s.setNama(form.getTxtNmSpl().getText());
        s.setAlamat(form.getTxtAlamat().getText());
        s.setTelp(form.getTxtTelp().getText());
        s.setEmail(form.getTxtEmail().getText());
        model.update(s);
    }
    
    //METHOD UNTUK MENGHAPUS DATA
    public void delete() {
        if(!form.getTxtKdSpl().getText().trim().isEmpty()) {
            int id  = Integer.parseInt(form.getTxtKdSpl().getText());
            model.delete(id);
        }
        else {
            JOptionPane.showMessageDialog(form, "Pilih Data Yang Akan Dihapus Terlebih Dahulu");
        }
    }
}
