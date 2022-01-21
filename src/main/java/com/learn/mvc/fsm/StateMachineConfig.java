package com.learn.mvc.fsm;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    /**
     * 状态机的初始状态和所有状态
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates().initial(States.DRAFT).states(EnumSet.allOf(States.class));
    }

    /**
     * 状态之间的转移规则
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
            .source(States.DRAFT).target(States.PUBLISH_TODO)
            .event(Events.ONLINE)
            .and()
            .withExternal()
            .source(States.PUBLISH_TODO).target(States.PUBLISH_DONE)
            .event(Events.PUBLISH)
            .and()
            .withExternal()
            .source(States.PUBLISH_DONE).target(States.DRAFT)
            .event(Events.ROLLBACK);
    }
}
