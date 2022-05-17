package ibm.eda.kc.orderms.infra.api;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import ibm.eda.kc.orderms.domain.OrderService;
import ibm.eda.kc.orderms.domain.ShippingOrder;

@RequestScoped
@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShippingOrderResource {
    
    @Inject
    OrderService serv;

    @POST
    @APIResponse(
        responseCode = "404",
        description = "Missing description",
        content = @Content(mediaType = "application/json"))
    @APIResponseSchema(value = ShippingOrder.class,
        responseDescription = "Create order with UUID",
        responseCode = "200")
    @Operation(
            summary = "Get JVM system properties for particular host",
            description = "Retrieves and returns the JVM system properties from the system "
            + "service running on the particular host.")
    public ShippingOrder createOrder(ShippingOrder order) {
        return serv.createOrder(order);
    }

    @GET
    public List<ShippingOrder> getAll() {
        return serv.getAllOrders();
    }

    @GET
    @Path("/{id}")
    public ShippingOrder getOrderByID(@PathParam("id") String id) {
        ShippingOrder order = serv.getOrderById(id);
        if (order == null) {
            throw new WebApplicationException("Order with id of " + id + " does not exist.", 404);
     
        }
        return order;
    }

    @PUT
    @Path("/cancel/{id}")
    public ShippingOrder cancelOrderByID(@PathParam("id") String id) {
        ShippingOrder order = serv.getOrderById(id);
        if (order == null) {
            throw new WebApplicationException("Order with id of " + id + " does not exist.", 404);
     
        }
        serv.cancelOrder(order);
        return order;
    }
}
