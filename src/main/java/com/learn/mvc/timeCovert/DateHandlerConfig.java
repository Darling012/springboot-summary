package com.learn.mvc.timeCovert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 日期处理全局配置
 * <see>https://segmentfault.com/a/1190000021906586</see>
 */
@Configuration
public class DateHandlerConfig {

    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    // 处理不了 requestParam是pojo情况
    // /**
    //  * LocalDate转换器，用于转换RequestParam和PathVariable参数
    //  * `@ConditionalOnBean(name = "requestMappingHandlerAdapter")`: 等requestMappingHandlerAdapter bean注册完成之后
    //  * 再添加自己的`converter`就不会注册到`FormattingConversionService`中
    //  */
    // @Bean
    // @ConditionalOnBean(name = "requestMappingHandlerAdapter")
    // public Converter<String, LocalDate> localDateConverter() {
    //     return source -> LocalDate.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    // }
    //
    // /**
    //  * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
    //  */
    // @Bean
    // @ConditionalOnBean(name = "requestMappingHandlerAdapter")
    // public Converter<String, LocalDateTime> localDateTimeConverter() {
    //     return source -> LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
    // }
    //
    // /**
    //  * LocalTime转换器，用于转换RequestParam和PathVariable参数
    //  */
    // @Bean
    // @ConditionalOnBean(name = "requestMappingHandlerAdapter")
    // public Converter<String, LocalTime> localTimeConverter() {
    //     return source -> LocalTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
    // }




    @Bean
 public Formatter<LocalDateTime> localDateFormatter() {
      return new Formatter<LocalDateTime>() {
          @Override
         public LocalDateTime parse(String text, Locale locale) throws ParseException {
              return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
          }
          @Override
          public String print(LocalDateTime object, Locale locale) {
              return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
          }
        };
    }


    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            }
        };
    }

    // 平铺参数返回的都带T 这么写不管用 瞎蒙的 todo
    @Bean
    public Converter<LocalDateTime, String> localDateTime2StringConverter() {
        return new Converter<LocalDateTime, String>() {
            @Override
            public String convert(LocalDateTime source) {

                return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

        };
    }

    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
        };
    }

    /**
     * Date转换器，用于转换RequestParam和PathVariable参数
     * 这里关于解析各种格式的日期格式采用了 hutool 的日期解析工具类
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @SneakyThrows
            @Override
            public Date convert(String source) {
                // return DateUtil.parse(source.trim());
                SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                return formatter.parse(source);
            }
        };
    }

    /**
     * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json（非json序列化也会走比如平铺参数带T问题，会走下面配置的序列化方案，会去除掉，应该是总体的序列化方案）
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        //LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                                     new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class,
                                     new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class,
                                     new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class,
                                       new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class,
                                       new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));


        //Date序列化和反序列化
        javaTimeModule.addSerializer(Date.class, new JsonSerializer() {
            /**
             * Method that can be called to ask implementation to serialize
             * values of type this serializer handles.
             *
             * @param value       Value to serialize; can <b>not</b> be null.
             * @param gen         Generator used to output resulting Json content
             * @param serializers Provider that can be used to get serializers for
             */
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                String formattedDate = formatter.format(value);
                gen.writeString(formattedDate);
            }
        });
        javaTimeModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws
                    IOException,
                    JsonProcessingException {
                SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                String date = jsonParser.getText();
                try {
                    return format.parse(date);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}
