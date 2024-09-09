//package com.javamain.common.util.json;
//
//import java.util.List;
//import java.util.Map;
//
//public class JsonUtilTest {
//
//
//    @DisplayName("Json字符串转Java对象")
//    @Test
//    public void testJsonStr2Obj() {
//
//        String json = "{\"color\":\"黑色\",\"specs\":\"16GB\"}";
//
//        Product product = JsonUtil.jsonStr2Obj(json,Product.class);
//        System.out.println(product.getColor());
//    }
//
//    @DisplayName("Java对象转Json字符串")
//    @Test
//    public void testObj2JsonStr() {
//
//        Product product = new Product();
//        product.setColor("黑色");
//        product.setSpecs("16GB");
//
//        String jsonStr = JsonUtil.obj2JsonStr(product);
//        System.out.println(jsonStr);
//    }
//
//    @DisplayName("Json字符串转指定对象的Map")
//    @Test
//    public void testJsonStr2Map() {
//
//        /* **********************
//         * 注意Json的格式
//         *
//         * *********************/
//
//        String json = "{\"data\":[" +
//                "{\"color\":\"黑色\",\"specs\":\"16GB\"}," +
//                "{\"color\":\"白色\",\"specs\":\"124GB\"}" +
//                "]}";
//
//        Map<String, List<Product>> map =  JsonUtil.jsonStr2Map(json,String.class,Product.class);
//        List<Product> products = map.get("data");
//        for(Product product:products){
//            System.out.println(product.getSpecs());
//        }
//
//    }
//
//
//    @DisplayName("Json字符串转指定对象的List")
//    @Test
//    public void testJsonStr2List() {
//
//        /* **********************
//         * 注意Json的格式
//         *
//         * *********************/
//        String json = "[" +
//                "{\"color\":\"黑色\",\"specs\":\"16GB\"}," +
//                "{\"color\":\"白色\",\"specs\":\"124GB\"}" +
//                "]";
//
//        List<Product> list = JsonUtil.jsonStr2List(json,Product.class);
//        for(Product product:list){
//            System.out.println(product.getColor());
//        }
//
//    }
//
//    /**
//     * description: 商品实体类
//     * date:  2023
//     */
//    @Data
//    class Product {
//        //颜色
//        private String color;
//        //规格
//        private String specs;
//    }
//
//}
