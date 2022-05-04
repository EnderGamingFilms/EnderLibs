package me.endergaming.enderlibs.item;

import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * {@link BetterItemStack} provides an easier way of interacting with {@link ItemStack}s and their corresponding {@link ItemMeta}s
 */
public class BetterItemStack {

    private final ItemStack itemStack;

    /**
     * {@link BetterItemStack} provides an easier way of interacting with {@link ItemStack}s and their corresponding {@link ItemMeta}s
     * @param itemStack The {@link ItemStack} to link to this instance
     */
    public BetterItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    //region ItemMeta
    private ItemMeta meta() {
        return itemStack.getItemMeta();
    }
    
    private void meta(ItemMeta meta) {
        itemStack.setItemMeta(meta);
    }

    /**
     * @return Whether the linked {@link ItemStack} has a custom display name set
     */
    public boolean hasDisplayName() {
        return meta().hasDisplayName();
    }

    /**
     * @return The custom display name of the linked {@link ItemStack}
     */
    @NotNull
    public String getDisplayName() {
        return meta().getDisplayName();
    }

    /**
     * Set the custom display name of the linked {@link ItemStack}
     * @param s The text to set the custom display name to
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setDisplayName(@Nullable String s) {
         ItemMeta meta = meta();
         meta.setDisplayName(s);
         meta(meta);

         return this;
    }

    public boolean hasLocalizedName() {
        return meta().hasLocalizedName();
    }

    /**
     * @return The custom localized name of the linked {@link ItemStack}
     */
    @NotNull
    public String getLocalizedName() {
        return meta().getLocalizedName();
    }

    /**
     * Set the custom localized name of the linked {@link ItemStack}
     * @param s The text to set the custom localized name to
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setLocalizedName(@Nullable String s) {
        ItemMeta meta = meta();
        meta.setLocalizedName(s);
        meta(meta);

        return this;
    }

    /**
     * @return Whether the linked {@link ItemStack} has lore set
     */
    public boolean hasLore() {
        return meta().hasLore();
    }

    /**
     * @return The lore of the linked {@link ItemStack}
     */
    @Nullable
    public List<String> getLore() {
        return meta().getLore();
    }

    /**
     * Set the lore of the linked {@link ItemStack}
     * @param list A list of lines to set as the lore of the linked {@link ItemStack}
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setLore(@Nullable List<String> list) {
        ItemMeta meta = meta();
        meta.setLore(list);
        meta(meta);

        return this;
    }

    /**
     * Set the lore of the linked {@link ItemStack}
     * @param list A list of lines to set as the lore of the linked {@link ItemStack}
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setLore(String... list) {
        ItemMeta meta = meta();
        meta.setLore(Arrays.asList(list));
        meta(meta);

        return this;
    }

    /**
     * @return Whether the linked {@link ItemStack} has CustomModelData
     */
    public boolean hasCustomModelData() {
        return meta().hasCustomModelData();
    }

    /**
     * @return The CustomModelData of the linked {@link ItemStack}
     */
    public int getCustomModelData() {
        return meta().getCustomModelData();
    }

    /**
     * Set the CustomModelData of the linked {@link ItemStack}
     * @param integer The value to set the CustomModelData to
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setCustomModelData(@Nullable Integer integer) {
        ItemMeta meta = meta();
        meta.setCustomModelData(integer);
        meta(meta);

        return this;
    }

    /**
     * @return Whether the linked {@link ItemStack} has any enchantments
     */
    public boolean hasEnchants() {
        return meta().hasEnchants();
    }

    public boolean hasEnchant(@NotNull Enchantment enchantment) {
        return meta().hasEnchant(enchantment);
    }

    public int getEnchantLevel(@NotNull Enchantment enchantment) {
        return meta().getEnchantLevel(enchantment);
    }

    @NotNull
    public Map<Enchantment, Integer> getEnchants() {
        return meta().getEnchants();
    }

    public boolean addEnchant(@NotNull Enchantment enchantment, int i, boolean b) {
        ItemMeta meta = meta();
        boolean r = meta.addEnchant(enchantment, i, b);
        meta(meta);

        return r;
    }

    public boolean removeEnchant(@NotNull Enchantment enchantment) {
        ItemMeta meta = meta();
        boolean r = meta.removeEnchant(enchantment);
        meta(meta);

        return r;
    }

    public boolean hasConflictingEnchant(@NotNull Enchantment enchantment) {
        return meta().hasConflictingEnchant(enchantment);
    }

