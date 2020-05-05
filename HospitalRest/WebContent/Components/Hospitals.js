$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
//Save
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateHospitalForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

//Valid
	var type = ($("#hidHospitalIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "HospitalsAPI",
		type : type,
		data : $("#formHospital").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onHospitalSaveComplete(response.responseText, status);
		}
	});
// $("#formHospital").submit();
});
function onHospitalSaveComplete(response, status) {
	var resultSet = JSON.parse(response);
	if (resultSet.status.trim() == "success") {
		$("#alertSuccess").text("Successfully saved.");
		$("#alertSuccess").show();
		$("#divHospitalsGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error") {
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
	}

	else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidHospitalIDSave").val("");
	$("#formHospital")[0].reset();

}

// UPDATE
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidHospitalIDSave").val(
					$(this).closest("tr").find('#hidHospitalIDUpdate').val());
			$("#hospitalCode").val($(this).closest("tr").find('td:eq(0)').text());
			$("#hospitalName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#hospitalAdress").val($(this).closest("tr").find('td:eq(2)').text());
			$("#hospitalPhoneNo").val($(this).closest("tr").find('td:eq(3)').text());
		});

// Remove
$(document).on("click", ".btnRemove", function(event) 
		{  
			$.ajax(  
			{   
				url : "HospitalsAPI",   
				type : "DELETE",   
				data : "HospitalID=" + $(this).data("hospitalid"),   
				dataType : "text",   
				complete : function(response, status)   
				{    
					onHospitalDeleteComplete(response.responseText, status);   
				}  
			}); 
		}); 

		function onHospitalDeleteComplete(response, status) 
		{  
			if (status == "success")  
			{   
				var resultSet = JSON.parse(response); 

				if (resultSet.status.trim() == "success")   
				{    
					$("#alertSuccess").text("Successfully deleted.");    
					$("#alertSuccess").show(); 
				
					$("#divHospitalsGrid").html(resultSet.data);   
				} else if (resultSet.status.trim() == "error")   
				{    
					$("#alertError").text(resultSet.data);    
					$("#alertError").show();   
				}

			} else if (status == "error")  
			{   
				$("#alertError").text("Error while deleting.");   
				$("#alertError").show();  
			} else  
			{   
				$("#alertError").text("Unknown error while deleting..");   
				$("#alertError").show();  
			}
		}
// CLIENTMODE
function validateHospitalForm() {
	// CODE
	if ($("#hospitalCode").val().trim() == "") {
		return "Insert Hospital Code.";
	}
	// NAME
	if ($("#hospitalName").val().trim() == "") {
		return "Insert Hospital Name.";
	}

	// ADDRESS
	if ($("#hospitalAdress").val().trim() == "") {
		return "Insert Hospital Adress.";
	}
	// PHONENO
	if ($("#hospitalPhoneNo").val().trim() == "") {
		return "Insert Hospital Phone No.";
	}
	return true;
}