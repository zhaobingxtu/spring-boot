package com.milton.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.milton.jsonDeserializer.BigDecimalJsonDeserializer;
import com.milton.jsonDeserializer.DateJsonDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class JacksonUtils {
    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectMapper objectMapperIgnoreNull = new ObjectMapper();
    private static final String NULL_JSON = "{}";

    public JacksonUtils() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static String obj2json(Object obj) {
        if (obj == null) {
            return "{}";
        } else if (checkStrJson(obj.toString())) {
            return obj.toString();
        } else {
            try {
                return objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                log.error(">>>>>obj2json fail:", var2);
                return "{}";
            }
        }
    }

    public static String obj2jsonIgnoreNull(Object obj) {
        if (obj == null) {
            return "{}";
        } else if (checkStrJson(obj.toString())) {
            return obj.toString();
        } else {
            try {
                return objectMapperIgnoreNull.writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                log.error(">>>>>obj2json fail:", var2);
                return "{}";
            }
        }
    }

    public static <T> T json2bean(String jsonString, Class<T> clazz) {
        return json2pojo(jsonString, clazz);
    }

    public static <T> T json2pojo(String jsonString, Class<T> clazz) {
        if (StringUtils.isNotBlank(jsonString)) {
            try {
                return objectMapper.readValue(jsonString, clazz);
            } catch (JsonProcessingException var3) {
                log.error(">>>>>json2pojo fail:", var3);
            }
        }

        return null;
    }

    public static <K, V> Map<K, V> json2Map(String json, Class<K> keyClass, Class<V> valueClass) {
        if (StringUtils.isBlank(json)) {
            return new HashMap();
        } else {
            try {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, new Class[]{keyClass, valueClass});
                return (Map) objectMapper.readValue(json, javaType);
            } catch (IOException var4) {
                log.error(">>>>>json2Map fail:", var4);
                return new HashMap();
            }
        }
    }

    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonArrayStr)) {
            return new ArrayList();
        } else {
            try {
                JavaType javaType = getCollectionType(ArrayList.class, clazz);
                List<T> list = (List) objectMapper.readValue(jsonArrayStr, javaType);
                if (list != null && list.size() > 0) {
                    list = (List) list.stream().filter(Objects::nonNull).collect(Collectors.toList());
                }

                return list;
            } catch (JsonProcessingException var4) {
                log.error(">>>>>json2list fail:", var4);
                return new ArrayList();
            }
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static String mapToJson(Map map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception var2) {
            log.error(">>>>>mapToJson fail:", var2);
            return "{}";
        }
    }

    public static <T> T obj2pojo(Object obj, Class<T> clazz) {
        return objectMapper.convertValue(obj, clazz);
    }

    public static <T> List<T> getListVarValue(String json, String varName, Class<T> clazz) throws JsonProcessingException {
        Object o = getVarValue(json, varName);
        return o == null ? null : json2list(obj2json(o), clazz);
    }

    public static <T> T getVarValue(String json, String varName, Class<T> clazz) throws JsonProcessingException {
        Object o = getVarValue(json, varName);
        if (o == null) {
            return null;
        } else {
            return clazz == String.class && !(o instanceof String) ? objectMapper.convertValue(o.toString(), clazz) : objectMapper.convertValue(o, clazz);
        }
    }

    public static Object getVarValue(String json, String varName) throws JsonProcessingException {
        if (!StringUtils.isBlank(json) && !StringUtils.isBlank(varName)) {
            if (!varName.contains(".")) {
                return getValue(json, varName);
            } else {
                int index = varName.indexOf(".");
                String curr = varName.substring(0, index);
                String other = varName.substring(index + 1);
                ObjectMapper mapper = getInstance();
                JsonNode node = mapper.readTree(json);
                node = node.get(curr);
                if (node == null) {
                    return null;
                } else if (!node.isArray()) {
                    return getVarValue(node.toString(), other);
                } else {
                    List<Object> list = new ArrayList();

                    for (int i = 0; i < node.size(); ++i) {
                        Object object = getVarValue(node.get(i).toString(), other);
                        if (object != null) {
                            if (object instanceof List) {
                                list.addAll((Collection) object);
                            } else {
                                list.add(object);
                            }
                        }
                    }

                    return list;
                }
            }
        } else {
            return null;
        }
    }

    public static Set<String> getJsonKeySet(String json) throws JsonProcessingException {
        Set<String> set = new HashSet();
        if (StringUtils.isBlank(json)) {
            return set;
        } else {
            ObjectMapper mapper = getInstance();
            JsonNode node = mapper.readTree(json);
            Iterator<Map.Entry<String, JsonNode>> it = node.fields();

            while (true) {
                while (it.hasNext()) {
                    Map.Entry<String, JsonNode> elt = (Map.Entry) it.next();
                    String key = (String) elt.getKey();
                    JsonNode jsonNode = (JsonNode) elt.getValue();
                    if (jsonNode instanceof ObjectNode) {
                        ObjectNode objectNode = (ObjectNode) jsonNode;
                        Set<String> children = getJsonKeySet(mapper.writeValueAsString(objectNode));
                        Iterator var15 = children.iterator();

                        while (var15.hasNext()) {
                            String s = (String) var15.next();
                            set.add(key + "." + s);
                        }
                    } else if (jsonNode instanceof ArrayNode) {
                        ArrayNode arrayNode = (ArrayNode) jsonNode;
                        if (arrayNode.size() > 0) {
                            JsonNode childNode = arrayNode.get(0);
                            if (childNode instanceof ValueNode) {
                                set.add(key);
                            } else {
                                Set<String> children = getJsonKeySet(mapper.writeValueAsString(childNode));
                                Iterator var11 = children.iterator();

                                while (var11.hasNext()) {
                                    String s = (String) var11.next();
                                    set.add(key + "." + s);
                                }
                            }
                        }
                    } else {
                        set.add(key);
                    }
                }

                return set;
            }
        }
    }

    public static int getKeyResultCount(String json, String key) throws JsonProcessingException {
        if (!StringUtils.isBlank(json) && !StringUtils.isBlank(key)) {
            Object o = getVarValue(json, key);
            if (o instanceof ArrayNode) {
                return ((ArrayNode) o).size();
            } else if (o instanceof List) {
                return ((List) o).size();
            } else {
                return o == null ? 0 : 1;
            }
        } else {
            return -1;
        }
    }

    private static Object getValue(String json, String varName) throws JsonProcessingException {
        ObjectMapper mapper = getInstance();
        JsonNode node = mapper.readTree(json);
        if (node == null) {
            return null;
        } else {
            ArrayList list;
            int i;
            if (node.isArray()) {
                list = null;

                for (i = 0; i < node.size(); ++i) {
                    Object object = getVarValue(node.get(i).toString(), varName);
                    if (object != null) {
                        if (object instanceof List) {
                            if (list == null) {
                                list = new ArrayList();
                            }

                            list.addAll((Collection) object);
                        } else {
                            if (list == null) {
                                list = new ArrayList();
                            }

                            list.add(object);
                        }
                    }
                }

                return list;
            } else {
                node = node.get(varName);
                if (node != null && !(node instanceof NullNode)) {
                    if (node.isArray()) {
                        list = new ArrayList();

                        for (i = 0; i < node.size(); ++i) {
                            JsonNode n = node.get(i);
                            if (n != null && !(n instanceof NullNode)) {
                                if (n.isValueNode()) {
                                    list.add(n.asText());
                                } else {
                                    list.add(n);
                                }
                            }
                        }

                        return list;
                    } else {
                        return node.isValueNode() ? node.asText() : node;
                    }
                } else {
                    return null;
                }
            }
        }
    }

    public static String jsonRemove(String json, String key) {
        if (StringUtils.isBlank(json)) {
            return "{}";
        } else {
            try {
                JsonNode node = objectMapper.readTree(json);
                if (node.isArray()) {
                    return json;
                } else {
                    ObjectNode objectNode = (ObjectNode) node;
                    jsonRemoveNode(objectNode, key);
                    return objectMapper.writeValueAsString(objectNode);
                }
            } catch (JsonProcessingException var4) {
                log.error(">>>>>jsonRemoveNode fail...", var4);
                return json;
            }
        }
    }

    private static void jsonRemoveNode(ObjectNode objectNode, String node) {
        if (node.contains(".")) {
            int index = node.indexOf(".");
            String curr = node.substring(0, index);
            String other = node.substring(index + 1);
            ObjectNode newNode = (ObjectNode) objectNode.get(curr);
            if (newNode == null) {
                return;
            }

            jsonRemoveNode(newNode, other);
            objectNode.set(curr, newNode);
        } else {
            objectNode.remove(node);
        }

    }

    public static String jsonAppend(String json, String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return null;
        } else {
            if (StringUtils.isBlank(json)) {
                json = "{}";
            }

            try {
                JsonNode node = objectMapper.readTree(json);
                if (key.contains(".")) {
                    int index = key.lastIndexOf(".");
                    String addKey = key.substring(index + 1);
                    key = key.substring(0, index);
                    String[] keys = key.split("\\.");
                    JsonNode jsonNode = node;

                    for (int i = 0; i < keys.length; ++i) {
                        String k = keys[i];
                        if (!jsonNode.has(k)) {
                            jsonAppendValue(jsonNode, k, new HashMap());
                        }

                        jsonNode = jsonNode.get(k);
                        if (!(jsonNode instanceof ContainerNode)) {
                            log.error(">>>>>Can not append value into ValueNode...");
                            return objectMapper.writeValueAsString(node);
                        }
                    }

                    if (jsonNode != null) {
                        jsonAppendValue(jsonNode, addKey, value);
                    }
                } else {
                    jsonAppendValue(node, key, value);
                }

                return objectMapper.writeValueAsString(node);
            } catch (Exception var10) {
                log.error(">>>>>jsonAppend fail...", var10);
                return null;
            }
        }
    }

    private static void jsonAppendValue(JsonNode node, String key, Object value) {
        if (!(node instanceof ContainerNode)) {
            log.error(">>>>>Can not append value into ValueNode...");
        } else {
            try {
                if (value instanceof Integer) {
                    Integer i = Integer.valueOf(value.toString());
                    jsonAdd(node, key, i);
                } else if (value instanceof Long) {
                    Long l = Long.valueOf(value.toString());
                    jsonAdd(node, key, l);
                } else if (value instanceof Double) {
                    Double d = Double.valueOf(value.toString());
                    jsonAdd(node, key, d);
                } else {
                    String s;
                    if (value instanceof Date) {
                        s = ConstantUtils.SIMPLE_DATETIME_FORMAT.format(value);
                        jsonAdd(node, key, s);
                    } else if (value instanceof String) {
                        s = value.toString();
                        jsonAdd(node, key, s);
                    } else {
                        jsonAdd(node, key, value);
                    }
                }
            } catch (JsonProcessingException var4) {
                log.error(">>>>jsonAppend fail...", var4);
            }

        }
    }

    private static void jsonAdd(JsonNode node, String jsonKey, Double jsonValue) throws JsonProcessingException {
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            arrayNode.add(jsonValue);
        } else {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.put(jsonKey, jsonValue);
        }

    }

    private static void jsonAdd(JsonNode node, String jsonKey, Long jsonValue) throws JsonProcessingException {
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            arrayNode.add(jsonValue);
        } else {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.put(jsonKey, jsonValue);
        }

    }

    private static void jsonAdd(JsonNode node, String jsonKey, Integer jsonValue) throws JsonProcessingException {
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            arrayNode.add(jsonValue);
        } else {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.put(jsonKey, jsonValue);
        }

    }

    private static void jsonAdd(JsonNode node, String jsonKey, String jsonValue) throws JsonProcessingException {
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            arrayNode.add(jsonValue);
        } else {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.put(jsonKey, jsonValue);
        }

    }

    private static void jsonAdd(JsonNode node, String jsonKey, Object jsonValue) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(obj2json(jsonValue));
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            arrayNode.add(jsonNode);
        } else {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.set(jsonKey, jsonNode);
        }

    }

    private static JsonNode strToJsonNode(String str) {
        ObjectMapper mapper = getInstance();

        try {
            JsonNode node = mapper.readTree(str);
            return node;
        } catch (JsonProcessingException var3) {
            return null;
        }
    }

    public static boolean checkStrJson(String json) {
        if (StringUtils.isBlank(json)) {
            return false;
        } else if (!json.startsWith("{") && !json.startsWith("[")) {
            return false;
        } else if (!json.endsWith("}") && !json.endsWith("]")) {
            return false;
        } else {
            JsonNode node = strToJsonNode(json);
            return node != null;
        }
    }

    public static String jsonRemove(String json, String key, int index) {
        if (StringUtils.isBlank(json)) {
            return "{}";
        } else {
            try {
                JsonNode node = objectMapper.readTree(json);
                if (StringUtils.isBlank(key)) {
                    if (node.isArray()) {
                        ArrayNode arrayNode = (ArrayNode) node;
                        arrayNode.remove(index);
                        return objectMapper.writeValueAsString(arrayNode);
                    } else {
                        return json;
                    }
                } else {
                    String[] keys = key.split("\\.");
                    JsonNode jsonNode = node;

                    for (int i = 0; i < key.length(); ++i) {
                        if (jsonNode.isArray()) {
                            return json;
                        }

                        jsonNode = jsonNode.get(keys[i]);
                    }

                    if (jsonNode.isArray()) {
                        ArrayNode arrayNode = (ArrayNode) jsonNode;
                        arrayNode.remove(index);
                        return objectMapper.writeValueAsString(node);
                    } else {
                        return json;
                    }
                }
            } catch (JsonProcessingException var7) {
                log.error(">>>>>jsonRemoveNode fail...", var7);
                return json;
            }
        }
    }

    public static JsonNode parser(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException var2) {
            log.error(">>>>>can not parse to json,str={}", json);
            return null;
        }
    }

    static {
        objectMapper.setSerializationInclusion(Include.ALWAYS);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.setDateFormat(ConstantUtils.SIMPLE_DATETIME_FORMAT);
        objectMapper.setTimeZone(ConstantUtils.TIME_ZONE);
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        simpleModule.addDeserializer(Date.class, new DateJsonDeserializer());
        simpleModule.addDeserializer(BigDecimal.class, new BigDecimalJsonDeserializer());
        objectMapper.registerModule(simpleModule);
        objectMapperIgnoreNull.setSerializationInclusion(Include.NON_NULL);
        objectMapperIgnoreNull.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapperIgnoreNull.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapperIgnoreNull.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapperIgnoreNull.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapperIgnoreNull.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapperIgnoreNull.setDateFormat(ConstantUtils.SIMPLE_DATETIME_FORMAT);
        objectMapperIgnoreNull.setTimeZone(ConstantUtils.TIME_ZONE);
        objectMapperIgnoreNull.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        objectMapperIgnoreNull.registerModule(simpleModule);
    }
}
