package de.campusshop.cw1.mapper;

import de.campusshop.cw1.dto.OrderResponse;
import de.campusshop.cw1.dto.PartnerOrderRequest;
import de.campusshop.cw1.model.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Manual mapper that keeps transformation logic out of the controller and service.
 * (Week 2 shows both manual and MapStruct mappers; a manual mapper is clearer here
 * because the total is computed.)
 *
 * <p>TODO (student): implement the two mapping methods.
 */
@Component
public class OrderMapper {

    /**
     * Build a new internal {@link Order} from a validated partner request:
     * copy {@code partnerOrderId} → {@code externalOrderId} and {@code buyer.id} → {@code customerId},
     * map each item, compute {@code totalAmount} as the sum of {@code quantity * unitPrice},
     * set status {@code IMPORTED} and use the supplied {@code orderId}/{@code createdAt}.
     */
    public Order toDomain(PartnerOrderRequest request, String orderId, Instant createdAt) {
        // TODO: implement
        throw new UnsupportedOperationException("TODO: implement OrderMapper.toDomain");
    }

    /** Map an internal {@link Order} to its API response DTO. */
    public OrderResponse toResponse(Order order) {
        // TODO: implement
        throw new UnsupportedOperationException("TODO: implement OrderMapper.toResponse");
    }
}
