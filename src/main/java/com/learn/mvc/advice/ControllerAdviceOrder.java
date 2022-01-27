package com.learn.mvc.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Spring3.2开始提供的新注解，控制器增强（AOP），最主要的应用是做统一的异常处理。@ControllerAdvice（看成spring mvc提供的一个特殊的拦截器）。@ControllerAdvice是一个@Component，用于定义@ExceptionHandler（最主要用途），@InitBinder和@ModelAttribute方法，适用于所有使用@RequestMapping方法（拦截）。
   @ExceptionHandler：为所有controller封装统一异常处理代码。
 * @ModelAttribute：为所有controller设置全局变量。
 * @InitBinder：用于为所有controller设置某个类型的数据转换器。
 */
// @ControllerAdvice
@Slf4j
public class ControllerAdviceOrder {
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * WebDataBinder是用来绑定请求参数到指定的属性编辑器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
          log.info("5、执行-----ControllerAdvice-----initBinder----");
        // System.out.println("initBinder执行");
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // dateFormat.setLenient(false);  //日期格式是否宽容（只能判断是否需要跳到下个月去）
        //
        // /*
        //  * spring mvc在绑定表单之前，都会先注册这些编辑器，
        //  * Spring自己提供了大量的实现类，诸如CustomDateEditor,CustomBooleanEditor,CustomNumberEditor等
        //  * 使用时候调用WebDataBinder的registerCustomEditor方法注册
        //  */
        // binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
}
