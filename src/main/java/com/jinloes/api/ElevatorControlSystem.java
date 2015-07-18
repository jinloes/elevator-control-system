package com.jinloes.api;

import com.jinloes.model.Call;

/**
 * Created by jinloes on 7/17/15.
 */
public interface ElevatorControlSystem {

    void call(Call call);

    void addDestination(int floor);
}
