/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_SP;
import Model.SP;
import View.TSP;
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
public class Controller_SP {
    TSP form;
    DAO_SP model;
    
    String[] header = new String[] {"No. FPB", "Kode Barang", "Nama Barang", "Harga SP", "Nama Supplier","Jumlah Pesan"};
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
    public Controller_SP(TSP form)
    {
        this.form = form;
        model = new DAO_SP();
        form.getTblPesan().setModel(tblModel);
        form.getTblPesan().setShowGrid(true);
        form.getTblPesan().setShowVerticalLines(true);
        form.getTblPesan().setGridColor(Color.gray);
    }
    
    //METHOD UNTUK MENAMPILKAN KODE
    public void tampilurutankode() {
        form.getTxtNoSP().setText(model.autonumberstring());
    }
    
    //METHOD UNTUK MEMBERSIHKAN OBJEK INPUT
    public void reset() {
        form.getTxtNoSP().setText("");
        SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd");
        form.getTxtTglSP().setText(String.valueOf(tgl.format(new Date())));
        form.getCmbFPB().setSelectedIndex(0);
        form.getTxtTglFPB().setText("");
        form.getCmbBrg().setSelectedIndex(0);
        form.getTxtNmBrg().setText("");
        form.getTxtHarga().setText("");
        form.getTxtJmlMinta().setText("");
        form.getTxtJmlPesan().setText("");
        form.getCmbSpl().setSelectedIndex(0);
        form.getTxtNmSpl().setText("");
        
        tampilurutankode();
        isicombofpb();
        isicombobarang();
        isicombospl();
        
        tblModel.setRowCount(0);
        form.getTxtNoSP().requestFocus();
        form.getTxtNoSP().setEditable(false);
        form.getTxtTglSP().setEditable(false);
        form.getTxtTglFPB().setEditable(false);
        form.getTxtNmBrg().setEditable(false);
        form.getTxtHarga().setEditable(false);
        form.getTxtNmSpl().setEditable(false);
        form.getTxtJmlMinta().setEditable(false);
    }
    
    //METHOD RESET DATA BARANG
    public void reset2() {
        form.getCmbBrg().setSelectedIndex(0);
        form.getTxtNmBrg().setText("");
        form.getTxtHarga().setText("");
        form.getTxtJmlMinta().setText("");
        form.getTxtJmlPesan().setText("");
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
                    form.getCmbFPB().getSelectedItem().toString(),
                    form.getCmbBrg().getSelectedItem().toString(),
                    form.getTxtNmBrg().getText(),
                    (Integer.parseInt(form.getTxtHarga().getText())),
                    form.getTxtNmSpl().getText(),
                    (Integer.parseInt(form.getTxtJmlPesan().getText()))
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
                        
                        tblModel.setValueAt(form.getCmbFPB().getSelectedItem().toString(),i,0);
                        tblModel.setValueAt(form.getCmbBrg().getSelectedItem().toString(),i,1);
                        tblModel.setValueAt(form.getTxtNmBrg().getText(), i, 2);
                        tblModel.setValueAt(Integer.parseInt(form.getTxtHarga().getText()), i, 3);
                        tblModel.setValueAt(form.getTxtNmSpl().getText(), i, 4);
                        tblModel.setValueAt(Integer.parseInt(form.getTxtJmlPesan().getText()), i, 5);
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
                        form.getCmbFPB().getSelectedItem().toString(),
                        form.getCmbBrg().getSelectedItem().toString(),
                        form.getTxtNmBrg().getText(),
                        (Integer.parseInt(form.getTxtHarga().getText())),
                        form.getTxtNmSpl().getText(),
                        (Integer.parseInt(form.getTxtJmlPesan().getText()))
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
        int [] selectedRows = form.getTblPesan().getSelectedRows();
        if(selectedRows.length > 0)
        {
            for (int i = selectedRows.length - 1;i>=0;i--)
            {
                tblModel.removeRow(selectedRows[i]);
            }
        }
    }
    
