package al.ozone.bl.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * An iBATIS type handler for java.lang.Booleans that are mapped to either 'Y'
 * or 'N' in the database. If a value is something other than 'Y' in the
 * database, including <code>null</code>, the resulting Boolean will be false.
 * <p>
 * DB --> Java ---------------- 'Y' true null false 'N' false 'blah' false
 * 
 * Java --> DB ---------------- null 'N' false 'N' true 'Y'
 */
public class YNBooleanTypeHandler implements TypeHandler<Object> {
    /** Indicates Yes or true. */
    static final String TRUE_STRING = "Y";

    /** Indicates No or false. */
    static final String FALSE_STRING = "N";

    /**
     * From Java to DB.
     */
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String dbValue;
        if (parameter == null) {
            dbValue = FALSE_STRING;
        }

        final Boolean bool = (Boolean) parameter;

        if (bool.booleanValue()) {
            dbValue = TRUE_STRING;
        } else {
            dbValue = FALSE_STRING;
        }
        ps.setString(i, dbValue);
    }

    /**
     * From DB to Java.
     */
    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        return valueOf(rs.getString(columnName));
    }

    public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return valueOf(cs.getString(columnIndex));
    }

    /**
     * Converts DB value to the Java value.
     */
    public Boolean valueOf(String s) {
        if (s == null) {
            return Boolean.FALSE;
        }

        final String value = trim(s);

        if (TRUE_STRING.equals(value)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Trims the String if not null.
     */
    static String trim(String string) {
        return (string == null) ? null : string.trim();
    }

}
