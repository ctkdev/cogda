package com.cogda.errors

import com.cogda.domain.onboarding.Registration
import grails.validation.ValidationException
import org.springframework.validation.Errors

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 6/11/13
 * Time: 1:52 AM
 * To change this template use File | Settings | File Templates.
 */
class RegistrationException extends RuntimeException {
    Errors errors

    RegistrationException(String msg) {
        super(msg)
    }

    RegistrationException(String message, Throwable exception){
        super(message, exception)
    }
}
