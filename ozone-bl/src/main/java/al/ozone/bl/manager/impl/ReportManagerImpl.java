package al.ozone.bl.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.bl.manager.ReportManager;

public class ReportManagerImpl implements ReportManager {
    static transient Log logger = LogFactory.getLog(ReportManagerImpl.class);
    private DataSource dataSource;

    public byte[] createReportPdf(String jasperFile, HashMap<String, Object> parameters) {

        Connection connection = null;
        InputStream jrxml = null;
        try {
            //logger.info("ReportManager - Opening connection to DB");
            connection = dataSource.getConnection();
            jrxml = getClass().getClassLoader().getResourceAsStream(jasperFile);
            //logger.debug("ReportManager - Loading file: " + jasperFile);
            JasperDesign design = JRXmlLoader.load(jrxml);
            JasperReport jasper = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, connection);
            byte thePdf[] = null;
            if ((jasperPrint.getPages().size()) > 0) {
                thePdf = JasperExportManager.exportReportToPdf(jasperPrint);
            }
            return thePdf;
        } catch (Exception e) {
            logger.error("Errore occoured during report generation:" + e.getMessage(), e);
            return null;
        } finally {
            // Close connection
            if (connection != null) {
                try {
                    //logger.info("JasperManager - Closing connection to DB");
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Close inputStream
            if (jrxml != null) {
                try {
                    jrxml.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
