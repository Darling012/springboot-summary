package com.learn.mvc.cache;

import com.learn.mvc.domain.entities.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @program: springboot-summary
 * @version:
 * @description:
 * @author: ling
 * @create: 2022-09-19 14:04
 **/
@RunWith(SpringRunner.class)
public class RedisSerTest {
    @InjectMocks
    private RedisTemplate<String, String> strRedisTemplate = new RedisTemplate<>();
    @InjectMocks
    private RedisTemplate redisTemplate = new RedisTemplate<>();

    @Test
    public void testString() {
        strRedisTemplate.opsForValue()
                        .set("strKey", "adb");
        System.out.println(strRedisTemplate.opsForValue()
                                           .get("strKey"));
    }

    @Test
    public void testSerializable() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 221kb 带包路径  需要序列化 jdk反序列化，若父类没有实现序列化接口，将调用父类无参构造器，且父类属性为null
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        // 22kb  不带包路径
        // redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Student.class));
        // 52kb 带包路径
        // redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        //46kb 不带包路径
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        // 77kb  带包路径
        // redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());


        Student girl = new Student();
        girl.setId(1L);
        girl.setName("C");
        redisTemplate.opsForValue()
                     .set("girl", girl);
        Student user2 = (Student) redisTemplate.opsForValue()
                                               .get("girl");
        System.out.println("user:" + user2.getId() + "," + user2.getAge());
    }

    @Test
    public void contextLoads() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(List.class));
        ValueOperations<String, List<Student>> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("aaa", Arrays.asList(new Student(1L, "24", 1), new Student(2L, "30", 1)));

        List<Student> p = valueOperations.get("aaa");
        System.out.println(p); //[{name=fsx, age=24}, {name=fff, age=30}]

        List<Student> aaa = (List<Student>) redisTemplate.opsForValue()
                                                         .get("aaa");
        System.out.println(aaa); //[{name=fsx, age=24}, {name=fff, age=30}]
    }

    @Test
    public void contextLoadss() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("aaa", 1L);
        Object pp = valueOperations.get("aaa");
        System.out.println(pp);
        //转换异常 java.lang.Integer cannot be cast to java.lang.Long
        Long p = valueOperations.get("aaa");
        System.out.println(p);
    }

    @Test
    public void contextLoadsss() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 没问题
        // redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        // 有问题
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        SetOperations<String, Long> setOperations = redisTemplate.opsForSet();
        setOperations.add("bbb", 1L);
        setOperations.add("bbb", 2L);

        Set<Long> p = setOperations.members("bbb");
        setOperations.pop("bbb");
        System.out.println(p);

        Long longValue = 123L;
        redisTemplate.opsForValue()
                     .set("cacheLongValue", longValue);
        Object cacheValue = redisTemplate.opsForValue()
                                         .get("cacheLongValue");
        Long a = (Long) cacheValue;
        System.out.println(a);
    }
}
