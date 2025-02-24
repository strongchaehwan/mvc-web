package hello.item_service.item.repository;

import hello.item_service.item.domain.Item;
import hello.item_service.item.dto.ItemUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void aftherEach() {
        itemRepository.clearStore();
    }


    @Test
    void save() {
        //given
        Item itemA = new Item("itemA", 10000, 10);

        //when
        Item saveItem = itemRepository.save(itemA);

        //then
        Item findItem = itemRepository.findById(saveItem.getId());
        Assertions.assertEquals(findItem,saveItem);
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 10000, 10);
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        Assertions.assertEquals(2,result.size());
        Assertions.assertTrue(result.contains(itemA));
        Assertions.assertTrue(result.contains(itemB));
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("itemA", 10000, 10);
        ItemUpdateDto itemUpdateDto = new ItemUpdateDto("ItemB", 20000, 20);
        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();

        //when
        itemRepository.update(itemId, itemUpdateDto);

        //then
        Item findItem = itemRepository.findById(itemId);

        Assertions.assertEquals(findItem.getItemName(),itemUpdateDto.getItemName());
        Assertions.assertEquals(findItem.getPrice(),itemUpdateDto.getPrice());
        Assertions.assertEquals(findItem.getQuantity(),itemUpdateDto.getQuantity());


    }

}