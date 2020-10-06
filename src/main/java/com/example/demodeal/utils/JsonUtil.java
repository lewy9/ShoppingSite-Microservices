package com.example.demodeal.utils;

import com.example.demodeal.domain.User;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;


public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }



    public static <T> String obj2String(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println("Parse Object to String error"+e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println("Parse Object to String error"+e);
            return null;
        }
    }





    public static <T> T string2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }

        try {
            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str,clazz);
        } catch (Exception e) {
            System.out.println("Parse String to Object error"+e);
            System.out.println();
            return null;
        }
    }



    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            System.out.println("Parse String to Object error"+e);
            return null;
        }
    }


    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            System.out.println("Parse String to Object error"+e);
            return null;
        }
    }


//
//    public static void main(String[] args) {
//        User testPojo = new User();
//        testPojo.setUsername("Geely");
//        testPojo.setId(666);
//
//        //{"name":"Geely","id":666}
//        String json = "{\"id\":666,\"username\":\"Geely\",\"password\":null}";
//        User testPojoObject = JsonUtil.string2Obj(json,User.class);
//        String testPojoJson = JsonUtil.obj2String(testPojo);
//        System.out.println(testPojoObject.getUsername());
//
//        System.out.println("end");
//
//    }


}
