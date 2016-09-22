package mx.redhat.samples.datagrid;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;

public class RemoteCacheQuery 
{

	public static void main(String[] args) 
	{
	    ConfigurationBuilder builder = new ConfigurationBuilder();
	    builder.addServer()
	    	.host("localhost")
	    	.port(11223)
	    	.marshaller(new ProtoStreamMarshaller()); // The Protobuf based marshaller is required for query capabilities
	    
		RemoteCacheManager remoteCacheManager = new RemoteCacheManager(builder.build());

	}

}
