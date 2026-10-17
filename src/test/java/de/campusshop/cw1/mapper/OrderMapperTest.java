package de.campusshop.cw1.mapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link OrderMapper} (Week 6).
 *
 * <p>TODO (student): test that a partner request maps to an {@code Order} with the external/customer
 * ids copied across, status {@code IMPORTED}, and {@code totalAmount} = Σ {@code quantity*unitPrice};
 * and that {@code toResponse} echoes the items. Remove the {@code @Disabled} annotations once written.
 */
class OrderMapperTest {

    private final OrderMapper mapper = new OrderMapper();

    @Test
    @Disabled("TODO: implement")
    void toDomainComputesTotalAndCopiesIds() {
        // TODO: arrange a PartnerOrderRequest, act with mapper.toDomain(...), assert with AssertJ
    }

    @Test
    @Disabled("TODO: implement")
    void toResponseEchoesItems() {
        // TODO
    }
}
