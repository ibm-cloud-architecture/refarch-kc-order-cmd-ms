package ibm.eda.kc.orderms.infra.events.reefer;

import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import ibm.eda.kc.orderms.domain.ShippingOrder;
import ibm.eda.kc.orderms.infra.events.order.OrderEventProducer;
import ibm.eda.kc.orderms.infra.repo.OrderRepository;

/**
 * Listen to the reefer topic and processes event from reefer service:
 * - reefer allocated event
 * - reefer unavailable event
 */
@ApplicationScoped
public class ReeferAgent {
    Logger logger = Logger.getLogger(ReeferAgent.class.getName());

    @Inject
    OrderRepository repo;

    @Inject
	public OrderEventProducer producer;
    
    @Incoming("reefers")
    public CompletionStage<Void> processReeferEvent(Message<ReeferEvent> messageWithReeferEvent){
        logger.info("Received reefer event for : " + messageWithReeferEvent.getPayload().reeferID);
        ReeferEvent oe = messageWithReeferEvent.getPayload();
        switch( oe.getType()){
            case ReeferEvent.REEFER_ALLOCATED_TYPE:
                ReeferEvent re=processReeferAllocatedEvent(oe);
                break;
            default:
                break;
        }
        return messageWithReeferEvent.ack();
    }

    /**
     * When order created, search for reefers close to the pickup location,
     * add them in the container ids and send an event as ReeferAllocated
     */
    public ReeferEvent processReeferAllocatedEvent( ReeferEvent re){
        ReeferAllocated ra = (ReeferAllocated)re.payload;
        ShippingOrder order = repo.findById(ra.orderID);
        if (order != null) {
            order.containerID = ra.reeferIDs;
            if (order.voyageID != null) {
                order.status = ShippingOrder.ASSIGNED_STATUS;
                producer.sendOrderUpdateEventFrom(order);
            }
            repo.updateOrder(order);
        } else {
            logger.warning(ra.orderID + " not found in repository");
        }
        
        return re;
    }
 
}
