package hello.item_service.item.repository;

import hello.item_service.item.domain.Item;
import hello.item_service.item.dto.ItemUpdateDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new ConcurrentHashMap<>();
    private static AtomicLong atomicLong = new AtomicLong(1);

    public Item save(Item item) {
        item.setId(atomicLong.getAndIncrement());
        store.put(item.getId(), item);
        return item;
    }

    public void delete(Long itemId) {
        store.remove(itemId);

    }

    public Item findById(Long itemId) {
        return store.get(itemId);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, ItemUpdateDto itemUpdateDto) {
        Item findItem = findById(itemId);
        findItem.setItemName(itemUpdateDto.getItemName());
        findItem.setPrice(itemUpdateDto.getPrice());
        findItem.setQuantity(itemUpdateDto.getQuantity());
    }

    public void deleteItem(Long itemId) {
        store.remove(itemId);
    }

    public void clearStore() {
        store.clear();
    }


}
