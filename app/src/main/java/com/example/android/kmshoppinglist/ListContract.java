package com.example.android.kmshoppinglist;

import android.provider.BaseColumns;

/**
 * Created by Lydia on 15-Mar-18.
 */

public class ListContract {

    private ListContract() {
    }

    public static final class ItemsList implements BaseColumns {

        public static final String TABLE_NAME = "ItemsList";
        public static final String COLUMN_ITEM_NAME = "Item_Name";
        public static final String COLUMN_ITEM_AMOUNT = "Item_Amount";
        public static final String COLUMN_ITEM_UNIT_PRICE = "Item_Unit_Price";
        public static final String COLUMN_ITEM_TOTAL_PRICE = "Item_Total_Price";

    }

}
