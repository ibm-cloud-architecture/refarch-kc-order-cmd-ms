package ibm.eda.kc.orderms.infra.events.voyage;

import ibm.eda.kc.orderms.infra.events.EventBase;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class VoyageEvent extends EventBase {
    public static final String TYPE_VOYAGE_ASSIGNED = "VoyageAssigned"; // from voyage ms
    public static final String TYPE_VOYAGE_NOT_FOUND = "VoyageNotFound"; // from voyage ms
    public String voyageID;
    public VoyageVariablePayload payload;

   public VoyageEvent(){} 
}
