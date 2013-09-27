var activeSection = 0;
var sectionCount = 0;
$(function() {
	$.ajax({
		url : "rest/initialization",
		type : "GET",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data, status, jqXHR) {
			if (jqXHR.status == 206) {
				redirect(data.path);
			}
			else {
				preprocessConfig(data);		
				
				var html, json, template;
				template = $('.template').val();
				json = data;
				html = Mustache.to_html(template, json).replace(/^\s*/mg, '');
				
				$("#box").html(html);
			}
		}
	});
});	

function preprocessConfig(data) {
	sectionCount = data.sections.length;			
	data.sections[0].first = true;
	for (i = 0; i < data.sections.length; i++){
		data.sections[i].id = i;
	}
}

function nextSection() {
	$("#section-"+activeSection).hide();
	activeSection += 1;
	$("#section-"+activeSection).show();
	if (activeSection + 1 >= sectionCount) {
		$("#next-section").attr("disabled",true);
		$("#save").show();
	}
	if (activeSection > 0) {
		$("#prev-section").removeAttr("disabled");
	}
}

function prevSection() {
	$("#section-"+activeSection).hide();
	activeSection -= 1;
	$("#section-"+activeSection).show();
	if (activeSection < sectionCount) {
		$("#next-section").removeAttr("disabled");
	}
	if (activeSection == 0) {
		$("#prev-section").attr("disabled",true);
	}
}

function save() {	
	var params = [];
	$("input").each(function(i, input){
		params.push({ "name": $(input).attr("id"), "value": $(input).val() });
	});
	
	var json = {};
	json.sections = [{"name": "whatever", "params": params}];
	
	$.ajax({
		url : "rest/initialization",
		type : "PUT",
		data : JSON.stringify(json),
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			redirect(data.path);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#error").html(jqXHR.responseText);
		}
	});
}

function redirect(path) {
	if (path.indexOf("../") == 0) {
		window.location.href = window.location.href + path;
	}
	else {
		window.location.href = path;
	}
}