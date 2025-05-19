package InventoryMgr;

import PurchaseMgr.PurchaseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateStock {

    static List<PurchaseOrder> FindPo(Item item) {

        // ReadPO File, find itemID and check if the itemId is approved or whatsoever

        List<PurchaseOrder> AllPO = PurchaseOrder.listAllPurchaseOrders("datafile/purchaseOrder.txt");
        List<PurchaseOrder> Filtered = new ArrayList<PurchaseOrder>();

        for (PurchaseOrder po : AllPO) {
            if (Objects.equals(po.ItemID, item.ItemID)) {
                if (Objects.equals(po.Status, "Approved")) {
                    Filtered.add(po);
                }
            }
        }

        return Filtered;
    }
}
