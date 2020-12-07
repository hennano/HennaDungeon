package net.hennabatch.hennadungeon.item;

import net.hennabatch.hennadungeon.effect.BuffEffect;
import net.hennabatch.hennadungeon.effect.InvisibleEffect;
import net.hennabatch.hennadungeon.effect.RegenerationEffect;
import net.hennabatch.hennadungeon.entity.Status;
import net.hennabatch.hennadungeon.item.armor.*;
import net.hennabatch.hennadungeon.item.potion.AddEffectPotion;
import net.hennabatch.hennadungeon.item.potion.HealPotionItem;
import net.hennabatch.hennadungeon.item.weapon.*;

import java.util.ArrayList;
import java.util.List;

public class Items {

    public static final Item HEAL_POTION = new HealPotionItem();
    public static final Item REGENRATION_POTION = new AddEffectPotion(new RegenerationEffect(10, 40));
    public static final Item ATK_BUFF_POTION = new AddEffectPotion(new BuffEffect(5, Status.EnumStatus.ATK, 10, false));
    public static final Item DEF_BUFF_POTION = new AddEffectPotion(new BuffEffect(5, Status.EnumStatus.DEF, 10, false));
    public static final Item MDEF_BUFF_POTION = new AddEffectPotion(new BuffEffect(5, Status.EnumStatus.MDEF, 10, false));
    public static final Item INVISIBLE_POTION = new AddEffectPotion(new InvisibleEffect(20));

    public static final Item HAND = new HandWeaponItem();
    public static final Item SWORD = new SwordItem();
    public static final Item POISON_DAGGER = new PoisonDaggerItem();
    public static final Item GRIMOIRE = new GrimoireItem();
    public static final Item MAGICALSWORD = new MagicalSwordItem();
    public static final Item LONGSWORD = new LongSwordItem();
    public static final Item NECRONOMICON = new NecronomiconItem();

    public static final Item SKIN = new SkinArmorItem();
    public static final Item LEATHER_ARMOR = new LeatherArmorItem();
    public static final Item IRON_ARMOR = new IronArmorItem();
    public static final Item CROTHES = new ClothesArmorItem();
    public static final Item ROBE = new RobeItem();

    public static final Item CRYSTAL = new CrystalItem();
    public static final Item KEY = new KeyItem();

    private static final Items singleton = new Items();

    private final List<Item> items = new ArrayList<>();

    private Items(){
        registerItems();
    }

    public static Items getInstance(){
        return singleton;
    }

    public List<Item> getItems() {
        return items;
    }

    public void registerItem(Item item){
        items.add(item);
    }

    public void registerItems(){
        registerItem(HEAL_POTION);
        registerItem(REGENRATION_POTION);
        registerItem(ATK_BUFF_POTION);
        registerItem(DEF_BUFF_POTION);
        registerItem(MDEF_BUFF_POTION);
        registerItem(INVISIBLE_POTION);
        registerItem(SWORD);
        registerItem(POISON_DAGGER);
        registerItem(GRIMOIRE);
        registerItem(MAGICALSWORD);
        registerItem(LONGSWORD);
        registerItem(LEATHER_ARMOR);
        registerItem(IRON_ARMOR);
        registerItem(NECRONOMICON);
        registerItem(CROTHES);
        registerItem(ROBE);
        registerItem(CRYSTAL);
    }
}
