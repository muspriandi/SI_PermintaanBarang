/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_FPB;
import Model.FPB;
import View.TFPB;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class Controller_FPB {
    TFPB form;
    DAO_FPB model;
    
    String[] header = new String[] {"Kode Barang", "Nama Barang", "Satuan", "Harga", "Jumlah Minta"};
    DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{}, header)
    {
        @Override
        //METHOD SUPAYA JTable tidak dapat diedit
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }
    };
    
    //KONTRUKTOR
    public Controller_FPB(TFPB form)
    {
        this.form = form;
        model = new DAO_FPB();
        form.getTblMinta().setModel(tblModel);
        form.getTblMinta().setShowGrid(true);
        form.getTblMinta().setShowVerticalLines(true);
        form.getTblMinta().setGridColor(Color.gray);
    }
    
    //METHOD UNTUK MENAMPILKAN KODE
    public void tampilurutankode() {
        form.getTxtNoFPB().setText(model.autonumberstring());
    }
    
    //METHOD UNTUK MEMBERSIHKAN OBJEK INPUT
    public void reset() {
        form.getTxtNoFPB().setText("");
        SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd");
        form.getTxtTglFPB().setText(String.valueOf(tgl.format(new Date())));
        form.getCmbKar().setSelectedIndex(0);
        form.getTxtNmKar().setText("");
        form.getTxtDept().setText("");
        form.getCmbBrg().setSelectedIndex(0);
        form.getTxtNmBrg().setText("");
        form.getTxtSatuan().setText("");
        form.getTxtHarga().setText("");
        form.getTxtJmlMinta().setText("");
        
        tampilurutankode();
        isicombokaryawan();
        isicombobarang();
        
        tblModel.setRowCount(0);
        form.getTxtNoFPB().requestFocus();
        form.getTxtNoFPB().setEditable(false);
        form.getTxtTglFPB().setEditable(false);
        form.getTxtNmKar().setEditable(false);
        form.getTxtDept().setEditable(false);
        form.getTxtNmBrg().setEditable(false);
        form.getTxtSatuan().setEditable(false);
        form.getTxtHarga().setEditable(false);
    }
    
    //MENGHAPUS DATA BARANG
    public void reset2() {
        form.getCmbBrg().setSelectedIndex(0);
        form.getTxtNmBrg().setText("");
        form.getTxtSatuan().setText("");
        form.getTxtHarga().setText("");
        form.getTxtJmlMinta().setText("");
    }
    
    //METHOD UNTUK MENAMPILKAN DATA KE JTABEL
    public void isiTable() {
        int jmlbaris = tblModel.getRowCount();
        int i, datasama = 0;
        
        if(form.getCmbBrg().getSelectedIndex() > 0)
        {
            if(jmlbaris == 0)
            {
                //JIKA TABEL MASIH KOSONG
                tblModel.addRow(new Object[]
                {
                    form.getCmbBrg().getSelectedItem().toString(),
                    form.getTxtNmBrg().getText(),
                    form.getTxtSatuan().getText(),
                    (Integer.parseInt(form.getTxtHarga().getText())),
                    (Integer.parseInt(form.getTxtJmlMinta().getText()))
                });
            }
            else
            {
                for(i=0;i<jmlbaris;i++)
                {
                    if(form.getCmbBrg().getSelectedItem().toString().equals(tblModel.getValueAt(i, 0).toString()))
                    {
                        datasama = 1;
                        JOptionPane.showMessageDialog(null, "Kode Barang " + tblModel.getValueAt(i, 0).toString() + " sudah pernah ditambahkan, dan akan diupdate dengan data terbaru");
                        
                        tblModel.setValueAt(form.getCmbBrg().getSelectedItem().toString(),i,0);
                        tblModel.setValueAt(form.getTxtNmBrg().getText(), i, 1);
                        tblModel.setValueAt(form.getTxtSatuan().getText(), i, 2);
                        tblModel.setValueAt(Integer.parseInt(form.getTxtHarga().getText()), i, 3);
                        tblModel.setValueAt(Integer.parseInt(form.getTxtJmlMinta().getText()), i, 4);
                        break;
                    }
                    else
                    {
                        datasama = 0;
                    }
                }
                
                if(datasama == 0)
                {
                    tblModel.addRow(new Object[]
                    {
                        form.getCmbBrg().getSelectedItem().toString(),
                        form.getTxtNmBrg().getText(),
                        form.getTxtSatuan().getText(),
                        (Integer.parseInt(form.getTxtHarga().getText())),
                        (Integer.parseInt(form.getTxtJmlMinta().getText()))
                    });
                }
            }
        }
    }
    
    //METHOD UNTUK MELETAKKAN DATA KEDALAM TEXTBOX BERDASARKAN DATA YANG DIPILIH
    public void isiField(int row) {
        //BELUM
    }
    
    //MENGHAPUS DATA DARI JTABEL
    public void resetrow() {
        int [] selectedRows = form.getTblMinta().getSelectedRows();
        if(selectedRows.length > 0)
        {
            for (int i = selectedRows.length - 1;i>=0;i--)
            {
                tblModel.removeRow(selectedRows[i]);
            }
        }
    }
    
    //METHOD UNTUK MENGISI DATA KEDALAM COMBO BOX 
    public void isicombokaryawan() {
        form.getCmbKar().removeAllItems();
        form.getCmbKar().addItem("Pilih");
        for (FPB f : model.isicombokaryawan())
        {
            form.getCmbKar().addItem(String.valueOf(f.getKdKar()));
        }
    }
    
    //METHOD MENAMPILKAN DATA KARYAWAN BEDASARKAN COMBO BOX
    public void tampilAllKar() {
        if(form.getCmbKar().getSelectedIndex() != 0)
        {
            for (FPB f : model.getAllKar(Integer.parseInt(form.getCmbKar().getSelectedItem().toString())))
            {
                form.getTxtNmKar().setText(f.getNmKar());
                form.getTxtDept().setText(f.getDept());
            }
        }
    }
    
    //METHOD UNTUK MENGISI DATA KEDALAM COMBO BOX 
    public void isicombobarang() {
        form.getCmbBrg().removeAllItems();
        form.getCmbBrg().addItem("Pilih");
        for (FPB f : model.isicombobarang())
        {
            form.getCmbBrg().addItem(String.valueOf(f.getKdBrg()));
        }
    }
    
    //METHOD MENAMPILKAN DATA BARANG BEDASARKAN COMBO BOX
    public void tampilAllBrg() {
        if(form.getCmbBrg().getSelectedIndex() != 0)
        {
            for (FPB f : model.getAllBrg(Integer.parseInt(form.getCmbBrg().getSelectedItem().toString())))
            {
                form.getTxtNmBrg().setText(f.getNmBrg());
                form.getTxtSatuan().setText(f.getSatuan());
                form.getTxtHarga().setText(String.valueOf(f.getHarga()));
            }
        }
    }
    
    //METHOD UNTUK SIMPAN DATA KE TABEL FPB
    public void insert() {
        FPB f = new FPB();
        f.setNoFPB(form.getTxtNoFPB().getText());
        f.setTglFPB(form.getTxtTglFPB().getText());
        f.setKdKar(Integer.parseInt(form.getCmbKar().getSelectedItem().toString()));
        model.insert(f);
    }
    
    //METHOD UNTUK SIMPAN DATA KE TABEL MINTA
    public void insert_minta() {
        int jmlbaris = tblModel.getRowCount();
        int i, simpan = 0;
        
        for(i=0;i<jmlbaris;i++)
        {
            FPB f = new FPB();
            f.setNoFPB(form.getTxtNoFPB().getText());
            f.setKdBrg(Integer.parseInt(tblModel.getValueAt(i, 0).toString()));
            f.setHarga(Integer.parseInt(tblModel.getValueAt(i, 3).toString()));
            f.setJumlahMinta(Integer.parseInt(tblModel.getValueAt(i, 4).toString()));
            model.insert_minta(f);
        }
    }
    
    //METHOD UNTUK CETAK
    public void cetak() {
        try {
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/CetakPermintaan.jasper";
            HashMap parameter = new HashMap();
            parameter.put("NoFPB", (form.getTxtNoFPB().getText()));
            
            JasperReport jp     = (JasperReport)JRLoader.loadObject(path);
            JasperPrint print   = JasperFillManager.fillReport(jp, parameter,conn);
            JasperViewer.viewReport(print,false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
            //CETAK KE EXCEL
            File xlsx = new File("D:/Permintaan"+(form.getTxtNoFPB().getText()+".xlsx"));
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE, xlsx);
            xlsxExporter.exportReport();
        }
        catch(Exception z) {
            JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak! "+z.getMessage(), "Cetak Data",JOptionPane.ERROR_MESSAGE);
        }
    }
}
