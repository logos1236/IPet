package ru.armishev.IPet.entity.event;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class EventFactory {
    @Autowired
    Starvation starvation;

    @Autowired
    Boring boring;

    public List<IEvent> getEventList() {
        List<IEvent> list = new ArrayList<>();

        list.add(starvation);
        list.add(boring);

        return list;
    }
}
