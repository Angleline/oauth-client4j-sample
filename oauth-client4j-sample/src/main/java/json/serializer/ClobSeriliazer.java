package json.serializer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobSeriliazer implements ObjectSerializer {

    public final static ClobSeriliazer instance = new ClobSeriliazer();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
    	Reader reader = null;
        try {
            if (object == null) {
                serializer.writeNull();
                return;
            }
            
            Clob clob = (Clob) object;
            reader = clob.getCharacterStream();

            StringWriter writer = new StringWriter();
            char[] buf = new char[1024];
            int len = 0;
            while ((len = reader.read(buf)) != -1) {
                writer.write(buf, 0, len);
            }
            
            String text = writer.toString();
            serializer.write(text);
        } catch (SQLException e) {
            throw new IOException("write clob error", e);
        } finally {
        	if (null != reader) {
        		reader.close();
			}
        }
    }

}
