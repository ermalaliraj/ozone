package al.ozone.bl.manager;

import java.util.HashMap;

/**
 * The Interface ReportManager.
 */
public interface ReportManager {

    /**
     * Creates a report in pdf format.
     * 
     * @param jasperFile
     *            the jasper report source file
     * @param parameters
     *            the parameters (to be passed to jasper source file)
     * @return the byte[]
     */
    byte[] createReportPdf(String jasperFile, HashMap<String, Object> parameters);

}
