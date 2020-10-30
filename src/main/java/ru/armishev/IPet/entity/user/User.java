package ru.armishev.IPet.entity.user;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;

@Service
@SessionScope
public class User implements IUser {
    String session_id = RequestContextHolder.currentRequestAttributes().getSessionId();
}
