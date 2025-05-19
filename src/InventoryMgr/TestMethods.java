package InventoryMgr;

import java.time.LocalDate;
import java.util.List;

public class TestMethods {

    public static void main(String[] args) {
        Item itm = new Item("I0001", "dont mind", 41, 30.75, 138, 11, "Vegetables", LocalDate.from(LocalDate.now().minusDays(1)));
//        System.out.println(UpdateStock.FindPo(itm).toString());
    }

}
