<div class="field template" id="editEmail">
	<input type="radio" name="primary" class="primaryRadio" checked="checked">
	<div id="emailShow" class="emailText showMode showMe"></div>
	<div class="field hideMe editMode input-append">
		<input id="emailAddress" type="text" class="emailInput input-xlarge" placeholder="bsmith@example.com">
		<button class="btn btn-primary saveEmail" type="button" onclick="saveEmail(event)"><i class="icon-save"></i> Save</button>
	</div>	
	<a class="btn showMe showMode" onclick="editEmail(event)" id="emailEditBtn"><i class="icon-edit"></i> Edit</a>
</div>