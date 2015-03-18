package com.redhat.examples.camel.cache;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cache.CacheConstants;

public class CacheRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		// Set up Cache
		from("cache://MyApplicationCache?timeToLiveSeconds=3600")
			.log("Cache Initialized")
			.to("mock:endpoint");
		
		// CXFRS Entry Point
		/*from("jetty:http://localhost:9001?matchOnUriPrefix=true")
			.to("cxfrs:bean:rsServer");*/
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
	}
}