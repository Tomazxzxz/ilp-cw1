package de.campusshop.cw1.service;

import de.campusshop.cw1.mapper.OrderMapper;
import de.campusshop.cw1.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

/**
 * Unit tests for {@link OrderService} with a <b>mocked</b> {@link OrderRepository} (Week 6).
 *
 * <p>TODO (student): use Mockito to stub the repository and verify the service behaviour. The mock
 * and a fixed {@link Clock} are wired for you below.
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    private OrderService service;

    private final Clock clock = Clock.fixed(Instant.parse("2026-05-01T12:00:00Z"), ZoneOffset.UTC);

    @BeforeEach
    void setUp() {
        service = new OrderService(repository, new OrderMapper(), clock);
    }

    @Test
    @Disabled("TODO: implement")
    void importOrderPersistsAndReturnsResponse() {
        // TODO: stub repository.findAll()/save(...), call service.importOrder(...), assert the response
    }

    @Test
    @Disabled("TODO: implement")
    void importOrderRejectsDuplicatePartnerOrderId() {
        // TODO: arrange an existing order with the same partnerOrderId, expect a DuplicateOrderException
    }

    @Test
    @Disabled("TODO: implement")
    void findByIdThrowsWhenMissing() {
        // TODO: expect OrderNotFoundException
    }
}
