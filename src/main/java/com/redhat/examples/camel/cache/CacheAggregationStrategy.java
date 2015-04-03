package com.redhat.examples.camel.cache;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class CacheAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		// TODO Auto-generated method stub
		Object userObj = oldExchange.getIn().getBody();
		Object passObj = newExchange.getIn().getBody();
		// Set username thats set in old body as the InUserKey
		oldExchange.getIn().setHeader("InUserKey", userObj.toString());
		// Set password thats set in new body as the InPassKey
		oldExchange.getIn().setHeader("InPassKey", passObj.toString());
		return oldExchange;
	}
	
}