    //METHOD UNTUK MENGISI DATA KEDALAM COMBO BOX 
    public void isicombofpb() {
        form.getCmbFPB().removeAllItems();
        form.getCmbFPB().addItem("Pilih");
        for (SP f : model.isicombofpb())
        {
            form.getCmbFPB().addItem(String.valueOf(f.getNoFPB()));
        }
    }
    
    //METHOD MENAMPILKAN DATA KARYAWAN BEDASARKAN COMBO BOX
    public void getAllFpb() {
        if(form.getCmbFPB().getSelectedIndex() != 0)
        {
            for (SP f : model.getAllFpb(form.getCmbFPB().getSelectedItem().toString()))
            {
                form.getTxtTglFPB().setText(f.getTglFPB());
            }
        }
    }
    
    //METHOD UNTUK MENGISI DATA KEDALAM COMBO BOX 
    public void isicombobarang() {
        form.getCmbBrg().removeAllItems();
        form.getCmbBrg().addItem("Pilih");
        for (SP f : model.isicombobarang())
        {
            form.getCmbBrg().addItem(String.valueOf(f.getKdBrg()));
        }
    }
    
    //METHOD MENAMPILKAN DATA BARANG BEDASARKAN COMBO BOX
    public void tampilAllBrg() {
        if(form.getCmbBrg().getSelectedIndex() != 0)
        {
            for (SP f : model.getAllBrg(Integer.parseInt(form.getCmbBrg().getSelectedItem().toString())))
            {
                form.getTxtNmBrg().setText(f.getNmBrg());
                form.getTxtHarga().setText(String.valueOf(f.getHarga()));
                form.getTxtJmlMinta().setText(String.valueOf(f.getJumlahMinta()));
            }
        }
    }
    
    //METHOD UNTUK MENGISI DATA KEDALAM COMBO BOX 
    public void isicombospl() {
        form.getCmbSpl().removeAllItems();
        form.getCmbSpl().addItem("Pilih");
        for (SP f : model.isicombospl())
        {
            form.getCmbSpl().addItem(String.valueOf(f.getKdSpl()));
        }
    }
    
    //METHOD MENAMPILKAN DATA BARANG BEDASARKAN COMBO BOX
    public void getAllsSpl() {
        if(form.getCmbSpl().getSelectedIndex() != 0)
        {
            for (SP f : model.getAllsSpl(form.getCmbSpl().getSelectedItem().toString()))
            {
                form.getTxtNmSpl().setText(f.getNmSpl());
            }
        }
    }
    
    //METHOD UNTUK SIMPAN DATA KE TABEL SP
    public void insert() {
        SP f = new SP();
        f.setNoSp(form.getTxtNoSP().getText());
        f.setTglSP(form.getTxtTglSP().getText());
        f.setNoFPB(form.getCmbFPB().getSelectedItem().toString());
        f.setKdSpl(Integer.parseInt(form.getCmbSpl().getSelectedItem().toString()));
        model.insert(f);
    }
    
    //METHOD UNTUK SIMPAN DATA KE TABEL MINTA
    public void insert_pesan() {
        int jmlbaris = tblModel.getRowCount();
        int i, simpan = 0;
        
        for(i=0;i<jmlbaris;i++)
        {
            SP f = new SP();
            f.setNoSp(form.getTxtNoSP().getText());
            f.setKdBrg(Integer.parseInt(tblModel.getValueAt(i, 1).toString()));
            f.setHarga(Integer.parseInt(tblModel.getValueAt(i, 3).toString()));
            f.setJumlahPesan(Integer.parseInt(tblModel.getValueAt(i, 5).toString()));
            model.insert_pesan(f);
        }
    }
    
    //METHOD UNTUK CETAK
    public void cetak() {
        try {
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/CetakPesan.jasper";
            HashMap parameter = new HashMap();
            parameter.put("NoSP", (form.getTxtNoSP().getText()));
            
            JasperReport jp     = (JasperReport)JRLoader.loadObject(path);
            JasperPrint print   = JasperFillManager.fillReport(jp, parameter,conn);
            JasperViewer.viewReport(print,false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
            //CETAK KE EXCEL
            File xlsx = new File("D:/Permintaan"+(form.getTxtNoSP().getText()+".xlsx"));
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
