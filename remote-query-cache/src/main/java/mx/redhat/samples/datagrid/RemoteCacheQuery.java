package mx.redhat.samples.datagrid;

import java.io.IOException;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoSchemaBuilderException;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import mx.redhat.samples.datagrid.model.Persona;

public class RemoteCacheQuery 
{

	public static void main(String[] args) throws ProtoSchemaBuilderException, IOException 
	{
	    ConfigurationBuilder builder = new ConfigurationBuilder();
	    builder.addServer()
	    		.host("localhost")
	    		.port(11223)
	    	.marshaller(new ProtoStreamMarshaller()); // The Protobuf based marshaller is required for query capabilities
	    
		RemoteCacheManager remoteCacheManager = new RemoteCacheManager(builder.build());

		SerializationContext serCtx = ProtoStreamMarshaller.getSerializationContext(remoteCacheManager);
		
		// generate and register a Protobuf schema and marshallers based 
		// on CachedValue class
		ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
		
		String generatedSchemaFile = protoSchemaBuilder
			    .fileName("persona.proto")
			    .packageName("samples_datagrid")
			    .addClass(Persona.class)
			    .build(serCtx);
		
		assert(serCtx.canMarshall(Persona.class));
		
		// display the schema file
		System.out.println(generatedSchemaFile);
		
		RemoteCache<String, String> metadataCache = remoteCacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
		metadataCache.put("cachedvalue.proto", generatedSchemaFile);
		String errors = metadataCache.get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);
		if (errors != null) {
			throw new IllegalStateException("Some Protobuf schema files contain errors:\n" + errors);
		}
		
		RemoteCache<String, Persona> remoteCache = remoteCacheManager.getCache("test");
		
		Persona persona1 = new Persona("0002","Juan", "Perez", "Perez", "PEPJ080808");
		remoteCache.put(persona1.getPersona(), persona1);

	}

}
