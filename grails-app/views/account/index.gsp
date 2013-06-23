
<%@ page import="com.cogda.multitenant.Account" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}" />
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="accountPage"/>

</head>

<body>

<content tag="header">
    <!-- Empty Header -->
</content>

<div id="MenuRow" class="row">
    <div class="span12">
        &nbsp;
    </div>
</div>

<section id="list-account" class="first">
    <table class="table table-bordered" id="accountList">
        <thead>
        <tr>

            %{--<th>${message(code: 'account.id.label', default: 'Id')}</th>--}%

            %{--<th>${message(code: 'account.version.label', default: 'Version')}</th>--}%

            <th>${message(code: 'account.accountName.label')}</th>

            <th>${message(code: 'account.accountCode.label')}</th>

            <th>${message(code: 'account.accountType.label')}</th>

            <th>${message(code: 'account.primaryContactName.label')}</th>

            <th>${message(code: 'account.primaryEmailAddress.label')}</th>

            <th>${message(code: 'account.primaryPhoneNumber.label')}</th>

            <th>${message(code: 'account.dateCreated.label')}</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</section>

<g:render template="/_common/modals/account/add"/>

</body>
</html>
