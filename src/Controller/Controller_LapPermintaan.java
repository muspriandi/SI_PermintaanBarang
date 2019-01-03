/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.LapPermintaan;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
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
public class Controller_LapPermintaan {
    LapPermintaan form;
    
    //KONSTRUKTOR
    public Controller_LapPermintaan(LapPermintaan form) {
        this.form = form;
    }
    
    public void awal() {
        SimpleDateFormat tglAwal     = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tglAkhir    = new SimpleDateFormat("yyyy-MM-dd");
        form.getTgl1().setText(String.valueOf(tglAwal.format(new Date())));
        form.getTgl2().setText(String.valueOf(tglAkhir.format(new Date())));
   }
    
    public void cetak_preview() {
        try {
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/RepPermintaan.jasper";
            HashMap parameter = new HashMap();
            parameter.put("tgl_awal", (form.getTgl1().getText()));
            parameter.put("tgl_akhir", (form.getTgl2().getText()));
            
            JasperReport jp     = (JasperReport)JRLoader.loadObject(path);
            JasperPrint print   = JasperFillManager.fillReport(jp, parameter,conn);
            JasperViewer.viewReport(print,false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }
        catch(Exception z) {
            JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak! "+z.getMessage(), "Cetak Data",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void cetak_excel() {
        try {
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/RepPermintaan.jasper";
            File xlsx = new File("D:/LapPermintaan.xlsx");
            HashMap parameter = new HashMap();
            parameter.put("tgl_awal", (form.getTgl1().getText()));
            parameter.put("tgl_akhir", (form.getTgl2().getText()));
            
            JasperPrint print   = JasperFillManager.fillReport(path,parameter,conn);
            
            JRXlsxExporter Xlsxexporter = new JRXlsxExporter();
            Xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
            Xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE,xlsx);
            Xlsxexporter.exportReport();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil dicetak, cek pada drive D:/LapPermintaan.xlsx");
        }
        catch(Exception z) {
            JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak! "+z.getMessage(), "Cetak Data",JOptionPane.ERROR_MESSAGE);
        }
    }
}
