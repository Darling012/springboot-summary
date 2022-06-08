package com.learn.mvc.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * <see>https://www.jianshu.com/p/5d4ffe267596</see>
 * ApplicationArguments是对参数（main方法）做了进一步的处理，可以解析--name=value的，我们就可以通过name来获取value（而CommandLineRunner只是获取--name=value）
 * <p>
 * 不同于 CommandLineRunner 中的 run 方法的数组参数，ApplicationRunner 里 run 方法的参数是一个 ApplicationArguments 对象。
 * ApplicationArguments 区分选项参数和非选项参数：
 * 对于非选项参数：我们可以通过 ApplicationArguments 的 getNonOptionArgs() 方法获取，获取到的是一个数组。
 * 对于选项参数：可以通过 ApplicationArguments 的 getOptionNames() 方法获取所有选项名称。通过 getOptionValues() 方法获取实际值（它会返回一个列表字符串）。
 */
@Component
@Order(1)
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {
    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("init----系统启动成功后执行,{}", "自定义的ApplicationRunner事件");
        List<String> nonOptionArgs = args.getNonOptionArgs();
        // 不是键值对形式的
        log.info("init----系统启动成功后执行,{},非选项参数,{}", "自定义的ApplicationRunner事件", nonOptionArgs);
        Set<String> optionNames = args.getOptionNames();
        for (String optionName : optionNames) {
            System.out.println("init----系统启动时执行自定义的ApplicationRunner事件选项参数name:"+ optionName
                                       + ";value:" + args.getOptionValues(optionName));

        }

    }
}
