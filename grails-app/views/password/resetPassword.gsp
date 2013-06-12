<html>
    <head>
        <title><g:message code="forgotPassword.title"/></title>
        <meta name="layout" content="kickstart" />
        <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
        <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
        <g:set var="layout_noflashmessage"	value="${true}" scope="request"/>
    </head>

    <body>
        <section id="resetPasswordSection" class="first">

            <g:if test = "${flash.error}">
                <div class="alert alert-error" id="errorMessages" >

                </div>
            </g:if>

            <g:if test = "${flash.message}">
                <div class="alert alert-success" id="messages">
                      ${flash.message}
                </div>
            </g:if>

            <g:hasErrors bean="${command}">
                <div class="alert alert-error">
                    <g:renderErrors bean="${command}" as="list" />
                </div>
            </g:hasErrors>

            <div class="alert alert-info" id="resetPasswordMessage" >
                <g:message code="spring.security.resetPassword.description"/>
            </div>

            <g:form class = "form-horizontal" name="resetPasswordForm" autocomplete="off" novalidate="novalidate"
                action="resetPassword" controller="password">

                <g:hiddenField name="t" value="${token}"/>

                <fieldset class="form">
                <legend>
                    <g:message code="forgotPassword.title"/>
                </legend>

                <div class="control-group fieldcontain ${hasErrors(bean: resetPasswordCommandInstance, field: 'password', 'error')} required">
                    <label for="password" class="control-label">
                        <g:message code="springSecurity.login.resetPassword.label" default="New Password" /> <span class="required-indicator">*</span> </label>
                    <div class="controls">
                        <g:passwordField name="password" required="" value=""/>
                        <span class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group fieldcontain ${hasErrors(bean: resetPasswordCommandInstance, field: 'passwordTwo', 'error')} required">
                    <label for="passwordTwo" class="control-label">
                        <g:message code="springSecurity.login.resetPasswordTwo.label" default="Re-Type New Password" /> <span class="required-indicator">*</span>
                    </label>
                    <div class="controls">
                        <g:passwordField name="passwordTwo" required="" value=""/>
                        <span class="help-inline"></span>
                    </div>
                </div>
                <div class="form-actions">
                    <g:submitButton name="resetPasswordButton" class="btn btn-primary" value="${message(code: 'spring.security.resetPassword.resetButton', default: 'Reset Password')}" />
                </div>
            </g:form>
        </section>
    </body>
</html>