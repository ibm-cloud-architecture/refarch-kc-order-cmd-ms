package ibm.eda.kc.orderms.infra.events.order;

import java.util.Date;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderCreatedEvent extends OrderVariablePayload {
    public String destinationCity;
	public String pickupCity;
	public String creationDate;

    public OrderCreatedEvent(){}

    public OrderCreatedEvent(String destinationCity, String pickupCity) {
        this.destinationCity = destinationCity;
        this.pickupCity = pickupCity;
        this.creationDate = new Date().toString();
    }

    
}
