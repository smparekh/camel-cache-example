package com.redhat.examples.camel.cache;

import javax.ws.rs.*;

// This could be an interface if CAMEL-6014 is fixed.
@Path("/restservice/")
public class RestService {

	@PUT
	@Path("/addEntry/{key}/{value}")
	@Produces("application/xml")
	@Consumes("application/xml")
	public void addEntry(@PathParam("key") String key, @PathParam("value") String value) {
		
	}
	
	@PUT
	@Path("/getEntry/{key}")
	@Produces("application/xml")
	@Consumes("application/xml")
	public void getEntry(@PathParam("key") String key) {
		
	}
}