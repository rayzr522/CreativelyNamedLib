package me.rayzr522.cnl.utils

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ItemFactory private constructor(private val base: ItemStack) {
    companion object {
        fun of(item: ItemStack, init: ItemFactory.() -> Unit): ItemFactory {
            val factory = ItemFactory(item.clone())
            factory.init()
            return factory
        }

        fun of(type: Material, init: ItemFactory.() -> Unit): ItemFactory = of(ItemStack(type), init)
    }

    var type: Material
        get() {
            return base.type
        }
        set(value) {
            base.type = value
        }

    var amount: Int
        get() {
            return base.amount
        }
        set(value) {
            base.amount = value
        }

    var durability: Int
        get() {
            return base.durability as Int
        }
        set(value) {
            assert(value >= Short.MIN_VALUE || value <= Short.MAX_VALUE, { "value must be between Short.MIN_VALUE and Short.MAX_VALUE" })

            base.durability = value as Short
        }

    var name: String?
        get() {
            return base.itemMeta?.displayName
        }
        set(value) {
            base.itemMeta?.displayName = value?.colorize()
        }

    var lore: MutableList<String?>
        get() {
            return base.itemMeta?.lore ?: mutableListOf()
        }
        set(value) {
            base.itemMeta?.lore = value.colorize()
        }

    fun addLore(loreToAdd: List<String?>) {
        val oldLore = lore
        oldLore.addAll(loreToAdd)
        lore = oldLore
    }

    fun build() = base.clone()
}

val a = ItemFactory.of(Material.WOOD) {
    name = "&cAwesome Item"
    lore = mutableListOf("&7Amazing", "", "&7So epic")
}.build()