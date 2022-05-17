package ibm.eda.kc.orderms.infra.events.voyage;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * 
 */
@RegisterForReflection
public class VoyageAllocated extends VoyageVariablePayload {
	public String orderID;

	public VoyageAllocated() {}

	public VoyageAllocated(String oid) {
		this.orderID = oid;
	}

}
