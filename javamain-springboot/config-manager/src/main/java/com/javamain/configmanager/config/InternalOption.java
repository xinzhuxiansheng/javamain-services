package com.javamain.configmanager.config;

import lombok.Data;

/**
 * 这里一定要使用Build模式
 *
 * @author yzhou
 * @date 2022/5/10
 */
@Data
public class InternalOption {
    private String key;
    private Object defaultValue;
    private String inputFormat;
    private Class classType;
    private String description;

    public static InternalOptionBuilder builder() {
        return new InternalOptionBuilder();
    }

    public static class InternalOptionBuilder {
        //        private String key;
//        private Object defaultValue;
//        private Class classType;
//        private String description;
        private InternalOption option;

        public InternalOptionBuilder() {
            option = new InternalOption();
        }

        public InternalOptionBuilder key(String key) {
            option.key = key;
            return this;
        }

        public InternalOptionBuilder defaultValue(Object defaultValue) {
            option.defaultValue = defaultValue;
            return this;
        }

        public InternalOptionBuilder inputFormat(String inputFormat) {
            option.inputFormat = inputFormat;
            return this;
        }

        public InternalOptionBuilder classType(Class classType) {
            option.classType = classType;
            return this;
        }

        public InternalOptionBuilder description(String description) {
            option.description = description;
            return this;
        }

        public InternalOption build(){
            return option;
        }
    }

    private InternalOption() {

    }
}
