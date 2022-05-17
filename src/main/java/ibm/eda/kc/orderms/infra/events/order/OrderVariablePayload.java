package ibm.eda.kc.orderms.infra.events.order;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use=Id.DEDUCTION, defaultImpl = OrderCreatedEvent.class)
@JsonSubTypes({
    @Type(value=OrderCreatedEvent.class,name="OrderCreatedEvent"),
    @Type(value=OrderUpdatedEvent.class, name="OrderUpdatedEvent")})
public abstract class OrderVariablePayload {

}
