package com.github.MicroBlog.commons;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1261165248840341983L;

    protected static MessageSourceAccessor message = SpringSecurityMessageSource.getAccessor();

    public UnauthorizedException() {
        super(message.getMessage("AbstractAccesDecisionManager.accesDenied", "Acces is denied"));

    }

    public UnauthorizedException (String message) {
        super(message);
    }
}

