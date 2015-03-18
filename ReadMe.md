Camel Cache Example with Rest
=============================
Example project on how to use the camel-cache component.

Build
-----
mvn clean install

Install
-------
*Required features to install on JBoss Fuse:*
- camel-cache
- camel-cxf

osgi:install -s mvn:com.redhat.examples/camel-cache-example/1.0.0-SNAPSHOT

Use
---
*Create new REST SOAP UI Project*
wadl location: http://localhost:9001/rest?_wadl

*Add a cache entry:*
Use the addEntry function exposed by the rest service.
1. Add the key string to the key param
2. Add the value string to the value param
3. Execute the request
4. The value is now added with the key specified

*Retrieve the cache entry:*
Use the getEntry function exposed b the rest service.
1.  Add the key string to the key param
2.  If value exists for key it is returned in the response...
3.  Else, a generic error string is returned
2.  Execute the request
