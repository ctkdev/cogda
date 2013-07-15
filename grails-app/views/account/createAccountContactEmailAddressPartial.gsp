<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactEmailAddressAddForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactEmailAddressAddForm['accountContactEmailAddress.emailAddress'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactEmailAddress.emailAddress"
                               data-ng-model="accountContactEmailAddress.emailAddress"
                               placeholder="${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}"/>
                    </div>
                </div>

                <div class="control-group fieldcontain" data-ng-class="{error: accountContactEmailAddressAddForm['accountContactEmailAddress.primaryEmailAddress'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactEmailAddresses.primary.label')}
                    </label>
                    <div class="controls">
                        <input type="checkbox"
                               name="accountContactEmailAddress.primaryEmailAddress"
                               data-ng-model="accountContactEmailAddress.primaryEmailAddress"
                               />
                    </div>
                </div>


                <div class="form-actions">
                    <button type="button" class="btn btn-danger" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
                    <button type="button" class="btn btn-primary" data-ng-click="saveAccountContactEmailAddress(accountContactEmailAddress)"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
                </div>
            </fieldset>
        </form>
    </div>
</div>
