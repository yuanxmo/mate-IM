package com.mate.im.base.statemachine;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.mate.im.base.exception.BizException;
import com.mate.im.base.exception.BizExceptionCode;

import java.util.Map;

public class BaseStateMachine<STATE, EVENT> implements StateMachine<STATE, EVENT> {
    private Map<String, STATE> stateTransitions = Maps.newHashMap();

    protected void putTransition(STATE origin, EVENT event, STATE target) {
        stateTransitions.put(Joiner.on("_").join(origin, event), target);
    }

    @Override
    public STATE transition(STATE state, EVENT event) {
        STATE target = stateTransitions.get(Joiner.on("_").join(state, event));
        if (target == null) {
            throw new BizException(
                    "state = " + state + ", event = " +
                            event, BizExceptionCode.STATE_MACHINE_TRANSITION_FAILED
            );
        }
        return target;
    }
}
