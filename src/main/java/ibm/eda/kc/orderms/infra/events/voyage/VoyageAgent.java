package ibm.eda.kc.orderms.infra.events.voyage;

import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import ibm.eda.kc.orderms.domain.ShippingOrder;
import ibm.eda.kc.orderms.infra.events.order.OrderEventProducer;
import ibm.eda.kc.orderms.infra.events.reefer.ReeferEvent;
import ibm.eda.kc.orderms.infra.repo.OrderRepository;

/**
 * Listen to voyages topic to process voyage allocation
 */
@ApplicationScoped
public class VoyageAgent {
    
    Logger logger = Logger.getLogger(VoyageAgent.class.getName());

    @Inject
    OrderRepository repo;

    @Inject
	public OrderEventProducer producer;
    
    @Incoming("voyages")
    public CompletionStage<Void> processVoyageEvent(Message<VoyageEvent> messageWithVoyageEvent){
        logger.info("Received reefer event for : " + messageWithVoyageEvent.getPayload().voyageID);
        VoyageEvent oe = messageWithVoyageEvent.getPayload();
        switch( oe.getType()){
            case ReeferEvent.REEFER_ALLOCATED_TYPE:
            VoyageEvent re=processVoyageAssignEvent(oe);
                break;
            default:
                break;
        }
        return messageWithVoyageEvent.ack();
    }

    @Transactional
    public VoyageEvent processVoyageAssignEvent(VoyageEvent ve) {
        VoyageAllocated ra = (VoyageAllocated)ve.payload;
        ShippingOrder order = repo.findById(ra.orderID);
        if (order != null) {
            order.voyageID = ve.voyageID;     
            if (order.containerID != null) {
                order.status = ShippingOrder.ASSIGNED_STATUS;
                producer.sendOrderUpdateEventFrom(order);
            }
            repo.updateOrder(order);
        } else {
            logger.warning(ra.orderID + " not found in repository");
        }
        
        return ve;
    }
}
