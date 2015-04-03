package com.redhat.examples.camel.cache;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cache.CacheConstants;
import com.redhat.examples.camel.cache.CacheAggregationStrategy;

public class CacheRouteBuilder extends RouteBuilder {

	private CacheAggregationStrategy CAS = new CacheAggregationStrategy();
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		// Set up Cache
		from("cache://MyApplicationCache?eternal=true")
			.to("mock:endpoint");
		
		// CXFRS Entry Point
		from("cxfrs:bean:rsServer?bindingStyle=SimpleConsumer")
			.log("GET Request Received...")
			.choice()
				.when(header("operationName").isEqualTo("addEntry"))
					.log("In add entry choice")
					.log("${headers}")
					.to("bean:restImplBean?method=addEntry")
					.to("cache://MyApplicationCache")
					.to("bean:restImplBean?method=addedEntry")
				.when(header("operationName").isEqualTo("getEntry"))
					.log("In get entry choice")
					.to("bean:restImplBean?method=getEntry")
					.to("cache://MyApplicationCache")
					.log("${headers}")
					.choice()
						.when(header(CacheConstants.CACHE_ELEMENT_WAS_FOUND).isNull())
				        .to("bean:restImplBean?method=notFound")
				        .otherwise()
				        .to("bean:restImplBean?method=gotEntry")
				    .end()
			.end();
		
		/* Workaround for accessing cache directly from other bundles shut down the cache 
		  when they are shutdown
		  see: https://issues.apache.org/jira/browse/CAMEL-7174 
		*/
		from("direct-vm:CacheKeyCheck")
			.log("Client CHECK Request Recevied...")
			.to("cache://MyApplicationCache");
		
		from("direct-vm:CacheChangeValue")
			.log("Client CHANGE Request Received...")
			.to("cache://MyApplicationCache");
		
		// Expects InUserKey and InPassKey Headers from client, returns cached values in the same headers
		from("direct-vm:GetCachedUserAndPass")
			.log("Client Get User and Pass Received...")
			.to("bean:restImplBean?method=setCachedUserKey")
			.to("cache://MyApplicationCache")
			.to("bean:restImplBean?method=setCachedPassKey")
			.enrich("cache://MyApplicationCache", CAS);
	}
}