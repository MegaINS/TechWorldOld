package ru.megains.techworld.common.item

import ru.megains.techworld.common.item.ItemType.ItemType

class ItemArmor(name:String)  extends Item(name){

    override val itemType: ItemType = ItemType.single
}
