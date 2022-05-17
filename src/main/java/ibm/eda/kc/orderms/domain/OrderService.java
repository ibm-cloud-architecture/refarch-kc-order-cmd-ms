package ibm.eda.kc.orderms.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import ibm.eda.kc.orderms.infra.events.order.OrderEvent;
import ibm.eda.kc.orderms.infra.events.order.OrderEventProducer;
import ibm.eda.kc.orderms.infra.repo.OrderRepository;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;


@ApplicationScoped
public class OrderService {

    @Inject
	public OrderRepository repository;

	@Inject
	public OrderEventProducer producer;

    public List<ShippingOrder> getAllOrders() {
		return repository.getAll();
	}


    @Transactional
    public ShippingOrder createOrder(ShippingOrder order) {
        if (order.orderID == null) {
            order.orderID = UUID.randomUUID().toString();
        }
        if (order.creationDate == null) {
			order.creationDate = LocalDate.now().toString();
		}
        order.status = ShippingOrder.PENDING_STATUS;
		order.updateDate= order.creationDate;
        repository.addOrder(order);
		producer.sendOrderCreatedEventFrom(order);
        
        return order;
    }


    public ShippingOrder getOrderById(String id) {
        return repository.findById(id);
    }


    @Transactional
    public void cancelOrder(ShippingOrder order) {
        order.status = ShippingOrder.CANCELLED_STATUS;
        producer.sendOrderUpdateEventFrom(order);
        repository.updateOrder(order);
    }
}
