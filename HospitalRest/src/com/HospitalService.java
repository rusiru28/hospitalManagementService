package com;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.parser.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Hospital;


@Path("/hospitals")
public class HospitalService {
	Hospital hospitalObj = new Hospital();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readHospital()
	{
	return hospitalObj.readHospital();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertHospital(@FormParam("hospitalCode") String hospitalCode,
			@FormParam("hospitalName") String hospitalName,
			@FormParam("hospitalAdress") String hospitalAdress,
			@FormParam("hospitalPhoneNo") String hospitalPhoneNo)
	{
	 String output = hospitalObj.insertHospital(hospitalCode, hospitalName, hospitalAdress, hospitalPhoneNo);
	return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateHospital(String HospitalData)
	{
	
	 JsonObject itemObject = new JsonParser().parse(HospitalData).getAsJsonObject();
	
	 String HospitalID = itemObject.get("HospitalID").getAsString();
	 String hospitalCode = itemObject.get("hospitalCode").getAsString();
	 String hospitalName = itemObject.get("hospitalName").getAsString();
	 String hospitalAdress = itemObject.get("hospitalAdress").getAsString();
	 String hospitalPhoneNo = itemObject.get("hospitalPhoneNo").getAsString();
	 String output = hospitalObj.updateHospital(HospitalID,hospitalCode,hospitalName,hospitalAdress,hospitalPhoneNo);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteHospital(String HospitalData)
	{

	 Document doc = Jsoup.parse(HospitalData, "", Parser.xmlParser());

	 String HospitalID = doc.select("HospitalID").text();
	 String output = hospitalObj.deleteHospital(HospitalID);
	return output;
	}
	
}
		


