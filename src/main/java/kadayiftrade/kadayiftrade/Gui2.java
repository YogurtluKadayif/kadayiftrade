package kadayiftrade.kadayiftrade;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.config.PixelmonItemsPokeballs;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.sql.*;
import java.util.UUID;

public class Gui2 {

    public Inventory inventory;
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL3 = "jdbc:h2:./kadayiftrade/db";
    static final String USER = "sa";
    static final String PASS = "";
    Connection connect2 = null;
    Statement statmt2 = null;
    Connection connect3 = null;
    Statement statmt3 = null;

    public Gui2(Player player, Integer kod) {
        this.inventory = Inventory.builder()
                .of(InventoryArchetypes.DOUBLE_CHEST)
                .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(TextColors.AQUA, "yTrade")))
                .property("inventorydimension", InventoryDimension.of(9, 5))
                .listener(ClickInventoryEvent.class, (ClickInventoryEvent event) -> {
                    event.setCancelled(true);
                    ItemStack affected;
                    ItemStack affectedi = null;
                    if (event.getSlot() != null && !(event.getTransactions().isEmpty())) {
                        affected = event.getTransactions().get(0).getOriginal().createStack();
                        if (event instanceof ClickInventoryEvent.Shift && (affected.getType() == ItemTypes.AIR || affected.getType() == ItemTypes.NONE)) {
                            affected = event.getTransactions().get(0).getDefault().createStack();
                            affectedi = event.getTransactions().get(22).getDefault().createStack();
                        }
                        if (affected.getType() == PixelmonItems.itemPixelmonSprite) {
                            String inp = affected.get(Keys.DISPLAY_NAME).toString();
                            String out = inp.replaceAll("[^0-9?!\\.]", "");
                            net.minecraft.item.ItemStack stack2 = null;
                            ItemStack is2 = null;
                            PokemonSpec spec2 = null;
                            net.minecraft.item.ItemStack stack3 = null;
                            ItemStack is3 = null;
                            PokemonSpec spec3 = null;
                            net.minecraft.item.ItemStack stack4 = null;
                            ItemStack is4 = null;
                            PokemonSpec spec4 = null;
                            net.minecraft.item.ItemStack stack5 = null;
                            ItemStack is5 = null;
                            PokemonSpec spec5 = null;
                            net.minecraft.item.ItemStack stack6 = null;
                            ItemStack is6 = null;
                            PokemonSpec spec6 = null;
                            net.minecraft.item.ItemStack stack7 = null;
                            ItemStack is7 = null;
                            PokemonSpec spec7 = null;
                            net.minecraft.item.ItemStack stack8 = null;
                            ItemStack is8 = null;
                            PokemonSpec spec8 = null;
                            PlayerPartyStorage p = Pixelmon.storageManager.getParty(player.getUniqueId());
                            final UUID uuid = player.getUniqueId();
                            if (out != "" && out != null && out.isEmpty() == false && Integer.parseInt(out) == 1) {
                                if (p.get(0) != null) {
                                    String pp = p.get(0).getSpecies().name;
                                    spec2 = PokemonSpec.from(pp);
                                    stack2 = ItemPixelmonSprite.getPhoto(spec2.create());
                                    ItemStack test2 = ItemStackUtil.fromNative(stack2);
                                    is2 = ItemStack.builder()
                                            .fromItemStack(test2).build();
                                    setSlot(4, 2, is2, "1", TextColors.AQUA, "Slot: 1" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp + "\nIVs: " + p.get(0).getIVs().getPercentage(1) + "\nNature: " + p.get(0).getNature().toString() + "\nGrowth: " + p.get(0).getGrowth().toString() + "\nLevel: " + p.get(0).getLevel() + "\nGender: " + p.get(0).getGender() + "\nAbility: " + p.get(0).getAbilityName() + "\nHeld: " + p.get(0).getHeldItem().getDisplayName() + "\nForm: " + p.get(0).getFormEnum().getForm());
                                    Kadayiftrade.pSlot.get(uuid).put("slot:", "0");
                                }
                            } else {
                                if (out != "" && out != null && out.isEmpty() == false && Integer.parseInt(out) == 2) {
                                    if (p.get(1) != null) {
                                        String pp2 = p.get(1).getSpecies().name;
                                        spec4 = PokemonSpec.from(pp2);
                                        stack4 = ItemPixelmonSprite.getPhoto(spec4.create());
                                        ItemStack test4 = ItemStackUtil.fromNative(stack4);
                                        is4 = ItemStack.builder()
                                                .fromItemStack(test4).build();
                                        setSlot(4, 2, is4, "2", TextColors.AQUA, "Slot: 2" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp2 + "\nIVs: " + p.get(1).getIVs().getPercentage(1) + "\nNature: " + p.get(1).getNature().toString() + "\nGrowth: " + p.get(1).getGrowth().toString() + "\nLevel: " + p.get(1).getLevel() + "\nGender: " + p.get(1).getGender() + "\nAbility: " + p.get(1).getAbilityName() + "\nHeld: " + p.get(1).getHeldItem().getDisplayName() + "\nForm: " + p.get(1).getFormEnum().getForm());
                                        Kadayiftrade.pSlot.get(uuid).put("slot:", "1");
                                    }
                                } else {
                                    if (out != "" && out != null && out.isEmpty() == false && Integer.parseInt(out) == 3) {
                                        if (p.get(2) != null) {
                                            String pp3 = p.get(2).getSpecies().name;
                                            spec5 = PokemonSpec.from(pp3);
                                            stack5 = ItemPixelmonSprite.getPhoto(spec5.create());
                                            ItemStack test5 = ItemStackUtil.fromNative(stack5);
                                            is5 = ItemStack.builder()
                                                    .fromItemStack(test5).build();
                                            setSlot(4, 2, is5, "3", TextColors.AQUA, "Slot: 3" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp3 + "\nIVs: " + p.get(2).getIVs().getPercentage(1) + "\nNature: " + p.get(2).getNature().toString() + "\nGrowth: " + p.get(2).getGrowth().toString() + "\nLevel: " + p.get(2).getLevel() + "\nGender: " + p.get(2).getGender() + "\nAbility: " + p.get(2).getAbilityName() + "\nHeld: " + p.get(2).getHeldItem().getDisplayName() + "\nForm: " + p.get(2).getFormEnum().getForm());
                                            Kadayiftrade.pSlot.get(uuid).put("slot:", "2");
                                        }
                                    } else {
                                        if (out != "" && out != null && out.isEmpty() == false && Integer.parseInt(out) == 4) {
                                            if (p.get(3) != null) {
                                                String pp4 = p.get(3).getSpecies().name;
                                                spec6 = PokemonSpec.from(pp4);
                                                stack6 = ItemPixelmonSprite.getPhoto(spec6.create());
                                                ItemStack test6 = ItemStackUtil.fromNative(stack6);
                                                is6 = ItemStack.builder()
                                                        .fromItemStack(test6).build();
                                                setSlot(4, 2, is6, "4", TextColors.AQUA, "Slot: 4" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp4 + "\nIVs: " + p.get(3).getIVs().getPercentage(1) + "\nNature: " + p.get(3).getNature().toString() + "\nGrowth: " + p.get(3).getGrowth().toString() + "\nLevel: " + p.get(3).getLevel() + "\nGender: " + p.get(3).getGender() + "\nAbility: " + p.get(3).getAbilityName() + "\nHeld: " + p.get(3).getHeldItem().getDisplayName() + "\nForm: " + p.get(3).getFormEnum().getForm());
                                                Kadayiftrade.pSlot.get(uuid).put("slot:", "3");
                                            }
                                        } else {
                                            if (out != "" && out != null && out.isEmpty() == false && Integer.parseInt(out) == 5) {
                                                if (p.get(4) != null) {
                                                    String pp5 = p.get(4).getSpecies().name;
                                                    spec7 = PokemonSpec.from(pp5);
                                                    stack7 = ItemPixelmonSprite.getPhoto(spec7.create());
                                                    ItemStack test7 = ItemStackUtil.fromNative(stack7);
                                                    is7 = ItemStack.builder()
                                                            .fromItemStack(test7).build();
                                                    setSlot(4, 2, is7, "5", TextColors.AQUA, "Slot: 5" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp5 + "\nIVs: " + p.get(4).getIVs().getPercentage(1) + "\nNature: " + p.get(4).getNature().toString() + "\nGrowth: " + p.get(4).getGrowth().toString() + "\nLevel: " + p.get(4).getLevel() + "\nGender: " + p.get(4).getGender() + "\nAbility: " + p.get(4).getAbilityName() + "\nHeld: " + p.get(4).getHeldItem().getDisplayName() + "\nForm: " + p.get(4).getFormEnum().getForm());
                                                    Kadayiftrade.pSlot.get(uuid).put("slot:", "4");
                                                }
                                            } else {
                                                if (out != "" && out != null && out.isEmpty() == false && Integer.parseInt(out) == 6) {
                                                    if (p.get(5) != null) {
                                                        String pp6 = p.get(5).getSpecies().name;
                                                        spec8 = PokemonSpec.from(pp6);
                                                        stack8 = ItemPixelmonSprite.getPhoto(spec8.create());
                                                        ItemStack test8 = ItemStackUtil.fromNative(stack8);
                                                        is8 = ItemStack.builder()
                                                                .fromItemStack(test8).build();
                                                        setSlot(4, 2, is8, "6", TextColors.AQUA, "Slot: 6" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp6 + "\nIVs: " + p.get(5).getIVs().getPercentage(1) + "\nNature: " + p.get(5).getNature().toString() + "\nGrowth: " + p.get(5).getGrowth().toString() + "\nLevel: " + p.get(5).getLevel() + "\nGender: " + p.get(5).getGender() + "\nAbility: " + p.get(5).getAbilityName() + "\nHeld: " + p.get(5).getHeldItem().getDisplayName() + "\nForm: " + p.get(5).getFormEnum().getForm());
                                                        Kadayiftrade.pSlot.get(uuid).put("slot:", "5");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (affected.getType() == ItemTypes.GREEN_SHULKER_BOX) {
                                String inp = affected.get(Keys.DISPLAY_NAME).toString();
                                String inpkod = affected.get(Keys.ITEM_LORE).toString();
                                String out = inpkod.replaceAll("[^0-9?!\\.]", "");
                                final UUID uuid = player.getUniqueId();
                                if (inp.contains("Onayla")) {
                                    if (Kadayiftrade.pSlot.get(uuid) != null) {
                                        final String value = Kadayiftrade.pSlot.get(uuid).get("slot:");
                                        Sponge.getCommandManager().process(player, "yTrade takas " + out + " " + value);
                                        player.closeInventory();
                                    }
                                }
                            } else if (affected.getType() == ItemTypes.RED_SHULKER_BOX) {
                                String inp = affected.get(Keys.DISPLAY_NAME).toString();
                                if (inp.contains("Reddet")) {
                                    player.closeInventory();
                                }
                            }

                        }
                    }
                })
                .build(Kadayiftrade.instance);
        try {
            connect2 = DriverManager.getConnection(DB_URL3, USER, PASS);
            statmt2 = connect2.createStatement();
            connect3 = DriverManager.getConnection(DB_URL3, USER, PASS);
            statmt3 = connect3.createStatement();
            try {
                Class.forName(JDBC_DRIVER);

                String sqlara3 = "SELECT * FROM Istenenler WHERE kod='" + kod + "'";
                ResultSet rsara3 = statmt2.executeQuery(sqlara3);

                String sqlara2 = "SELECT * FROM Verilenler WHERE kod='" + kod + "'";
                ResultSet rsara2 = statmt3.executeQuery(sqlara2);
                net.minecraft.item.ItemStack stack = null;
                ItemStack is = null;
                PokemonSpec spec = null;
                net.minecraft.item.ItemStack stack2 = null;
                ItemStack is2 = null;
                PokemonSpec spec2 = null;
                net.minecraft.item.ItemStack stack3 = null;
                ItemStack is3 = null;
                PokemonSpec spec3 = null;
                net.minecraft.item.ItemStack stack4 = null;
                ItemStack is4 = null;
                PokemonSpec spec4 = null;
                net.minecraft.item.ItemStack stack5 = null;
                ItemStack is5 = null;
                PokemonSpec spec5 = null;
                net.minecraft.item.ItemStack stack6 = null;
                ItemStack is6 = null;
                PokemonSpec spec6 = null;
                net.minecraft.item.ItemStack stack7 = null;
                ItemStack is7 = null;
                PokemonSpec spec7 = null;
                net.minecraft.item.ItemStack stack8 = null;
                ItemStack is8 = null;
                PokemonSpec spec8 = null;
                while(rsara3.next() && rsara2.next()) {
                    setSlot(0, 0, ItemStack.of(ItemTypes.BARRIER), "Bilgi", TextColors.WHITE, "1. Istenen\n 2. Verilen" );
                    setSlot(4, 2, ItemStack.of(ItemTypes.BARRIER), "Verecegin", TextColors.WHITE, "" );
                    setSlot(4, 4, ItemStack.of(ItemTypes.BARRIER), "Bilgi", TextColors.WHITE, " Pokemonlarin" );
                    setSlot(2, 2, ItemStack.of(ItemTypes.GREEN_SHULKER_BOX), "Onayla", TextColors.GREEN, String.valueOf(kod) );
                    setSlot(6, 2, ItemStack.of(ItemTypes.RED_SHULKER_BOX), "Reddet", TextColors.RED, "" );
                    setSlot(4, 4, ItemStack.of((ItemType) PixelmonItemsPokeballs.pokeBall), "Senin Pokemonlarin", TextColors.AQUA, "" );

                    //
                    setSlot(1, 0, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(2, 0, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(3, 0, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(5, 0, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(6, 0, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(7, 0, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(0, 1, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(8, 1, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(0, 2, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(8, 2, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(0, 3, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(8, 3, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(8, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(1, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(2, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(3, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(5, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(6, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(7, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(8, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );
                    setSlot(0, 4, ItemStack.of(ItemTypes.STAINED_GLASS_PANE), "", TextColors.WHITE, "" );



                    // Istenen
                    stack = null;
                    is = null;
                    spec = PokemonSpec.from(rsara3.getString("pokemon"));
                    stack = ItemPixelmonSprite.getPhoto(spec.create());
                    ItemStack test = ItemStackUtil.fromNative(stack);

                    is = ItemStack.builder()
                            .fromItemStack(test).build();
                    setSlot(4, 0, is, String.valueOf(spec.name),  TextColors.AQUA, "ID: " + rsara3.getInt("id") + "\nOyuncu: " + rsara3.getString("isim") + "\nPokemon: " + rsara3.getString("pokemon") + "\nIVs: " + rsara3.getString("ivs") + "\nNature: " + rsara3.getString("nature") + "\nGrowth: " + rsara3.getString("growth") + "\nLevel: " + rsara3.getInt("level") + "\nGender: " + rsara3.getString("gender") + "\nAbility: " + rsara3.getString("ability") + "\nHeld: " + rsara3.getString("held") + "\nForm: " + rsara3.getInt("form") + "\nShiny: " + rsara3.getInt("shiny"));

                    // Verilen
                    spec3 = PokemonSpec.from(rsara2.getString("pokemon"));
                    stack3 = ItemPixelmonSprite.getPhoto(spec3.create());
                    ItemStack test3 = ItemStackUtil.fromNative(stack3);
                    is3 = ItemStack.builder()
                            .fromItemStack(test3).build();
                    setSlot(8, 0, is3, String.valueOf(spec3.name),  TextColors.AQUA, "ID: " + rsara2.getInt("id") + "\nOyuncu: " + rsara2.getString("isim") + "\nPokemon: " + rsara2.getString("pokemon") + "\nIVs: " + rsara2.getString("ivs") + "\nNature: " + rsara2.getString("nature") + "\nGrowth: " + rsara2.getString("growth") + "\nLevel: " + rsara2.getInt("level") + "\nGender: " + rsara2.getString("gender") + "\nAbility: " + rsara2.getString("ability") + "\nHeld: " + rsara2.getString("held") + "\nForm: " + rsara2.getInt("form") + "\nShiny: " + rsara2.getInt("shiny"));


                    // Takasi gerceklestirecek kisinin 1. pokemonu
                    PlayerPartyStorage p = Pixelmon.storageManager.getParty(player.getUniqueId());
                    if(p.countAll() >= 2) {
                        if (p.get(0) != null) {
                            String pp = p.get(0).getSpecies().name;
                            spec2 = PokemonSpec.from(pp);
                            stack2 = ItemPixelmonSprite.getPhoto(spec2.create());
                            ItemStack test2 = ItemStackUtil.fromNative(stack2);
                            is2 = ItemStack.builder()
                                    .fromItemStack(test2).build();
                            setSlot(1, 4, is2, "1", TextColors.AQUA, "Slot: 1" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp + "\nIVs: " + p.get(0).getIVs().getPercentage(1) + "\nNature: " + p.get(0).getNature().toString() + "\nGrowth: " + p.get(0).getGrowth().toString() + "\nLevel: " + p.get(0).getLevel() + "\nGender: " + p.get(0).getGender() + "\nAbility: " + p.get(0).getAbilityName() + "\nHeld: " + p.get(0).getHeldItem().getDisplayName() + "\nForm: " + p.get(0).getFormEnum().getForm());
                        }
                        // Takasi gerceklestirecek kisinin 2. pokemonu
                        if (p.get(1) != null) {
                            String pp2 = p.get(1).getSpecies().name;
                            spec4 = PokemonSpec.from(pp2);
                            stack4 = ItemPixelmonSprite.getPhoto(spec4.create());
                            ItemStack test4 = ItemStackUtil.fromNative(stack4);
                            is4 = ItemStack.builder()
                                    .fromItemStack(test4).build();
                            setSlot(2, 4, is4, "2", TextColors.AQUA, "Slot: 2" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp2 + "\nIVs: " + p.get(1).getIVs().getPercentage(1) + "\nNature: " + p.get(1).getNature().toString() + "\nGrowth: " + p.get(1).getGrowth().toString() + "\nLevel: " + p.get(1).getLevel() + "\nGender: " + p.get(1).getGender() + "\nAbility: " + p.get(1).getAbilityName() + "\nHeld: " + p.get(1).getHeldItem().getDisplayName() + "\nForm: " + p.get(1).getFormEnum().getForm());
                        }
                        // Takasi gerceklestirecek kisinin 3. pokemonu
                        if (p.get(2) != null) {
                            String pp3 = p.get(2).getSpecies().name;
                            spec5 = PokemonSpec.from(pp3);
                            stack5 = ItemPixelmonSprite.getPhoto(spec5.create());
                            ItemStack test5 = ItemStackUtil.fromNative(stack5);
                            is5 = ItemStack.builder()
                                    .fromItemStack(test5).build();
                            setSlot(3, 4, is5, "3", TextColors.AQUA, "Slot: 3" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp3 + "\nIVs: " + p.get(2).getIVs().getPercentage(1) + "\nNature: " + p.get(2).getNature().toString() + "\nGrowth: " + p.get(2).getGrowth().toString() + "\nLevel: " + p.get(2).getLevel() + "\nGender: " + p.get(2).getGender() + "\nAbility: " + p.get(2).getAbilityName() + "\nHeld: " + p.get(2).getHeldItem().getDisplayName() + "\nForm: " + p.get(2).getFormEnum().getForm());
                        }
                        // Takasi gerceklestirecek kisinin 4. pokemonu
                        if (p.get(3) != null) {
                            String pp4 = p.get(3).getSpecies().name;
                            spec6 = PokemonSpec.from(pp4);
                            stack6 = ItemPixelmonSprite.getPhoto(spec6.create());
                            ItemStack test6 = ItemStackUtil.fromNative(stack6);
                            is6 = ItemStack.builder()
                                    .fromItemStack(test6).build();
                            setSlot(5, 4, is6, "4", TextColors.AQUA, "Slot: 4" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp4 + "\nIVs: " + p.get(3).getIVs().getPercentage(1) + "\nNature: " + p.get(3).getNature().toString() + "\nGrowth: " + p.get(3).getGrowth().toString() + "\nLevel: " + p.get(3).getLevel() + "\nGender: " + p.get(3).getGender() + "\nAbility: " + p.get(3).getAbilityName() + "\nHeld: " + p.get(3).getHeldItem().getDisplayName() + "\nForm: " + p.get(3).getFormEnum().getForm());
                        }
                        // Takasi gerceklestirecek kisinin 5. pokemonu
                        if (p.get(4) != null) {
                            String pp5 = p.get(4).getSpecies().name;
                            spec7 = PokemonSpec.from(pp5);
                            stack7 = ItemPixelmonSprite.getPhoto(spec7.create());
                            ItemStack test7 = ItemStackUtil.fromNative(stack7);
                            is7 = ItemStack.builder()
                                    .fromItemStack(test7).build();
                            setSlot(6, 4, is7, "5", TextColors.AQUA, "Slot: 5" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp5 + "\nIVs: " + p.get(4).getIVs().getPercentage(1) + "\nNature: " + p.get(4).getNature().toString() + "\nGrowth: " + p.get(4).getGrowth().toString() + "\nLevel: " + p.get(4).getLevel() + "\nGender: " + p.get(4).getGender() + "\nAbility: " + p.get(4).getAbilityName() + "\nHeld: " + p.get(4).getHeldItem().getDisplayName() + "\nForm: " + p.get(4).getFormEnum().getForm());
                        }
                        // Takasi gerceklestirecek kisinin 6. pokemonu
                        if (p.get(5) != null) {
                            String pp6 = p.get(5).getSpecies().name;
                            spec8 = PokemonSpec.from(pp6);
                            stack8 = ItemPixelmonSprite.getPhoto(spec8.create());
                            ItemStack test8 = ItemStackUtil.fromNative(stack8);
                            is8 = ItemStack.builder()
                                    .fromItemStack(test8).build();
                            setSlot(7, 4, is8, "6", TextColors.AQUA, "Slot: 6" + "\nOyuncu: " + player.getName() + "\nPokemon: " + pp6 + "\nIVs: " + p.get(5).getIVs().getPercentage(1) + "\nNature: " + p.get(5).getNature().toString() + "\nGrowth: " + p.get(5).getGrowth().toString() + "\nLevel: " + p.get(5).getLevel() + "\nGender: " + p.get(5).getGender() + "\nAbility: " + p.get(5).getAbilityName() + "\nHeld: " + p.get(5).getHeldItem().getDisplayName() + "\nForm: " + p.get(5).getFormEnum().getForm());
                        }
                    }

                }

            }catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt2 != null) statmt2.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect2 != null) connect2.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }

        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    private int sirax = 0;
    private int siray = 0;

    public void setSlot(Integer x, Integer y, ItemStack type, String name, TextColor renk, String lore ) {
        if(sirax == 9) {
            siray++;
            sirax = 0;
        }

        inventory.query(SlotPos.of(x, y)).set(ItemStack.builder()
                .from(type)
                .add(Keys.DISPLAY_NAME, Text.of(renk , name))
                .add(Keys.ITEM_LORE,  lore.isEmpty() ? Lists.newArrayList() : Lists.newArrayList(Text.of(lore)))
                .build());
        sirax++;
    }


    public Inventory getgui() {
        return this.inventory;
    }

}
