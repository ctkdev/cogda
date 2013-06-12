package com.cogda.domain.onboarding

import com.cogda.account.EmailSendReasonCode
import com.cogda.account.EmailSendStatusCode
import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.EmailConfirmationLog
import com.cogda.domain.admin.SystemEmailMessage
import com.cogda.domain.admin.SystemEmailMessageTemplate
import grails.gsp.PageRenderer
import grails.plugin.mail.MailService
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.grails.plugin.platform.events.EventMessage
import org.springframework.mail.MailMessage

/**
 * AccountActivationService
 * A service class encapsulates the core business logic of a Grails application
 */
class AccountActivationService {

    static transactional = true

    GrailsApplication grailsApplication
    PageRenderer groovyPageRenderer
    MailService mailService

    /**
     * Saves the registration and updates the status to AWAITING_ADMIN_APPROVAL.
     * TESTED: NO
     * @param registration
     * @return EmailConfirmationLog
     */
    public EmailConfirmationLog confirmEmailVerification(Registration registration){

        SystemEmailMessageTemplate emailVerificationMessage = SystemEmailMessageTemplate.findByTitle("VERIFIED_SUCCESSFULLY_EMAIL")

        registration.registrationStatus = RegistrationStatus.AWAITING_ADMIN_APPROVAL
        registration.save()

        Map messageParameters = [:]
        messageParameters.appName = grailsApplication.config.application.name

        // Apply the dynamic values to the body of the email template
        String messageBody = emailVerificationMessage.writeMessageOutput(messageParameters)

        String emailBody = groovyPageRenderer.render(view:'/email/confirmationLayout', model:[body:messageBody])

        EmailConfirmationLog confLog = new EmailConfirmationLog()
        confLog.emailSendReason = EmailSendReasonCode.EMAIL_VERIFIED_SUCCESSFULLY
        confLog.emailTo = registration.emailAddress
        confLog.emailFrom = grailsApplication.config.grails.mail.default.from
        confLog.emailSubject = emailVerificationMessage.subject
        confLog.emailBody = messageBody

        confLog.save()

        registration.addToEmailConfirmationLogs(confLog)

        event('sendEmailVerifiedMessage', confLog)

        return confLog
    }

    @grails.events.Listener(topic="sendEmailVerifiedMessage")
    def sendEmailConfirmedMessage(EventMessage eventMessage){
        EmailConfirmationLog confLog = (EmailConfirmationLog)eventMessage.data
        sendEmailVerified(confLog)
    }

    /**
     * Sends the email that tells the user that their email was verified.
     * TESTED: NO
     */
    def sendEmailVerified(EmailConfirmationLog confLog){
        MailMessage message
        try {
            message = mailService.sendMail {
                to confLog.emailTo
                from confLog.emailFrom
                subject confLog.emailSubject
                html confLog.emailBody
            }
            confLog.emailSendStatus = EmailSendStatusCode.SUCCESS
            confLog.save()
        } catch (Throwable t) {
            confLog.emailErrors = t.toString()
            confLog.emailSendStatus = EmailSendStatusCode.FAILURE
            confLog.save()
            log.error("Mail send failed", t)
        }
    }

    /**
     * Send an email confirmation based on the
     * passed in email address in the args and
     * the Registration object.
     *
     * @param args Map of arguments:
     * emailAddress - Required, email address
     *
     * @return AccountActivationEmailConfirmation
     */
    public EmailConfirmationLog prepareEmailVerification(Registration registration, String emailVerificationUrl) {

        SystemEmailMessageTemplate emailVerificationMessage = SystemEmailMessageTemplate.findByTitle("INITIAL_ACCOUNT_ACTIVATION_EMAIL")

        assert emailVerificationMessage, "Account Activation Email Message Was Not Found!"

        Map messageParameters = [:]
        messageParameters.appName = grailsApplication.config.application.name
        messageParameters.activationUrl = emailVerificationUrl

        // Apply the dynamic values to the body of the email template
        String messageBody = emailVerificationMessage.writeMessageOutput(messageParameters)

        String emailBody = groovyPageRenderer.render(view:'/email/confirmationLayout', model:[body:messageBody])

        EmailConfirmationLog confLog = new EmailConfirmationLog()
        confLog.emailSendReason = EmailSendReasonCode.INITIAL_ACCOUNT_VERIFICATION_EMAIL
        confLog.emailTo = registration.emailAddress
        confLog.emailFrom = grailsApplication.config.grails.mail.default.from
        confLog.emailSubject = emailVerificationMessage.subject
        confLog.emailBody = messageBody

        confLog.save()

        registration.addToEmailConfirmationLogs(confLog)

        // Fire the sendEmailConfirmation event method asynchronously after the successful
        // save of EmailConfirmationLog for email processing.
        event('sendEmailVerificationMessage', confLog)

        return confLog
    }

    @grails.events.Listener(topic="sendEmailVerificationMessage")
    def sendFirstEmailVerification(EventMessage eventMessage){
        EmailConfirmationLog confLog = (EmailConfirmationLog)eventMessage.data
        sendEmailVerification(confLog)
    }

    /**
     * Sends the email verification.
     */
    def sendEmailVerification(EmailConfirmationLog confLog){
        MailMessage message
        try {
            message = mailService.sendMail {
                to confLog.emailTo
                from confLog.emailFrom
                subject confLog.emailSubject
                html confLog.emailBody
            }
            confLog.emailSendStatus = EmailSendStatusCode.SUCCESS
            confLog.save()
        } catch (Throwable t) {
            confLog.emailErrors = t.toString()
            confLog.emailSendStatus = EmailSendStatusCode.FAILURE
            confLog.save()
            log.error("Mail send failed", t)
        }
    }
}
