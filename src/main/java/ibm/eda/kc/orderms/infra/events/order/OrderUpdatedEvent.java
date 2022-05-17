package ibm.eda.kc.orderms.infra.events.order;

import java.time.LocalDate;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderUpdatedEvent extends OrderVariablePayload  {
    public String voyageID;
    public String reeferIDs;
    public String updateDate;

    public OrderUpdatedEvent(){
        updateDate = LocalDate.now().toString();
    }
}