    public BetterItemStack addEnchantInline(@NotNull Enchantment enchantment, int i, boolean b) {
        ItemMeta meta = meta();
        boolean r = meta.addEnchant(enchantment, i, b);
        meta(meta);

        return this;
    }

    public BetterItemStack removeEnchantInline(@NotNull Enchantment enchantment) {
        ItemMeta meta = meta();
        boolean r = meta.removeEnchant(enchantment);
        meta(meta);

        return this;
    }

    public BetterItemStack addItemFlags(@NotNull ItemFlag... itemFlags) {
        ItemMeta meta = meta();
        meta.addItemFlags(itemFlags);
        meta(meta);

        return this;
    }

    public BetterItemStack removeItemFlags(@NotNull ItemFlag... itemFlags) {
        ItemMeta meta = meta();
        meta.removeItemFlags(itemFlags);
        meta(meta);

        return this;
    }

    @NotNull
    public Set<ItemFlag> getItemFlags() {
        return meta().getItemFlags();
    }

    public boolean hasItemFlag(@NotNull ItemFlag itemFlag) {
        return meta().hasItemFlag(itemFlag);
    }

    public boolean isUnbreakable() {
        return meta().isUnbreakable();
    }

    public BetterItemStack setUnbreakable(boolean b) {
        ItemMeta meta = meta();
        meta.setUnbreakable(b);
        meta(meta);

        return this;
    }

    public boolean hasAttributeModifiers() {
        return meta().hasAttributeModifiers();
    }

    @Nullable
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return meta().getAttributeModifiers();
    }

    @NotNull
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        return meta().getAttributeModifiers(equipmentSlot);
    }

    @Nullable
    public Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute) {
        return meta().getAttributeModifiers(attribute);
    }

    public boolean addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier attributeModifier) {
        ItemMeta meta = meta();
        boolean r = meta.addAttributeModifier(attribute, attributeModifier);
        meta(meta);

        return r;
    }

    public BetterItemStack setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> multimap) {
        ItemMeta meta = meta();
        meta.setAttributeModifiers(multimap);
        meta(meta);

        return this;
    }

    public boolean removeAttributeModifier(@NotNull Attribute attribute) {
        ItemMeta meta = meta();
        boolean r = meta.removeAttributeModifier(attribute);
        meta(meta);

        return r;
    }

    public boolean removeAttributeModifier(@NotNull EquipmentSlot equipmentSlot) {
        ItemMeta meta = meta();
        boolean r = meta.removeAttributeModifier(equipmentSlot);
        meta(meta);

        return r;
    }

    public boolean removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier attributeModifier) {
        ItemMeta meta = meta();
        boolean r = meta.removeAttributeModifier(attribute, attributeModifier);
        meta(meta);

        return r;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    public CustomItemTagContainer getCustomTagContainer() {
        return meta().getCustomTagContainer();
    }

    @SuppressWarnings("deprecation")
    public BetterItemStack setVersion(int i) {
        ItemMeta meta = meta();
        meta.setVersion(i);
        meta(meta);

        return this;
    }

    @NotNull
    public ItemMeta cloneMeta() {
        return meta().clone();
    }

    //endregion

    //region ItemStack

    public Material getType() {
        return itemStack.getType();
    }

    public BetterItemStack setType(Material type) {
        itemStack.setType(type);

        return this;
    }

    public int getAmount() {
        return itemStack.getAmount();
    }

    public BetterItemStack setAmount(int amount) {
        itemStack.setAmount(amount);

        return this;
    }

    @SuppressWarnings("deprecation")
    public MaterialData getData() {
        return itemStack.getData();
    }

    @SuppressWarnings("deprecation")
    public BetterItemStack setData(MaterialData data) {
        itemStack.setData(data);

        return this;
    }

    @SuppressWarnings("deprecation")
    public short getDurability() {
        return itemStack.getDurability();
    }

    @SuppressWarnings("deprecation")
    public BetterItemStack setDurability(short durability) {
        itemStack.setDurability(durability);

        return this;
    }

    public int getMaxStackSize() {
        return itemStack.getMaxStackSize();
    }

    public boolean isSimilar(ItemStack itemStack) {
        return itemStack.isSimilar(itemStack);
    }

    public ItemStack cloneStack() {
        return itemStack.clone();
    }

    //endregion

    /**
     * @return The {@link ItemMeta} of the linked {@link ItemStack}
     */
    public ItemMeta getItemMeta() {
        return meta();
    }

    /**
     * @return The {@link ItemStack} linked to this instance
     */
    public ItemStack getItemStack() {
        return itemStack;
    }
}
