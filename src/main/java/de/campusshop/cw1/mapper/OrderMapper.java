package de.campusshop.cw1.mapper;

import de.campusshop.cw1.dto.OrderItemResponse;
import de.campusshop.cw1.dto.OrderResponse;
import de.campusshop.cw1.dto.PartnerOrderRequest;
import de.campusshop.cw1.model.Order;
import de.campusshop.cw1.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.stream.Collectors;
import java.util.List;
import de.campusshop.cw1.model.OrderItem;

/**
 * Manual mapper that keeps transformation logic out of the controller and service.
 * (Week 2 shows both manual and MapStruct mappers; a manual mapper is clearer here
 * because the total is computed.)
 *
 * <p>
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
        List<OrderItem> items = request.items().stream().map(item -> new OrderItem(item.sku(), item.name(), item.quantity(), item.unitPrice())).collect(Collectors.toList());
        BigDecimal total = items.stream().map(item -> item.lineTotal()).reduce(BigDecimal.ZERO, (tot, next) -> tot.add(next));
        return new Order(orderId, request.partnerOrderId(), request.buyer().id(), OrderStatus.IMPORTED, total, createdAt, items);
    }

    /** Map an internal {@link Order} to its API response DTO. */
    public OrderResponse toResponse(Order order) {
        return new OrderResponse(order.orderId(), order.externalOrderId(), order.customerId(), order.status(),order.totalAmount(),order.createdAt(),order.items().stream().map(item -> new OrderItemResponse(item.sku(), item.name(), item.quantity(),item.unitPrice())).collect(Collectors.toList()));
    }
}
