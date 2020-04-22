package ca.jrvs.apps.twitter.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonParser {

  /**
   * Convert a Java object to JSON string
   *
   * @param object input object
   * @return JSON string
   */
  public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    if (!includeNullValues) {
      mapper.setSerializationInclusion(Include.NON_NULL);
    }
    if (prettyJson) {
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return mapper.writeValueAsString(object);
  }

  /**
   * Parse JSON string to a object
   *
   * @param j JSON string
   * @param c object class
   * @return Object
   */
  public static <T> T toObjectFromJson(String j, Class c) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return (T) mapper.readValue(j, c);
  }

}
