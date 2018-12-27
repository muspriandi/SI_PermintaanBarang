/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_Barang;
import DAO.Model_DAO;
import Model.Barang;
import View.MBarang;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Controller_Barang {
    MBarang form;
    Model_DAO<Barang> model;
    List<Barang> list;
    String[] header;
    
    //KONSTRUKTOR
    public Controller_Barang(MBarang form) {
        this.form   = form;
        model       = new DAO_Barang();
        list        = model.getAll();
        header      = new String[]{"Kode Barang", "Nama Barang", "Satuan", "Harga"};
        
        form.getTblBrg().setShowGrid(true);
        form.getTblBrg().setShowVerticalLines(true);
        form.getTblBrg().setGridColor(Color.gray);
    }
    
    //METHOD UNTUK MENAMPILKAN KODE
    public void tampilurutankode() {
        Barang b = new Barang();
        model.autonumber(b);
        form.getTxtKdBrg().setText(String.valueOf(model.autonumber(b)));
    }
    
    //METHOD UNTUK MEMBERSIHKAN OBJEK INPUT
    public void reset() {
        form.getTxtKdBrg().setText("");
        form.getTxtNmBrg().setText("");
        form.getTxtSatuan().setText("");
        form.getTxtHarga().setText("");
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
        for(Barang b : list) {
            data[0] = b.getKode();
            data[1] = b.getNama();
            data[2] = b.getSatuan();
            data[3] = b.getHarga();
            tblModel.addRow(data);
        }
        form.getTblBrg().setModel(tblModel);
    }
    
    //METHOD UNTUK MELETAKKAN DATA KEDALAM TEXTBOX BERDASARKAN DATA YANG DIPILIH
    public void isiField(int row) {
        form.getTxtKdBrg().setText(String.valueOf(list.get(row).getKode()));
        form.getTxtNmBrg().setText(list.get(row).getNama());
        form.getTxtSatuan().setText(list.get(row).getSatuan());
        form.getTxtHarga().setText(String.valueOf(list.get(row).getHarga()));
    }
    
    //METHOD UNTUK MENCARI DATA BERDASARKAN INPUTAN
    public void isiTableCari() {
        list = model.getCari(form.getTxtcari().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header);
        Object[] data = new Object[header.length];
        for(Barang b : list) {
            data[0] = b.getKode();
            data[1] = b.getNama();
            data[2] = b.getSatuan();
            data[3] = b.getHarga();
            tblModel.addRow(data);
        }
        form.getTblBrg().setModel(tblModel);
    }
    
    //METHOD UNTUK SIMPAN DATA
    public void insert() {
        Barang b = new Barang();
        b.setKode(Integer.parseInt(form.getTxtKdBrg().getText()));
        b.setNama(form.getTxtNmBrg().getText());
        b.setSatuan(form.getTxtSatuan().getText());
        b.setHarga(Integer.parseInt(form.getTxtHarga().getText()));
        model.insert(b);
    }
    
    //METHOD UNTUK MENGUBAH DATA
    public void update() {
        Barang b = new Barang();
        b.setKode(Integer.parseInt(form.getTxtKdBrg().getText()));
        b.setNama(form.getTxtNmBrg().getText());
        b.setSatuan(form.getTxtSatuan().getText());
        b.setHarga(Integer.parseInt(form.getTxtHarga().getText()));
        model.update(b);
    }
    
    //METHOD UNTUK MENGHAPUS DATA
    public void delete() {
        if(!form.getTxtKdBrg().getText().trim().isEmpty()) {
            int id  = Integer.parseInt(form.getTxtKdBrg().getText());
            model.delete(id);
        }
        else {
            JOptionPane.showMessageDialog(form, "Pilih Data Yang Akan Dihapus Terlebih Dahulu");
        }
    }
}
