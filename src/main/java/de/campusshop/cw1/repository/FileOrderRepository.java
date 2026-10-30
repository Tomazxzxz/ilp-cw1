package de.campusshop.cw1.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.campusshop.cw1.config.StorageProperties;
import de.campusshop.cw1.exception.StorageException;
import de.campusshop.cw1.model.Order;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * JSON-file backed {@link OrderRepository}.
 *
 * <p>TODO (student): implement file persistence with Jackson {@code ObjectMapper} and
 * {@code Path}/{@code Files} (Week 5; CW1 spec §5). Store <b>all orders in ONE file as a single
 * JSON array</b> — not one file per order. Suggested approach: hold the orders in the in-memory
 * {@code ordersById} map for fast lookup and rewrite the whole array after each mutation, under the
 * {@code lock}. Create the parent directory and an empty {@code []} file if missing; write
 * atomically (temp file + {@code Files.move(..., ATOMIC_MOVE, REPLACE_EXISTING)}) so a crash never
 * leaves a half-written store (Appendix D.2). Date serialisation is handled by the {@code ObjectMapper}
 * configuration (Appendix D.3).
 */
@Repository
public class FileOrderRepository implements OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(FileOrderRepository.class);

    private final ObjectMapper objectMapper;
    private final Path storagePath;
    private final Map<String, Order> ordersById = new LinkedHashMap<>();
    private final Object lock = new Object();

    public FileOrderRepository(ObjectMapper objectMapper, StorageProperties properties) {
        this.objectMapper = objectMapper;
        this.storagePath = Path.of(properties.file());
    }

    /** Initialise the store: create parent dir + empty file if needed, then load existing orders. */
    @PostConstruct
    public void load() {
        try {
            Files.createDirectories(storagePath.getParent());
            if (Files.exists(storagePath) == false) {
                Files.writeString(storagePath, "[]");
            }
            List<Order> orders = objectMapper.readValue(storagePath.toFile(), new TypeReference<List<Order>>() {});
            for (Order order : orders) {
                ordersById.put(order.orderId(), order);
            }
        } catch (IOException e) {
            throw new StorageException("could not initialise order store at " + storagePath, e);
        }
    }

    @Override
    public Order save(Order order) {
        ordersById.put(order.orderId(),order);
        try {
            objectMapper.writeValue(storagePath.toFile(),new ArrayList<>(ordersById.values()));
        } catch (IOException e) {
            throw new StorageException("couldn't save order", e);
        }
        return order;
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(ordersById.get(orderId));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(ordersById.values());
    }

    @Override
    public List<Order> findByCustomerId(String customerId) {
        return ordersById.values().stream().filter(order -> order.customerId().equals(customerId)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(String orderId) {
        Order removed = ordersById.remove(orderId);
        if (removed == null) {
            return false;
        }
        try {
            objectMapper.writeValue(storagePath.toFile(),new ArrayList<>(ordersById.values()));
        } catch (IOException e) {
            throw new StorageException("couldn't be deleted", e);
        }
        return true;
    }

    @Override
    public boolean existsByExternalOrderId(String externalOrderId) {
        return ordersById.values().stream().anyMatch(extOrder -> extOrder.externalOrderId().equals(externalOrderId));
    }
}
