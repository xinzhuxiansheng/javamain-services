package com.javamain.common.util.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import java.util.List;
import java.util.Map;

public class JsonUtils {
    /* **********************
     *
     * 序列化是将对象转换为字节序列的过程, 序列化后的对象可以在网络中传输。
     * Json就是将对象转换为Json字符串。
     *
     * 在前端中,有json对象和json字符串之分，在后端只有对象和Json字符串之分, java中指Java对象。
     *
     * json格式：
     * 1. 对象形式： {"h":"hello"}
     * 2. 数组形式： [{"h":"hello"},{"w":"world"}]
     *
     * Json工具类中，Json字符串和Java对象之间的转换是关键
     *
     * *********************/

    /**
     * description: Java对象转换为Json字符串
     * @param obj:
     * @return java.lang.String
     */
    public static String obj2JsonStr(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * description: Json字符串转换为Java对象
     * @param json:
     * @return java.lang.Object
     */
    public static <T> T jsonStr2Obj(String json,Class<T> cls) {
        return JSON.parseObject(json,cls);
    }

    /**
     * author: Imooc
     * description: Json字符串转换为指定类型的Map
     * @param json:
     * @param key:
     * @param value:
     * @return java.util.Map<k,v>
     */

    /* **********************
     * 为什么需要将Json字符串转换为指定类型的Map
     *
     * 例如, 有如下格式json
     * "{'颜色':'黑色','规格':'16GB'}"
     * "{'颜色':'白色','规格':'128GB'}"
     *
     * 前端需要返回格式
     * '颜色': ['黑色','白色']
     *
     * 后端可以以Map类型返回：
     * Map<String,Array<String>>
     *
     * *********************/
    public static <K,V> Map<K,List<V>> jsonStr2Map(String json, Class<K> key, Class<V> value) {

        /* **********************
         *
         * 因为Map带有泛型，如果直接使用Map.class会导致类型转换失败
         * 需要用TypeReference处理复杂的泛型对象
         *
         * TypeReference的构造方法有有参构造和无参构造
         *
         * 有参构造的参数是指泛型的类型变量~
         *
         * *********************/
        return JSON.parseObject(json,new TypeReference<Map<K,List<V>>>(key,value){});

    }

    /**
     * description: json字符串转换为指定类型的List
     * @param json:
     * @param cls:
     * @return java.util.List<T>
     */

    /* **********************
     * 为什么需要将json字符串转换为指定类型的List ？
     *
     * 例如，有如下json
     * "[{'颜色':'黑色','规格':'16GB'},{'颜色':'白色','规格':'128GB'}]"
     *
     * 可以将每一行转换为实体类，放入List数组里。
     *
     * *********************/
    public static <T> List<T> jsonStr2List(String json, Class<T> cls) {

        return JSON.parseArray(json,cls);

    }
}
