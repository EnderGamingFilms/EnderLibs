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
        return this.itemStack.getItemMeta();
    }
    
    private void meta(ItemMeta meta) {
        this.itemStack.setItemMeta(meta);
    }

    /**
     * @return Whether the linked {@link ItemStack} has a custom display name set
     */
    public boolean hasDisplayName() {
        return this.meta().hasDisplayName();
    }

    /**
     * @return The custom display name of the linked {@link ItemStack}
     */
    @NotNull
    public String getDisplayName() {
        return this.meta().getDisplayName();
    }

    /**
     * Set the custom display name of the linked {@link ItemStack}
     * @param s The text to set the custom display name to
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setDisplayName(@Nullable String s) {
         ItemMeta meta = this.meta();
         meta.setDisplayName(s);
        this.meta(meta);

         return this;
    }

    public boolean hasLocalizedName() {
        return this.meta().hasLocalizedName();
    }

    /**
     * @return The custom localized name of the linked {@link ItemStack}
     */
    @NotNull
    public String getLocalizedName() {
        return this.meta().getLocalizedName();
    }

    /**
     * Set the custom localized name of the linked {@link ItemStack}
     * @param s The text to set the custom localized name to
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setLocalizedName(@Nullable String s) {
        ItemMeta meta = this.meta();
        meta.setLocalizedName(s);
        this.meta(meta);

        return this;
    }

    /**
     * @return Whether the linked {@link ItemStack} has lore set
     */
    public boolean hasLore() {
        return this.meta().hasLore();
    }

    /**
     * @return The lore of the linked {@link ItemStack}
     */
    @Nullable
    public List<String> getLore() {
        return this.meta().getLore();
    }

    /**
     * Set the lore of the linked {@link ItemStack}
     * @param list A list of lines to set as the lore of the linked {@link ItemStack}
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setLore(@Nullable List<String> list) {
        ItemMeta meta = this.meta();
        meta.setLore(list);
        this.meta(meta);

        return this;
    }

    /**
     * Set the lore of the linked {@link ItemStack}
     * @param list A list of lines to set as the lore of the linked {@link ItemStack}
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setLore(String... list) {
        ItemMeta meta = this.meta();
        meta.setLore(Arrays.asList(list));
        this.meta(meta);

        return this;
    }

    /**
     * @return Whether the linked {@link ItemStack} has CustomModelData
     */
    public boolean hasCustomModelData() {
        return this.meta().hasCustomModelData();
    }

    /**
     * @return The CustomModelData of the linked {@link ItemStack}
     */
    public int getCustomModelData() {
        return this.meta().getCustomModelData();
    }

    /**
     * Set the CustomModelData of the linked {@link ItemStack}
     * @param integer The value to set the CustomModelData to
     * @return This instance of {@link BetterItemStack}
     */
    public BetterItemStack setCustomModelData(@Nullable Integer integer) {
        ItemMeta meta = this.meta();
        meta.setCustomModelData(integer);
        this.meta(meta);

        return this;
    }

    /**
     * @return Whether the linked {@link ItemStack} has any enchantments
     */
    public boolean hasEnchants() {
        return this.meta().hasEnchants();
    }

    public boolean hasEnchant(@NotNull Enchantment enchantment) {
        return this.meta().hasEnchant(enchantment);
    }

    public int getEnchantLevel(@NotNull Enchantment enchantment) {
        return this.meta().getEnchantLevel(enchantment);
    }

    @NotNull
    public Map<Enchantment, Integer> getEnchants() {
        return this.meta().getEnchants();
    }

    public boolean addEnchant(@NotNull Enchantment enchantment, int i, boolean b) {
        ItemMeta meta = this.meta();
        boolean r = meta.addEnchant(enchantment, i, b);
        this.meta(meta);

        return r;
    }

    public boolean removeEnchant(@NotNull Enchantment enchantment) {
        ItemMeta meta = this.meta();
        boolean r = meta.removeEnchant(enchantment);
        this.meta(meta);

        return r;
    }

    public boolean hasConflictingEnchant(@NotNull Enchantment enchantment) {
        return this.meta().hasConflictingEnchant(enchantment);
    }

    public BetterItemStack addEnchantInline(@NotNull Enchantment enchantment, int i, boolean b) {
        ItemMeta meta = this.meta();
        boolean r = meta.addEnchant(enchantment, i, b);
        this.meta(meta);

        return this;
    }

    public BetterItemStack removeEnchantInline(@NotNull Enchantment enchantment) {
        ItemMeta meta = this.meta();
        boolean r = meta.removeEnchant(enchantment);
        this.meta(meta);

        return this;
    }

    public BetterItemStack addItemFlags(@NotNull ItemFlag... itemFlags) {
        ItemMeta meta = this.meta();
        meta.addItemFlags(itemFlags);
        this.meta(meta);

        return this;
    }

    public BetterItemStack removeItemFlags(@NotNull ItemFlag... itemFlags) {
        ItemMeta meta = this.meta();
        meta.removeItemFlags(itemFlags);
        this.meta(meta);

        return this;
    }

    @NotNull
    public Set<ItemFlag> getItemFlags() {
        return this.meta().getItemFlags();
    }

    public boolean hasItemFlag(@NotNull ItemFlag itemFlag) {
        return this.meta().hasItemFlag(itemFlag);
    }

    public boolean isUnbreakable() {
        return this.meta().isUnbreakable();
    }

    public BetterItemStack setUnbreakable(boolean b) {
        ItemMeta meta = this.meta();
        meta.setUnbreakable(b);
        this.meta(meta);

        return this;
    }

    public boolean hasAttributeModifiers() {
        return this.meta().hasAttributeModifiers();
    }

    @Nullable
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.meta().getAttributeModifiers();
    }

    @NotNull
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        return this.meta().getAttributeModifiers(equipmentSlot);
    }

    @Nullable
    public Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute) {
        return this.meta().getAttributeModifiers(attribute);
    }

    public boolean addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier attributeModifier) {
        ItemMeta meta = this.meta();
        boolean r = meta.addAttributeModifier(attribute, attributeModifier);
        this.meta(meta);

        return r;
    }

    public BetterItemStack setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> multimap) {
        ItemMeta meta = this.meta();
        meta.setAttributeModifiers(multimap);
        this.meta(meta);

        return this;
    }

    public boolean removeAttributeModifier(@NotNull Attribute attribute) {
        ItemMeta meta = this.meta();
        boolean r = meta.removeAttributeModifier(attribute);
        this.meta(meta);

        return r;
    }

    public boolean removeAttributeModifier(@NotNull EquipmentSlot equipmentSlot) {
        ItemMeta meta = this.meta();
        boolean r = meta.removeAttributeModifier(equipmentSlot);
        this.meta(meta);

        return r;
    }

    public boolean removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier attributeModifier) {
        ItemMeta meta = this.meta();
        boolean r = meta.removeAttributeModifier(attribute, attributeModifier);
        this.meta(meta);

        return r;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    public CustomItemTagContainer getCustomTagContainer() {
        return this.meta().getCustomTagContainer();
    }

    @SuppressWarnings("deprecation")
    public BetterItemStack setVersion(int i) {
        ItemMeta meta = this.meta();
        meta.setVersion(i);
        this.meta(meta);

        return this;
    }

    @NotNull
    public ItemMeta cloneMeta() {
        return this.meta().clone();
    }

    //endregion

    //region ItemStack

    public Material getType() {
        return this.itemStack.getType();
    }

    public BetterItemStack setType(Material type) {
        this.itemStack.setType(type);

        return this;
    }

    public int getAmount() {
        return this.itemStack.getAmount();
    }

    public BetterItemStack setAmount(int amount) {
        this.itemStack.setAmount(amount);

        return this;
    }

    @SuppressWarnings("deprecation")
    public MaterialData getData() {
        return this.itemStack.getData();
    }

    @SuppressWarnings("deprecation")
    public BetterItemStack setData(MaterialData data) {
        this.itemStack.setData(data);

        return this;
    }

    @SuppressWarnings("deprecation")
    public short getDurability() {
        return this.itemStack.getDurability();
    }

    @SuppressWarnings("deprecation")
    public BetterItemStack setDurability(short durability) {
        this.itemStack.setDurability(durability);

        return this;
    }

    public int getMaxStackSize() {
        return this.itemStack.getMaxStackSize();
    }

    public boolean isSimilar(ItemStack itemStack) {
        return itemStack.isSimilar(itemStack);
    }

    public ItemStack cloneStack() {
        return this.itemStack.clone();
    }

    //endregion

    /**
     * @return The {@link ItemMeta} of the linked {@link ItemStack}
     */
    public ItemMeta getItemMeta() {
        return this.meta();
    }

    /**
     * @return The {@link ItemStack} linked to this instance
     */
    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
