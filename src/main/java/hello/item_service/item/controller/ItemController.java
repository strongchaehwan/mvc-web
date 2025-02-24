package hello.item_service.item.controller;


import hello.item_service.item.domain.Item;
import hello.item_service.item.dto.ItemRegisterDto;
import hello.item_service.item.dto.ItemUpdateDto;
import hello.item_service.item.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    private String item(@PathVariable(name = "itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }


    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String saveV1(@RequestParam(name = "itemName") String itemName, @RequestParam(name = "price") int price,
                         @RequestParam(name = "quantity") Integer quantity, Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

    //@PostMapping("/add")
    public String saveV2(@ModelAttribute ItemRegisterDto itemRegisterDto, Model model) {
        Item item = new Item(itemRegisterDto.getItemName(), itemRegisterDto.getPrice(), itemRegisterDto.getQuantity());
        itemRepository.save(item);
        model.addAttribute("item", item);
        //return "basic/item";
        return "redirect:/basic/items/" + item.getId();
    }


    /**
     *
     */
    @PostMapping("/add")
    public String saveV3(@ModelAttribute ItemRegisterDto itemRegisterDto, RedirectAttributes redirectAttributes) {
        Item item = new Item(itemRegisterDto.getItemName(), itemRegisterDto.getPrice(), itemRegisterDto.getQuantity());
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    private String editForm(@PathVariable(name = "itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    private String edit(@PathVariable(name = "itemId") Long itemId, @ModelAttribute ItemUpdateDto itemUpdateDto) {
        itemRepository.update(itemId, itemUpdateDto);
        Item item = itemRepository.findById(itemId);

        return "redirect:/basic/items/{itemId}";
    }


    @PostMapping("/{itemId}/delete")
    public String delete(@PathVariable(name = "itemId") Long itemId) {
        itemRepository.delete(itemId);
        return "redirect:/basic/items";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("운영체제", 10000, 10));
        itemRepository.save(new Item("데이터통신", 20000, 20));
    }


}
