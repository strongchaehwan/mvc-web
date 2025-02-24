package hello.item_service.item.dto;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRegisterDto {

//    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRegisterDto that = (ItemRegisterDto) o;
        return Objects.equals(itemName, that.itemName) && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, price, quantity);
    }

    @Override
    public String toString() {
        return "ItemRegisterDto{" +
                "itemName='" + itemName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
