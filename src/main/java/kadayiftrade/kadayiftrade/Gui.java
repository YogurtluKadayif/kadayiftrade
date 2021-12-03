package kadayiftrade.kadayiftrade;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
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
import java.util.concurrent.TimeUnit;

public class Gui {
    public Inventory inventory;
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL3 = "jdbc:h2:./kadayiftrade/db";
    static final String USER = "sa";
    static final String PASS = "";
    Connection connect3 = null;
    Statement statmt3 = null;
    int sh = 0;
    private int sirax = 0;
    private int siray = 0;
    int sayfa = 1;


    public Gui(Player player_args, Player player_cmd_src) {
        this.inventory = Inventory.builder()
                .of(InventoryArchetypes.DOUBLE_CHEST)
                .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(TextColors.AQUA, "yTrade")))
                .property("inventorydimension", InventoryDimension.of(9, 5))
                .listener(ClickInventoryEvent.class, (ClickInventoryEvent event) -> {
                    event.setCancelled(true);
                    ItemStack affected;
                    if (event.getSlot() != null && !(event.getTransactions().isEmpty())) {
                        affected = event.getTransactions().get(0).getOriginal().createStack();
                        if (event instanceof ClickInventoryEvent.Shift && (affected.getType() == ItemTypes.AIR || affected.getType() == ItemTypes.NONE)) {
                            affected = event.getTransactions().get(0).getDefault().createStack();
                        }
                        if (affected.getType() == PixelmonItems.itemPixelmonSprite) {
                            String inp = affected.get(Keys.DISPLAY_NAME).toString();
                            String out = inp.replaceAll("[^0-9?!\\.]", "");
                            int oint = Integer.parseInt(out);

                            final Player player = (Player) player_args;
                            final Integer kod = oint;
                            final Gui2 gui = new Gui2(player, kod);

                            Sponge.getScheduler().createTaskBuilder()
                                    .execute(() -> player.closeInventory())
                                    .delay(1, TimeUnit.SECONDS)
                                    .submit(Kadayiftrade.instance);

                            Sponge.getScheduler().createTaskBuilder()
                                    .execute(() -> player.openInventory(gui.getgui()))
                                    .delay(2, TimeUnit.SECONDS)
                                    .submit(Kadayiftrade.instance);

                            final UUID uuid = player.getUniqueId();
                            if (!Kadayiftrade.pSlot.containsKey(uuid)) {
                                Kadayiftrade.pSlot.put(uuid, Maps.newHashMap());
                            }

                        } else {
                            if (affected.getType() == PixelmonItems.tradeHolderRight) {
                                String inp = affected.get(Keys.DISPLAY_NAME).toString();
                                if (inp.contains("Sonraki Sayfa")) {
                                    inventory.clear();
                                    try {
                                        connect3 = DriverManager.getConnection(DB_URL3, USER, PASS);
                                        statmt3 = connect3.createStatement();
                                        try {
                                            Class.forName(JDBC_DRIVER);

                                            sayfa++;
                                            int sl = sayfa * 35;
                                            int sli = sl - 35 + 1;

                                            String sqlara3 = "SELECT * FROM Istenenler LIMIT 35 OFFSET " + sli;
                                            ResultSet rsara3 = statmt3.executeQuery(sqlara3);
                                            net.minecraft.item.ItemStack stack = null;
                                            ItemStack is = null;
                                            PokemonSpec spec = null;

                                            int srx = 0;
                                            int sry = 0;

                                            sirax = 0;
                                            siray = 0;
                                            while (rsara3.next()) {
                                                if (srx != 8 && sry != 3) {
                                                    srx++;
                                                    sry++;
                                                    sh++;


                                                    stack = null;
                                                    is = null;
                                                    spec = PokemonSpec.from(rsara3.getString("pokemon"));
                                                    stack = ItemPixelmonSprite.getPhoto(spec.create());
                                                    ItemStack test = ItemStackUtil.fromNative(stack);

                                                    is = ItemStack.builder()
                                                            .fromItemStack(test).build();
                                                    setSlot(is, String.valueOf(rsara3.getInt("kod")), TextColors.AQUA, "ID: " + rsara3.getInt("id") + "\nOyuncu: " + rsara3.getString("isim") + "\nPokemon: " + rsara3.getString("pokemon") + "\nIVs: " + rsara3.getString("ivs") + "\nNature: " + rsara3.getString("nature") + "\nGrowth: " + rsara3.getString("growth") + "\nLevel: " + rsara3.getInt("level") + "\nGender: " + rsara3.getString("gender") + "\nAbility: " + rsara3.getString("ability") + "\nHeld: " + rsara3.getString("held") + "\nForm: " + rsara3.getInt("form") + "\nShiny: " + rsara3.getInt("shiny"));

                                                }
                                            }

                                        } catch (SQLException se) {
                                            se.printStackTrace();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            try {
                                                if (statmt3 != null) statmt3.close();
                                            } catch (SQLException se2) {
                                            }
                                            try {
                                                if (connect3 != null) connect3.close();
                                            } catch (SQLException se) {
                                                se.printStackTrace();
                                            }
                                        }

                                    } catch (SQLException se) {
                                        se.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    }
                })
                .build(Kadayiftrade.instance);
        inventory.query(SlotPos.of(8, 4)).set(ItemStack.builder()
                .from(ItemStack.of((ItemType) PixelmonItems.tradeHolderRight))
                .add(Keys.DISPLAY_NAME, Text.of(TextColors.WHITE, "Sonraki Sayfa"))
                .build());
        try {
            connect3 = DriverManager.getConnection(DB_URL3, USER, PASS);
            statmt3 = connect3.createStatement();
            try {
                Class.forName(JDBC_DRIVER);

                String sqlara3 = "SELECT * FROM Istenenler LIMIT 36";
                ResultSet rsara3 = statmt3.executeQuery(sqlara3);
                rsara3.next();
                net.minecraft.item.ItemStack stack = null;
                ItemStack is = null;
                PokemonSpec spec = null;

                int srx = 0;
                int sry = 0;


                while(rsara3.next()) {
                    if (srx != 8 && sry != 3) {
                        srx++;
                        sry++;
                        sh++;

                        stack = null;
                        is = null;
                        spec = PokemonSpec.from(rsara3.getString("pokemon"));
                        stack = ItemPixelmonSprite.getPhoto(spec.create());
                        ItemStack test = ItemStackUtil.fromNative(stack);


                        is = ItemStack.builder()
                                .fromItemStack(test).build();
                        setSlot(is, String.valueOf(rsara3.getInt("kod")), TextColors.AQUA, "ID: " + rsara3.getInt("id") + "\nOyuncu: " + rsara3.getString("isim") + "\nPokemon: " + rsara3.getString("pokemon") + "\nIVs: " + rsara3.getString("ivs") + "\nNature: " + rsara3.getString("nature") + "\nGrowth: " + rsara3.getString("growth") + "\nLevel: " + rsara3.getInt("level") + "\nGender: " + rsara3.getString("gender") + "\nAbility: " + rsara3.getString("ability") + "\nHeld: " + rsara3.getString("held") + "\nForm: " + rsara3.getInt("form") + "\nShiny: " + rsara3.getInt("shiny"));
                    }
                }

            }catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt3 != null) statmt3.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect3 != null) connect3.close();
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



    public void setSlot(ItemStack type, String name, TextColor renk, String lore ) {
        if(sirax == 8 && siray == 4) {
            sirax = 9;
            siray = 5;
        }
        if(sirax == 9) {
            siray++;
            sirax = 0;
        }


        inventory.query(SlotPos.of(sirax, siray)).set(ItemStack.builder()
                .from(type)
                .add(Keys.DISPLAY_NAME, Text.of(renk , name))
                .add(Keys.ITEM_LORE,  lore.isEmpty() ? Lists.newArrayList() : Lists.newArrayList(Text.of(lore)))
                .build());
        sirax++;
    }

    public Inventory getbackpack() {
        return this.inventory;
    }

}
