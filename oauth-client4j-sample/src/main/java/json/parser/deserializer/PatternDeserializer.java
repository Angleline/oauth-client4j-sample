package json.parser.deserializer;

import java.lang.reflect.Type;
import java.util.regex.Pattern;

import json.parser.DefaultJSONParser;
import json.parser.JSONToken;

public class PatternDeserializer implements ObjectDeserializer {
    public final static PatternDeserializer instance = new PatternDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        Object value = parser.parse();

        if (value == null) {
            return null;
        }
        
        String pattern = (String) value;
        
        return (T) Pattern.compile(pattern);
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
    }
}
