package de.campusshop.cw1.controller;

import de.campusshop.cw1.service.OrderService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Web-layer tests for {@link OrderController} using {@link MockMvc} and a mocked service
 * (Week 6).
 *
 * <p>TODO (student): assert that a valid {@code POST /partner-orders} returns
 * {@code 201} and an invalid one returns {@code 400}.
 */
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService service;

    @Test
    @Disabled("TODO: implement")
    void validPostReturns201() {
        // TODO: stub service.importOrder(...), perform POST with a valid body, expect 201
    }

    @Test
    @Disabled("TODO: implement")
    void invalidPostReturns400() {
        // TODO: perform POST with an invalid body (e.g. empty items), expect 400
    }
}
