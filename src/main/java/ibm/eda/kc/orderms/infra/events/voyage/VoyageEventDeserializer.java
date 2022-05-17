package ibm.eda.kc.orderms.infra.events.voyage;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class VoyageEventDeserializer extends ObjectMapperDeserializer<VoyageEvent> {
    public VoyageEventDeserializer(){
        // pass the class to the parent.
        super(VoyageEvent.class);
    }
    
}
