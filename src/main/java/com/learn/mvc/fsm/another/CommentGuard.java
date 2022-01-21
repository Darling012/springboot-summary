package com.learn.mvc.fsm.another;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

/**
 * @program: girl
 * @version:
 * @author: ling
 * @createTime: 2021-09-01 09:14
 **/
@Component
public class CommentGuard implements Guard {
    @Override
    public boolean evaluate(StateContext stateContext) {
        return false;
    }
}
