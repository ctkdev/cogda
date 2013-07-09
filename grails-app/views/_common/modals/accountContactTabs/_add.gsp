<div class="tab-pane" id="createContactsContent">
    <g:hiddenField name="accountId" value="${accountInstance?.id}"/>

    <div class="control-group fieldcontain ${hasErrors(bean: accountContactInstance, field: 'firstName', 'error')} required">
        <label for="firstName" class="control-label"><g:message code="accountContact.firstName.label" default="First Name" /><span class="required-indicator">*</span></label>
        <div class="controls">
            <g:textField name="firstName" required="" value="${accountContactInstance?.firstName}"/>
            <span class="help-inline">${hasErrors(bean: accountContactInstance, field: 'firstName', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: accountContactInstance, field: 'middleName', 'error')} ">
        <label for="middleName" class="control-label"><g:message code="accountContact.middleName.label" default="Middle Name" /></label>
        <div class="controls">
            <g:textField name="middleName" value="${accountContactInstance?.middleName}"/>
            <span class="help-inline">${hasErrors(bean: accountContactInstance, field: 'middleName', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: accountContactInstance, field: 'lastName', 'error')} required">
        <label for="lastName" class="control-label"><g:message code="accountContact.lastName.label" default="Last Name" /><span class="required-indicator">*</span></label>
        <div class="controls">
            <g:textField name="lastName" required="" value="${accountContactInstance?.lastName}"/>
            <span class="help-inline">${hasErrors(bean: accountContactInstance, field: 'lastName', 'error')}</span>
        </div>
    </div>

    <div class="add">
        <div class="btn btn-danger add-field" id="closeCreateContactTab"><i class="icon-remove-circle"></i> Cancel</div>
        <div class="btn btn-success add-field" onclick="saveAccountContact();"><i class="icon-plus"></i> Create Contact</div>

    </div>
</div>