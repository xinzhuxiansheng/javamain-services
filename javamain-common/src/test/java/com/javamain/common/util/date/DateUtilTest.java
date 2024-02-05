//package com.javamain.common.util.date;
//
//import java.time.LocalDateTime;
//
//public class DateUtilTest {
//    @DisplayName("Junt5测试LocalDateTime转String")
//    @Test
//    void testConvertLocalDateTime2Str1() {
//        assertEquals(
//                "2020-01-01 00:00:00",
//                DateUtil.convertLocalDateTime2Str(LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
//    }
//
//    @DisplayName("Junit4测试LocalDateTime转String")
//    @Test
//    void testConvertLocalDateTime2Str() {
//        assertThat(
//                DateUtil.convertLocalDateTime2Str(
//                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)))
//                .isEqualTo("2020-01-01 00:00:00");
//    }
//
//    @DisplayName("Junit4测试String转LocalDateTime")
//    @Test
//    void testConvertStr2LocalDateTime() {
//        assertThat(
//                DateUtil.convertStr2LocalDateTime("2020-01-01 00:00:00"))
//                .isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
//    }
//
//    /* **********************
//     *
//     * 注意：
//     *
//     * 时间戳是毫秒
//     *
//     * *********************/
//
//    @DisplayName("Junit4测试时间戳转LocalDateTime")
//    @Test
//    void testConvertTimestamp2LocalDateTime() {
//        assertThat(
//                DateUtil.convertTimestamp2LocalDateTime(1577808000000L))
//                .isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
//    }
//
//    @DisplayName("Junit4测试LocalDateTime转时间戳")
//    @Test
//    void testConvertLocalDateTime2Timestamp() {
//        assertThat(
//                DateUtil.convertLocalDateTime2Timestamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0)))
//                .isEqualTo(1577808000000L);
//    }
//
//    @DisplayName("Junit5测试LocalDateTime转时间戳")
//    @Test
//    void testConvertLocalDateTime2Timestamp1() {
//        assertEquals(
//                1577808000000L,
//                DateUtil.convertLocalDateTime2Timestamp(LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
//    }
//
//    @DisplayName("Junit5测试String转LocalDateTime")
//    @Test
//    void testConvertStr2LocalDateTime1() {
//        assertEquals(
//                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
//                DateUtil.convertStr2LocalDateTime("2020-01-01 00:00:00")
//        );
//    }
//
//    @DisplayName("Junit5测试时间戳转LocalDateTime")
//    @Test
//    void testConvertTimestamp2LocalDateTime1() {
//        assertEquals(
//                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
//                DateUtil.convertTimestamp2LocalDateTime(1577808000000L)
//        );
//    }
//}
