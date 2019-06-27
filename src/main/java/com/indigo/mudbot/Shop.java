package com.indigo.mudbot;

import com.indigo.mudbot.Items.Item;

public class Shop {
    Item shopItem1;
    Item shopItem2;
    Item shopItem3;
    public Shop(Item item1, Item item2, Item item3){
        this.shopItem1 = item1;
        this.shopItem2 = item2;
        this.shopItem3 = item3;
    }
    public Item getItem1(){
        return this.shopItem1;
    }
    public Item getItem2(){
        return this.shopItem2;
    }
    public Item getItem3(){
        return this.shopItem3;
    }
}

