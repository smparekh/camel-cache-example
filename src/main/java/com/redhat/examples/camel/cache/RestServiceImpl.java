package com.redhat.examples.camel.cache;

import java.util.List;

import javax.ws.rs.core.PathSegment;

import org.apache.camel.Exchange;
import org.apache.camel.component.cache.CacheConstants;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;



public class RestServiceImpl {
	public RestServiceImpl() {

	}

	public void addEntry(Exchange exchange) {
		String pathString = exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class);
		
		List<PathSegment> pathList = JAXRSUtils.getPathSegments(pathString, false);
		exchange.getIn().setHeader(CacheConstants.CACHE_OPERATION, CacheConstants.CACHE_OPERATION_ADD);
		exchange.getIn().setHeader(CacheConstants.CACHE_KEY, pathList.get(2).toString());
		exchange.getIn().setBody(pathList.get(3).toString());
	}
	
	public void addedEntry(Exchange exchange) {
		String rString = "Added entry for key: " + exchange.getIn().getHeader("key").toString() + ".";
		exchange.getOut().setBody( rString );
	}

	public void getEntry(Exchange exchange) {
		String pathString = exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class);
		
		List<PathSegment> pathList = JAXRSUtils.getPathSegments(pathString, false);
		
		exchange.getIn().setHeader(CacheConstants.CACHE_OPERATION, CacheConstants.CACHE_OPERATION_GET);
		exchange.getIn().setHeader(CacheConstants.CACHE_KEY, pathList.get(2).toString());
	}
	
	public void gotEntry(Exchange exchange) {
		String rString = "Got value " + exchange.getIn().getBody().toString() + ".";
		exchange.getOut().setBody( rString );
	}
	
	public void notFound(Exchange exchange) {
		String rString = "Value for key: " + exchange.getIn().getHeader("key").toString() + " not found.";
		exchange.getOut().setBody( rString );
	}
	
	public void setCachedUserKey(Exchange exchange) {
		String inUser = exchange.getIn().getHeader("InUserKey").toString();
		exchange.getIn().setHeader(CacheConstants.CACHE_OPERATION, CacheConstants.CACHE_OPERATION_GET);
		exchange.getIn().setHeader(CacheConstants.CACHE_KEY, inUser);
	}
	
	public void setCachedPassKey(Exchange exchange) {
		String inPass = exchange.getIn().getHeader("InPassKey").toString();
		exchange.getIn().setHeader(CacheConstants.CACHE_OPERATION, CacheConstants.CACHE_OPERATION_GET);
		exchange.getIn().setHeader(CacheConstants.CACHE_KEY, inPass);
	}
}
