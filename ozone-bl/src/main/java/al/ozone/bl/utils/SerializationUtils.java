package al.ozone.bl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SerializationUtils {
    private static Log logger = LogFactory.getLog(SerializationUtils.class);

    public static Object load(File file) throws Exception {
        Object ret = null;
        if (file.exists()) {
        	logger.debug("Loading object from file " + file.getAbsolutePath());

            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            ret = objectInputStream.readObject();

            IOUtils.closeQuietly(objectInputStream);
            IOUtils.closeQuietly(fileInputStream);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Object not found in  file " + file.getAbsolutePath());
            }
        }
        return ret;
    }

    public static void save(File file, Serializable object) throws Exception {
    	logger.debug("Saving " + object + " to file " + file.getAbsolutePath());

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(object);
        objectOutputStream.flush();

        IOUtils.closeQuietly(objectOutputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }

    /**
     * Returns the temp (SO) folder for the logged user.
     * Example: C:\Users\Ermal\AppData\Local\Temp\
     * @return temp logged user
     */
    public static String getTempDir() {
        return System.getProperty("java.io.tmpdir");
    }
}
