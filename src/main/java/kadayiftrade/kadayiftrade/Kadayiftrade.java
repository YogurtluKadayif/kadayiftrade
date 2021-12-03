package kadayiftrade.kadayiftrade;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.pokemon.MovesetEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.BattleQuery;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.AttackBase;
import com.pixelmonmod.pixelmon.client.gui.battles.AttackData;
import com.pixelmonmod.pixelmon.commands.Battle;
import com.pixelmonmod.pixelmon.config.PixelmonItemsHeld;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.AttackTypeAdapter;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.pokedex.Pokedex;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.sql.*;
import java.util.*;

@Plugin(
        id = "kadayiftrade",
        name = "Kadayiftrade",
        description = "YogurtluKadayif Pixelmon Trade Plugin",
        authors = {
                "YogurtluKadayif"
        }
)
public class Kadayiftrade {

    public static String pokeList = ", bulbasaur, ivysaur, venusaur, charmander, charmeleon, charizard, squirtle, wartortle, blastoise, caterpie, metapod, butterfree, weedle, kakuna, beedrill, pidgey, pidgeotto, pidgeot, rattata, raticate, spearow, fearow, ekans, arbok, pikachu, raichu, sandshrew, sandslash, nidoran♀, nidorina, nidoqueen, nidoran♂, nidorino, nidoking, clefairy, clefable, vulpix, ninetales, jigglypuff, wigglytuff, zubat, golbat, oddish, gloom, vileplume, paras, parasect, venonat, venomoth, diglett, dugtrio, meowth, persian, psyduck, golduck, mankey, primeape, growlithe, arcanine, poliwag, poliwhirl, poliwrath, abra, kadabra, alakazam, machop, machoke, machamp, bellsprout, weepinbell, victreebel, tentacool, tentacruel, geodude, graveler, golem, ponyta, rapidash, slowpoke, slowbro, magnemite, magneton, farfetch’d, doduo, dodrio, seel, dewgong, grimer, muk, shellder, cloyster, gastly, haunter, gengar, onix, drowzee, hypno, krabby, kingler, voltorb, electrode, exeggcute, exeggutor, cubone, marowak, hitmonlee, hitmonchan, lickitung, koffing, weezing, rhyhorn, rhydon, chansey, tangela, kangaskhan, horsea, seadra, goldeen, seaking, staryu, starmie, mr. mime, scyther, jynx, electabuzz, magmar, pinsir, tauros, magikarp, gyarados, lapras, ditto, eevee, vaporeon, jolteon, flareon, porygon, omanyte, omastar, kabuto, kabutops, aerodactyl, snorlax, articuno, zapdos, moltres, dratini, dragonair, dragonite, mewtwo, mew, chikorita, bayleef, meganium, cyndaquil, quilava, typhlosion, totodile, croconaw, feraligatr, sentret, furret, hoothoot, noctowl, ledyba, ledian, spinarak, ariados, crobat, chinchou, lanturn, pichu, cleffa, igglybuff, togepi, togetic, natu, xatu, mareep, flaaffy, ampharos, bellossom, marill, azumarill, sudowoodo, politoed, hoppip, skiploom, jumpluff, aipom, sunkern, sunflora, yanma, wooper, quagsire, espeon, umbreon, murkrow, slowking, misdreavus, unown, wobbuffet, girafarig, pineco, forretress, dunsparce, gligar, steelix, snubbull, granbull, qwilfish, scizor, shuckle, heracross, sneasel, teddiursa, ursaring, slugma, magcargo, swinub, piloswine, corsola, remoraid, octillery, delibird, mantine, skarmory, houndour, houndoom, kingdra, phanpy, donphan, porygon2, stantler, smeargle, tyrogue, hitmontop, smoochum, elekid, magby, miltank, blissey, raikou, entei, suicune, larvitar, pupitar, tyranitar, lugia, ho-oh, celebi, treecko, grovyle, sceptile, torchic, combusken, blaziken, mudkip, marshtomp, swampert, poochyena, mightyena, zigzagoon, linoone, wurmple, silcoon, beautifly, cascoon, dustox, lotad, lombre, ludicolo, seedot, nuzleaf, shiftry, taillow, swellow, wingull, pelipper, ralts, kirlia, gardevoir, surskit, masquerain, shroomish, breloom, slakoth, vigoroth, slaking, nincada, ninjask, shedinja, whismur, loudred, exploud, makuhita, hariyama, azurill, nosepass, skitty, delcatty, sableye, mawile, aron, lairon, aggron, meditite, medicham, electrike, manectric, plusle, minun, volbeat, illumise, roselia, gulpin, swalot, carvanha, sharpedo, wailmer, wailord, numel, camerupt, torkoal, spoink, grumpig, spinda, trapinch, vibrava, flygon, cacnea, cacturne, swablu, altaria, zangoose, seviper, lunatone, solrock, barboach, whiscash, corphish, crawdaunt, baltoy, claydol, lileep, cradily, anorith, armaldo, feebas, milotic, castform, kecleon, shuppet, banette, duskull, dusclops, tropius, chimecho, absol, wynaut, snorunt, glalie, spheal, sealeo, walrein, clamperl, huntail, gorebyss, relicanth, luvdisc, bagon, shelgon, salamence, beldum, metang, metagross, regirock, regice, registeel, latias, latios, kyogre, groudon, rayquaza, jirachi, deoxys, turtwig, grotle, torterra, chimchar, monferno, infernape, piplup, prinplup, empoleon, starly, staravia, staraptor, bidoof, bibarel, kricketot, kricketune, shinx, luxio, luxray, budew, roserade, cranidos, rampardos, shieldon, bastiodon, burmy, wormadam, mothim, combee, vespiquen, pachirisu, buizel, floatzel, cherubi, cherrim, shellos, gastrodon, ambipom, drifloon, drifblim, buneary, lopunny, mismagius, honchkrow, glameow, purugly, chingling, stunky, skuntank, bronzor, bronzong, bonsly, mime jr., happiny, chatot, spiritomb, gible, gabite, garchomp, munchlax, riolu, lucario, hippopotas, hippowdon, skorupi, drapion, croagunk, toxicroak, carnivine, finneon, lumineon, mantyke, snover, abomasnow, weavile, magnezone, lickilicky, rhyperior, tangrowth, electivire, magmortar, togekiss, yanmega, leafeon, glaceon, gliscor, mamoswine, porygon-z, gallade, probopass, dusknoir, froslass, rotom, uxie, mesprit, azelf, dialga, palkia, heatran, regigigas, giratina, cresselia, phione, manaphy, darkrai, shaymin, arceus, victini, snivy, servine, serperior, tepig, pignite, emboar, oshawott, dewott, samurott, patrat, watchog, lillipup, herdier, stoutland, purrloin, liepard, pansage, simisage, pansear, simisear, panpour, simipour, munna, musharna, pidove, tranquill, unfezant, blitzle, zebstrika, roggenrola, boldore, gigalith, woobat, swoobat, drilbur, excadrill, audino, timburr, gurdurr, conkeldurr, tympole, palpitoad, seismitoad, throh, sawk, sewaddle, swadloon, leavanny, venipede, whirlipede, scolipede, cottonee, whimsicott, petilil, lilligant, basculin, sandile, krokorok, krookodile, darumaka, darmanitan, maractus, dwebble, crustle, scraggy, scrafty, sigilyph, yamask, cofagrigus, tirtouga, carracosta, archen, archeops, trubbish, garbodor, zorua, zoroark, minccino, cinccino, gothita, gothorita, gothitelle, solosis, duosion, reuniclus, ducklett, swanna, vanillite, vanillish, vanilluxe, deerling, sawsbuck, emolga, karrablast, escavalier, foongus, amoonguss, frillish, jellicent, alomomola, joltik, galvantula, ferroseed, ferrothorn, klink, klang, klinklang, tynamo, eelektrik, eelektross, elgyem, beheeyem, litwick, lampent, chandelure, axew, fraxure, haxorus, cubchoo, beartic, cryogonal, shelmet, accelgor, stunfisk, mienfoo, mienshao, druddigon, golett, golurk, pawniard, bisharp, bouffalant, rufflet, braviary, vullaby, mandibuzz, heatmor, durant, deino, zweilous, hydreigon, larvesta, volcarona, cobalion, terrakion, virizion, tornadus, thundurus, reshiram, zekrom, landorus, kyurem, keldeo, meloetta, genesect, chespin, quilladin, chesnaught, fennekin, braixen, delphox, froakie, frogadier, greninja, bunnelby, diggersby, fletchling, fletchinder, talonflame, scatterbug, spewpa, vivillon, litleo, pyroar, flabébé, floette, florges, skiddo, gogoat, pancham, pangoro, furfrou, espurr, meowstic, honedge, doublade, aegislash, spritzee, aromatisse, swirlix, slurpuff, inkay, malamar, binacle, barbaracle, skrelp, dragalge, clauncher, clawitzer, helioptile, heliolisk, tyrunt, tyrantrum, amaura, aurorus, sylveon, hawlucha, dedenne, carbink, goomy, sliggoo, goodra, klefki, phantump, trevenant, pumpkaboo, gourgeist, bergmite, avalugg, noibat, noivern, xerneas, yveltal, zygarde, diancie, hoopa, volcanion, rowlet, dartrix, decidueye, litten, torracat, incineroar, popplio, brionne, primarina, pikipek, trumbeak, toucannon, yungoos, gumshoos, grubbin, charjabug, vikavolt, crabrawler, crabominable, oricorio, cutiefly, ribombee, rockruff, lycanroc, wishiwashi, mareanie, toxapex, mudbray, mudsdale, dewpider, araquanid, fomantis, lurantis, morelull, shiinotic, salandit, salazzle, stufful, bewear, bounsweet, steenee, tsareena, comfey, oranguru, passimian, wimpod, golisopod, sandygast, palossand, pyukumuku, type: null, silvally, minior, komala, turtonator, togedemaru, mimikyu, bruxish, drampa, dhelmise, jangmo-o, hakamo-o, kommo-o, tapu koko, tapu lele, tapu bulu, tapu fini, cosmog, cosmoem, solgaleo, lunala, nihilego, buzzwole, pheromosa, xurkitree, celesteela, kartana, guzzlord, necrozma, magearna, marshadow, poipole, naganadel, stakataka, blacephalon, zeraora, meltan, melmetal, grookey, thwackey, rillaboom, scorbunny, raboot, cinderace, sobble, drizzile, inteleon, skwovet, greedent, rookidee, corvisquire, corviknight, blipbug, dottler, orbeetle, nickit, thievul, gossifleur, eldegoss, wooloo, dubwool, chewtle, drednaw, yamper, boltund, rolycoly, carkol, coalossal, applin, flapple, appletun, silicobra, sandaconda, cramorant, arrokuda, barraskewda, toxel, toxtricity, sizzlipede, centiskorch, clobbopus, grapploct, sinistea, polteageist, hatenna, hattrem, hatterene, impidimp, morgrem, grimmsnarl, obstagoon, perrserker, cursola, sirfetch’d, mr. rime, runerigus, milcery, alcremie, falinks, pincurchin, snom, frosmoth, stonjourner, eiscue, indeedee, morpeko, cufant, copperajah, dracozolt, arctozolt, dracovish, arctovish, duraludon, dreepy, drakloak, dragapult, zacian, zamazenta, eternatus, kubfu, urshifu, zarude";
    public static String natureList = ", hardy, lonely, brave, adamant, naughty, bold, docile, relaxed, impish, lax, timid, hasty, serious, jolly, naive, modest, mild, quiet, bashful, rash, calm, gentle, sassy, careful, quirky, fe";
    public static String growthList = ", microscopic, pygmy, runt, small, ordinary, huge, giant, enormous, ginormous, fe";
    public static String abilityList = ", adaptability, aerilate, aftermath, airLock, analytic, angerpoint, anticipation, arenatrap, aromaveil, aurabreak, baddreams, battery, battlearmor, battlebond, beastboost, berserk, bigpecks, blaze, bulletproof, cheekpouch, chlorophyll, clearbody, cloudnine, colorchange, comatose, competitive, compoundeyes, contrary, corrosion, cursedbody, cutecharm, damp, dancer, darkaura, dazzling, defeatist, defiant, deltastream, desolateLand, disguise, download, drizzle, drought, dryskin, earlybird, effectSpore, electricsurge, emergencyexit, fairyaura, filter, flamebody, flareboost, flashfire, flowergift, fluffy, forecast, forewarn, friendguard, frisk, fullmetalbody, furcoat, galewings, galvanize, gluttony, gooey, grass pelt, grassysurge, guts, harvest, healer, heatproof, heavymetal, honeygather, hugepower, hustle, hydration, hypercutter, icebody, illuminate, illusion, immunity, imposter, infiltrator, innardsout, innerfocus, insomnia, intimidate, ironbarbs, ironfist, justified, keeneye, klutz, leafguard, levitate, lightmetal, lightningrod, limber, liquidooze, liquidvoice, longreach, magicbounce, magicguard, magician, magmaarmor, magnetpull, marvelscale, megalauncher, merciless, minus, mistysurge, moldbreaker, moody, motordrive, moxie, multiscale, multitype, mummy, naturalcure, neuroforce, noguard, normalize, oblivious, overcoat, overgrow, owntempo, parentalbond, pickpocket, pickup, pixilate, plus, poisonheal, poisonpoint, poisontouch, powerconstruct, powerofalchemy, prankster, pressure, primordialsea, prismarmor, protean, psychicsurge, purepower, queenlymajesty, quickfeet, raindish, rattled, receiver, reckless, refrigerate, regenerator, rivalry, rkssystem, rockhead, roughskin, runaway, sandforce, sandrush, sandstream, sandveil, sapsipper, schooling, scrappy, serenegrace, shadowshield, shadowtag, shedskin, sheerforce, shellarmor, shielddust, shieldsdown, simple, skilllink, slowstart, slushrush, sniper, snowcloak, snowwarning, solarpower, solidrock, soul-heart, soundproof, speedboost, stakeout, stall, stamina, stancechange, static, steadfast, steelworker, stench, stickyhold, stormdrain, strongjaw, sturdy, suctioncups, superluck, surgesurfer, swarm, sweetveil, swiftswim, symbiosis, synchronize, tangledfeet, tanglinghair, technician, telepathy, teravolt, thickfat, tintedlens, torrent, toughclaws, toxicboost, trace, triage, truant, turboblaze, unaware, unburden, unnerve, victorystar, vitalspirit, voltabsorb, waterabsorb, waterbubble, watercompaction, waterveil, weakarmor, whitesmoke, wimpout, wonderguard, wonderskin, zenmode, gorillatactics, screencleaner, corrosion, innardsout, propellertail, iceface, fe";
    public static String genderList= ", male, female, genderless, fe";
    public static String heldList = ", ability capsule, abomasite, absolite, absorb bulb, adamant orb, adrenaline orb, aerodactylite, aggronite, air balloon, alakazite, aloraichium z, altarianite, ampharosite, amulet coin, assault vest, audinite, banettite, beedrillite, berry sweet, big root, binding band, black belt, black glasses, black sludge, blastoisinite, blazikenite, blunder policy, bright powder, bug gem, bug memory, buginium z, burn drive, cameruptite, cell battery, charcoal, charizardite x, charizardite y, chill drive, chipped pot, choice band, choice scarf, choice specs, cleanse tag, clover sweet, colress machine, cracked pot, damp rock, dark gem, dark memory, darkinium z, decidium z, deep sea scale, deep sea tooth, destiny knot, diancite, douse drive, draco plate, dragon fang, dragon gem, dragon memory, dragonium z, dread plate, dropped item, earth plate, eevium z, eject button, eject pack, electric gem, electric memory, electric seed, electrium z, elevator key, everstone, eviolite, exp. share, expert belt, fairium z, fairy memory, fighting gem, fighting memory, fightinium z, fire gem, fire memory, firium z, fist plate, flame orb, flame plate, float stone, flower sweet, flying gem, flying memory, flyinium z, focus band, focus sash, full incense, galladite, garchompite, gardevoirite, gengarite, ghost gem, ghost memory, ghostium z, glalitite, grass gem, grass memory, grassium z, grassy seed, grip claw, griseous orb, ground gem, ground memory, groundium z, grubby hanky, gyaradosite, hard stone, heat rock, heavy-duty boots, heracronite, hi-tech earbuds, honor of kalos, houndoominite, ice gem, ice memory, icicle plate, icium z, icy rock, incinium z, insect plate, intriguing stone, iron ball, iron plate, kangaskhanite, king's rock, kommonium z, lagging tail, latiasite, latiosite, lax incense, leek, leftovers, life orb, light ball, light clay, looker ticket, lopunnite, love sweet, lucarionite, luck incense, lucky egg, lucky punch, luminous moss, lunalium z, lustrous orb, lycanium z, macho brace, magnet, manectite, marshadium z, mawilite, meadow plate, medichamite, mental herb, metagrossite, metal coat, metal powder, metronome, mewnium z, mewtwonite x, mewtwonite y, mimikium z, mind plate, miracle seed, misty seed, muscle band, mystic water, never-melt ice, normal gem, normalium z, odd incense, pass orb, pidgeotite, pikanium z, pikashunium z, pink nectar, pinsirite, pixie plate, plasma card, poison barb, poison gem, poison memory, poisonium z, power anklet, power band, power belt, power bracer, power herb, power lens, power plant pass, power weight, primarium z, prison bottle, prof's letter, protective pads, psychic gem, psychic memory, psychic seed, psychium z, pure incense, purple nectar, quick claw, quick powder, razor claw, razor fang, red card, red nectar, ribbon sweet, ring target, rock gem, rock incense, rock memory, rockium z, rocky helmet, room service, rose incense, sablenite, safety goggles  salamencite, sceptilite, scizorite, scope lens, sea incense, sharp beak, sharpedonite, shed shell, shell bell, shock drive, silk scarf, silver powder, sky plate, slowbronite, smoke ball, smooth rock, snorlium z, snowball, soft sand, solganium z, soothe bell, soul dew, spell tag, splash plate, spooky plate, star sweet, steel gem, steel memory, steelium z, steelixite, sticky barb, stone plate, strawberry sweet, swampertite, sweet apple, tapunium z, tart apple, terrain extender, thick club, throat spray, toxic orb, toxic plate, twisted spoon, tyranitarite, ultranecrozium z, utility umbrella, venusaurite, water gem, water memory, waterium z, wave incense, weakness policy, white herb, wide lens, wise glasses, yellow nectar, zap plate, zoom lens";

    public static String json = "";

    @Inject
    public static Kadayiftrade instance;
    @Nonnull
    private Game game = null;

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL3 = "jdbc:h2:./kadayiftrade/db";

    static final String USER = "sa";
    static final String PASS = "";


    @Nonnull public static Map<UUID, Map<String, String>> pSlot = null;

    Connection connect = null;
    Statement statmt = null;
    Connection connect2 = null;
    Statement statmt2 = null;
    Connection connect3 = null;
    Statement statmt3 = null;


    @Inject
    public Kadayiftrade()
    {
        instance = this;
    }


    public void jsonReadAbility(String id, String fo, String type) {
        InputStream stream = instance.getClass().getClassLoader().getResourceAsStream("assets/kadayiftrade/" + id + ".json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            String restr = "";
            int ii = -1;
            while(reader.ready() ) {

                if(type == "ability") {
                    if (reader.readLine().contains("abilities")) {
                        if(ii == Integer.valueOf(fo)) {
                            break;
                        }
                        restr = reader.readLine().toLowerCase() + reader.readLine().toLowerCase() + reader.readLine().toLowerCase();
                        ii++;
                        //System.out.println(restr);
                        restr.replaceAll("  ", "");
                    }
                } else {
                    if (type == "gender") {
                        if (reader.readLine().contains("catchRate")) {
                            String repl = reader.readLine();
                            String[] rpl = repl.split(" ");
                            //System.out.println(rpl[3].replaceAll(",", ""));
                            restr = rpl[3].replaceAll(",", "");
                        }
                    } else {
                        if(type == "form") {
                            int i = 0;
                            while(reader.ready()) {
                                if(reader.readLine().contains("\"" + (i + 1) + "\":")) {
                                    i++;
                                    //System.out.println(i);
                                    restr = "" + i;
                                }
                            }
                        }
                    }
                }
            }
            if(restr == "") {
                restr = "" + 0;
            }
            restr.toLowerCase();
            json = restr;
            reader.close();
            //System.out.println(json);
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }


    @Listener
    public void onServerStart(GamePreInitializationEvent event) {
        this.game = game;
        this.pSlot = Maps.newHashMap();
        Connection connect = null;
        Statement statmt = null;

        try {
            File file = new File(DB_URL3);
            if (file.exists()) {
                System.out.print("Database kaydi mevcut.");
            } else {
                Class.forName(JDBC_DRIVER);
                System.out.println("Database'e baglaniliyor.");
                connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                statmt = connect.createStatement();
            }

            File file2 = new File("./kadayiftrade/db.trace.db");
            if (file2.exists()) {
                file2.delete();
                System.out.println("Trace dosyasi silindi.");
            } else {
                System.out.println("Trace dosyasi bulunamadi.");
            }

            String sql = "CREATE TABLE IF NOT EXISTS   Istenenler " +
                    "(id INTEGER not NULL, " +
                    " isim VARCHAR(255)," +
                    " pokemon VARCHAR(255), " +
                    " ivs VARCHAR(255), " +
                    " nature VARCHAR(255), " +
                    " growth VARCHAR(255), " +
                    " level INTEGER, " +
                    " ability VARCHAR(255), " +
                    " gender VARCHAR(255), " +
                    " ivstat VARCHAR(255), " +
                    " ortrainer VARCHAR(255), " +
                    " held VARCHAR(255), " +
                    " form INTEGER, " +
                    " shiny INTEGER, " +
                    " evstat VARCHAR(255), " +
                    " moves VARCHAR(255), " +
                    " uuid VARCHAR(255), " +
                    " kod INTEGER)";
            statmt.executeUpdate(sql);

            String sql2 = "CREATE TABLE IF NOT EXISTS   Verilenler " +
                    "(id INTEGER not NULL, " +
                    " isim VARCHAR(255)," +
                    " pokemon VARCHAR(255), " +
                    " ivs VARCHAR(255), " +
                    " nature VARCHAR(255), " +
                    " growth VARCHAR(255), " +
                    " level INTEGER, " +
                    " ability VARCHAR(255), " +
                    " gender VARCHAR(255), " +
                    " ivstat VARCHAR(255), " +
                    " ortrainer VARCHAR(255), " +
                    " held VARCHAR(255), " +
                    " form INTEGER, " +
                    " shiny INTEGER, " +
                    " evstat VARCHAR(255), " +
                    " moves VARCHAR(255), " +
                    " uuid VARCHAR(255), " +
                    " kod INTEGER)";
            statmt.executeUpdate(sql2);

            String sql3 = "CREATE TABLE IF NOT EXISTS   Beklenenler " +
                    "(id INTEGER not NULL, " +
                    " isim VARCHAR(255)," +
                    " pokemon VARCHAR(255), " +
                    " ivs VARCHAR(255), " +
                    " nature VARCHAR(255), " +
                    " growth VARCHAR(255), " +
                    " level INTEGER, " +
                    " ability VARCHAR(255), " +
                    " gender VARCHAR(255), " +
                    " ivstat VARCHAR(255), " +
                    " ortrainer VARCHAR(255), " +
                    " held VARCHAR(255), " +
                    " form INTEGER, " +
                    " shiny INTEGER, " +
                    " evstat VARCHAR(255), " +
                    " moves VARCHAR(255), " +
                    " uuid VARCHAR(255), " +
                    " kod INTEGER)";
            statmt.executeUpdate(sql3);

            String isAra = "SELECT * FROM Verilenler";
            ResultSet rsarai = statmt.executeQuery(isAra);
            if(rsarai.next()) {
                connect3 = DriverManager.getConnection(DB_URL3, USER, PASS);
                statmt3 = connect3.createStatement();
                String sqlrs1 = "SELECT id FROM Beklenenler ORDER BY id DESC";
                ResultSet rs1 = statmt3.executeQuery(sqlrs1);

                rs1.next();
                int idr1 = rs1.getInt("id");
                int id = idr1 + 1;



                while(rsarai.next()) {
                    String sqlistenenler = "INSERT INTO Beklenenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = connect.prepareStatement(sqlistenenler);
                    preparedStatement.setInt(1,id);
                    preparedStatement.setString(2, rsarai.getString("isim"));
                    preparedStatement.setString(3, rsarai.getString("pokemon"));
                    preparedStatement.setString(4, null);
                    preparedStatement.setString(5,rsarai.getString("nature"));
                    preparedStatement.setString(6, rsarai.getString("growth"));
                    preparedStatement.setInt(7, rsarai.getInt("level"));
                    preparedStatement.setString(8, rsarai.getString("ability"));
                    preparedStatement.setString(9, rsarai.getString("gender"));
                    preparedStatement.setString(10, rsarai.getString("ivstat"));
                    preparedStatement.setString(11, rsarai.getString("ortrainer"));
                    preparedStatement.setString(12, rsarai.getString("held"));
                    preparedStatement.setInt(13, rsarai.getInt("form"));
                    preparedStatement.setInt(14, rsarai.getInt("shiny"));
                    preparedStatement.setString(15, rsarai.getString("evstat"));
                    preparedStatement.setString(16, rsarai.getString("moves"));
                    preparedStatement.setString(17, rsarai.getString("uuid"));
                    preparedStatement.setInt(18, rsarai.getInt("kod"));
                    preparedStatement.executeUpdate();
                }








                String sqlbasvurular = "DELETE FROM Istenenler";
                PreparedStatement preparedStatement2 = connect.prepareStatement(sqlbasvurular);
                preparedStatement2.executeUpdate();
            }
            String veAra = "SELECT * FROM Verilenler WHERE id='0'";
            ResultSet rsarav = statmt.executeQuery(veAra);
            if(rsarav.next()) {

                String sqlbasvurular = "DELETE FROM Verilenler";
                PreparedStatement preparedStatement2 = connect.prepareStatement(sqlbasvurular);
                preparedStatement2.executeUpdate();
            }



            String iAra = "SELECT * FROM Istenenler WHERE id='0'";
            ResultSet rsara = statmt.executeQuery(iAra);
            if(rsara.next() == false) {
                String sqlistenenler = "INSERT INTO Istenenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connect.prepareStatement(sqlistenenler);
                preparedStatement.setInt(1,0);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, null);
                preparedStatement.setString(4, null);
                preparedStatement.setString(5, null);
                preparedStatement.setString(6, null);
                preparedStatement.setInt(7, 0);
                preparedStatement.setString(8, null);
                preparedStatement.setString(9, null);
                preparedStatement.setString(10, null);
                preparedStatement.setString(11, null);
                preparedStatement.setString(12, null);
                preparedStatement.setInt(13, 0);
                preparedStatement.setInt(14, 0);
                preparedStatement.setString(15, null);
                preparedStatement.setString(16, null);
                preparedStatement.setString(17, null);
                preparedStatement.setInt(18, 0);
                preparedStatement.executeUpdate();
            }

            String vAra = "SELECT * FROM Verilenler WHERE id='0'";
            ResultSet rsara2 = statmt.executeQuery(vAra);
            if(rsara2.next() == false) {
                String sqlistenenler = "INSERT INTO Verilenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connect.prepareStatement(sqlistenenler);
                preparedStatement.setInt(1,0);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, null);
                preparedStatement.setString(4, null);
                preparedStatement.setString(5, null);
                preparedStatement.setString(6, null);
                preparedStatement.setInt(7, 0);
                preparedStatement.setString(8, null);
                preparedStatement.setString(9, null);
                preparedStatement.setString(10, null);
                preparedStatement.setString(11, null);
                preparedStatement.setString(12, null);
                preparedStatement.setInt(13, 0);
                preparedStatement.setInt(14, 0);
                preparedStatement.setString(15, null);
                preparedStatement.setString(16, null);
                preparedStatement.setString(17, null);
                preparedStatement.setInt(18, 0);
                preparedStatement.executeUpdate();
            }

            String bAra = "SELECT * FROM Beklenenler WHERE id='0'";
            ResultSet rsara3 = statmt.executeQuery(bAra);
            if(rsara3.next() == false) {
                String sqlistenenler = "INSERT INTO Beklenenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connect.prepareStatement(sqlistenenler);
                preparedStatement.setInt(1,0);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, null);
                preparedStatement.setString(4, null);
                preparedStatement.setString(5, null);
                preparedStatement.setString(6, null);
                preparedStatement.setInt(7, 0);
                preparedStatement.setString(8, null);
                preparedStatement.setString(9, null);
                preparedStatement.setString(10, null);
                preparedStatement.setString(11, null);
                preparedStatement.setString(12, null);
                preparedStatement.setInt(13, 0);
                preparedStatement.setInt(14, 0);
                preparedStatement.setString(15, null);
                preparedStatement.setString(16, null);
                preparedStatement.setString(17, null);
                preparedStatement.setInt(18, 0);
                preparedStatement.executeUpdate();
            }

            System.out.println("Verilen databasede tableler olusturuldu.");
            statmt.close();
            statmt.close();

        }catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(statmt!=null) statmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(statmt!=null) statmt.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    @Listener
    public void onInit(@Nullable final GameInitializationEvent e) {
        Sponge.getCommandManager().register(instance, myCommandSpec, "ytrade");
    }


    @Listener
    public void onServerStop(GameStoppingServerEvent event){
        try{
            if(statmt!=null) statmt.close();
            if(statmt2!=null) statmt2.close();
            if(statmt3!=null) statmt3.close();
        } catch(SQLException se2) {
        }
        try {
            if(connect!=null) connect.close();
            if(connect2!=null) connect2.close();
            if(connect3!=null) connect3.close();
        } catch(SQLException se){
            se.printStackTrace();
        }
    }


    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        try {
            connect = DriverManager.getConnection(DB_URL3, USER, PASS);
            statmt = connect.createStatement();
            try {
                String sqlara = "SELECT * FROM Beklenenler WHERE isim='" + event.getTargetEntity().getName() + "'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    connect3 = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt3 = connect3.createStatement();

                    String sqlrs1 = "SELECT id FROM Beklenenler ORDER BY id DESC";
                    ResultSet rs1 = statmt3.executeQuery(sqlrs1);

                    int id = -1;
                    while(rs1.next()) {
                        //int idr1 = rs1.getInt("id");
                        id = id + 1;
                    }

                    event.getTargetEntity().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, " Su kadar pokemonunuz beklemede: ", TextColors.BLUE, id, TextColors.GREEN, ". Almak icin /ytrade tw yaziniz." ));
                }


            }catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                    if (statmt2 != null) statmt2.close();
                    if (statmt3 != null) statmt3.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                    if (connect2 != null) connect2.close();
                    if (connect3 != null) connect3.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    CommandSpec bilgi = CommandSpec.builder()
            .description(Text.of("yTrade plugininin bilgi ve uyari mesajlarini gosterir."))
            .permission("ytrade.bilgi")
            .executor((CommandSource src, CommandContext args) -> {
                src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "yTrade v1.0 by YogurtluKadayif"));
                src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Iletisim Discord: YogurtluKadayif#4837"));
                src.sendMessage(Text.of(TextColors.AQUA, "-", TextColors.GREEN, "Kendi pokemonunuza kendiniz sahip cikmalisiniz, eger yanlis pokemonu takasta verirseniz karsi taraf geri vermek zorunda degildir, yetkililer yardim edemez. Bu yuzden her seyi kontrol ettiginize dikkat edin."));
                src.sendMessage(Text.of(TextColors.AQUA, "-", TextColors.GREEN, "Yazilan ivs ve level minimumdur. Yazdiginiz ivsten ve levelden yukarisi kabul edilir. Nature, growth ve ability fark etmeyecek sekilde secilebilir, secmek icin fe yazmaniz gerekmektir. Formu fark etmeyecek sekilde secmek icin 00 yazmalisiniz."));
                src.sendMessage(Text.of(TextColors.AQUA, "-", TextColors.GREEN, "Bazi durumlarda takaslanan pokemonlarin original trainerleri ve moveleri degisebilir."));
                src.sendMessage(Text.of(TextColors.AQUA, "-", TextColors.DARK_PURPLE, "Form: 0 Normal, 1 Alolan - Shiny: 0 Normal, 1 Shiny"));
                src.sendMessage(Text.of(TextColors.AQUA, "->", TextColors.DARK_PURPLE, "Ornek kullanim asagidadir. Ornekte 1. slottaki pokemon verilecek olarak secilmis, karsi taraftan en az 90 ivs, adamant, huge, male ve torrent abilityli mudkip istenmistir."));
                src.sendMessage(Text.of(TextColors.AQUA, "/", TextColors.DARK_PURPLE, "ytrade ekle 1 mudkip 90 adamant huge 15 male 0 0 torrent"));
                src.sendMessage(Text.of(TextColors.AQUA, "->", TextColors.DARK_PURPLE, "Ornek 2:"));
                src.sendMessage(Text.of(TextColors.AQUA, "/", TextColors.DARK_PURPLE, "/ytrade ekle 2 mudkip 1 fe fe 1 male 00 0 torrent"));

                return CommandResult.success();
            })
            .build();




    CommandSpec oyuncu_sil = CommandSpec.builder()
            .description(Text.of("Takasi silmenizi saglar."))
            .permission("ytrade.sil")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("kod")))
            )
            .executor((CommandSource src, CommandContext args) -> {
                try {
                    Integer kod = args.<Integer>getOne("kod").get();
                    connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt = connect.createStatement();
                    connect2 = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt2 = connect2.createStatement();
                    try {
                        String sqlara = "SELECT * FROM Verilenler WHERE kod='" + kod + "'";
                        ResultSet rsara = statmt.executeQuery(sqlara);
                        String sqlara2 = "SELECT * FROM Istenenler WHERE kod='" + kod + "'";
                        ResultSet rsara2 = statmt2.executeQuery(sqlara2);

                        if (rsara.next() && rsara2.next()) {
                            if (rsara.getString("isim").equals(src.getName()) && rsara2.getString("isim").equals(src.getName())) {
                                connect3 = DriverManager.getConnection(DB_URL3, USER, PASS);
                                statmt3 = connect3.createStatement();

                                String sqlrs1 = "SELECT id FROM Beklenenler ORDER BY id DESC";
                                ResultSet rs1 = statmt3.executeQuery(sqlrs1);

                                rs1.next();
                                int idr1 = rs1.getInt("id");
                                int id = idr1 + 1;

                                String sqlistenenler = "INSERT INTO Beklenenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                PreparedStatement preparedStatement = connect3.prepareStatement(sqlistenenler);
                                preparedStatement.setInt(1, id);
                                preparedStatement.setString(2, rsara.getString("isim"));
                                preparedStatement.setString(3, rsara.getString("pokemon"));
                                preparedStatement.setString(4, null);
                                preparedStatement.setString(5, rsara.getString("nature"));
                                preparedStatement.setString(6, rsara.getString("growth"));
                                preparedStatement.setInt(7, rsara.getInt("level"));
                                preparedStatement.setString(8, rsara.getString("ability"));
                                preparedStatement.setString(9, rsara.getString("gender"));
                                preparedStatement.setString(10, rsara.getString("ivstat"));
                                preparedStatement.setString(11, rsara.getString("ortrainer"));
                                preparedStatement.setString(12, rsara.getString("held"));
                                preparedStatement.setInt(13, rsara.getInt("form"));
                                preparedStatement.setInt(14, rsara.getInt("shiny"));
                                preparedStatement.setString(15, rsara.getString("evstat"));
                                preparedStatement.setString(16, rsara.getString("moves"));
                                preparedStatement.setString(17, rsara.getString("uuid"));
                                preparedStatement.setInt(18, kod);
                                preparedStatement.executeUpdate();

                                String sqldel = "DELETE FROM Istenenler WHERE kod='" + kod + "'";
                                PreparedStatement preparedStatement3 = connect.prepareStatement(sqldel);
                                preparedStatement3.executeUpdate();
                                String sqldel2 = "DELETE FROM Verilenler WHERE kod='" + kod + "'";
                                PreparedStatement preparedStatement2 = connect2.prepareStatement(sqldel2);
                                preparedStatement2.executeUpdate();
                                src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, kod + " kodlu takasi sildiniz. /ytrade tw yazarak pokemonunuzu alabilirsiniz."));

                            } else {
                                src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, kod + " kodlu takas sizin degil."));
                            }
                        }

                    }catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                            if (statmt2 != null) statmt2.close();
                            if (statmt3 != null) statmt3.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                            if (connect2 != null) connect2.close();
                            if (connect3 != null) connect3.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                return CommandResult.success();
            })
            .build();

    CommandSpec admin_sil = CommandSpec.builder()
            .description(Text.of("Takasi silmenizi saglar."))
            .permission("admin.sil")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("kod")))
                    )
            .executor((CommandSource src, CommandContext args) -> {
                try {
                    Integer kod = args.<Integer>getOne("kod").get();
                    connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt = connect.createStatement();
                    connect2 = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt2 = connect2.createStatement();
                    try {
                        String sqlara = "SELECT * FROM Verilenler WHERE kod='" + kod + "'";
                        ResultSet rsara = statmt.executeQuery(sqlara);
                        String sqlara2 = "SELECT * FROM Istenenler WHERE kod='" + kod + "'";
                        ResultSet rsara2 = statmt2.executeQuery(sqlara2);

                        if(rsara.next() && rsara2.next()) {
                            connect3 = DriverManager.getConnection(DB_URL3, USER, PASS);
                            statmt3 = connect3.createStatement();

                            String sqlrs1 = "SELECT id FROM Beklenenler ORDER BY id DESC";
                            ResultSet rs1 = statmt3.executeQuery(sqlrs1);

                            rs1.next();
                            int idr1 = rs1.getInt("id");
                            int id = idr1 + 1;

                            String sqlistenenler = "INSERT INTO Beklenenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement preparedStatement = connect3.prepareStatement(sqlistenenler);
                            preparedStatement.setInt(1,id);
                            preparedStatement.setString(2, rsara.getString("isim"));
                            preparedStatement.setString(3, rsara.getString("pokemon"));
                            preparedStatement.setString(4, null);
                            preparedStatement.setString(5, rsara.getString("nature"));
                            preparedStatement.setString(6, rsara.getString("growth"));
                            preparedStatement.setInt(7, rsara.getInt("level"));
                            preparedStatement.setString(8, rsara.getString("ability"));
                            preparedStatement.setString(9, rsara.getString("gender"));
                            preparedStatement.setString(10, rsara.getString("ivstat"));
                            preparedStatement.setString(11,rsara.getString("ortrainer"));
                            preparedStatement.setString(12, rsara.getString("held"));
                            preparedStatement.setInt(13, rsara.getInt("form"));
                            preparedStatement.setInt(14, rsara.getInt("shiny"));
                            preparedStatement.setString(15, rsara.getString("evstat"));
                            preparedStatement.setString(16, rsara.getString("moves"));
                            preparedStatement.setString(17, rsara.getString("uuid"));
                            preparedStatement.setInt(18, kod);
                            preparedStatement.executeUpdate();

                            String sqldel = "DELETE FROM Istenenler WHERE kod='" + kod + "'";
                            PreparedStatement preparedStatement3 = connect.prepareStatement(sqldel);
                            preparedStatement3.executeUpdate();
                            String sqldel2 = "DELETE FROM Verilenler WHERE kod='" + kod + "'";
                            PreparedStatement preparedStatement2 = connect2.prepareStatement(sqldel2);
                            preparedStatement2.executeUpdate();
                            src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, kod + " kodlu takasi sildiniz."));

                        }


                    }catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                            if (statmt2 != null) statmt2.close();
                            if (statmt3 != null) statmt3.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                            if (connect2 != null) connect2.close();
                            if (connect3 != null) connect3.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                return CommandResult.success();
            })
            .build();


    CommandSpec al = CommandSpec.builder()
            .description(Text.of("Beklenen pokemonlarinizi almanizi saglar."))
            .permission("ytrade.wal")
            .executor((CommandSource src, CommandContext args) -> {
                try {
                    connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt = connect.createStatement();
                    try {
                        String sqlara = "SELECT * FROM Beklenenler WHERE isim='" + src.getName() + "'";
                        ResultSet rsara = statmt.executeQuery(sqlara);

                        while(rsara.next()) {
                            String pokei = rsara.getString("pokemon");
                            String naturei = rsara.getString("nature");
                            String growth = rsara.getString("growth");
                            int leveli = rsara.getInt("level");
                            String ability = rsara.getString("ability");
                            String gender = rsara.getString("gender");
                            String ivstat = rsara.getString("ivstat");
                            String evstat = rsara.getString("evstat");
                            String ortrainer = rsara.getString("ortrainer");
                            String held = rsara.getString("held");
                            int form = rsara.getInt("form");
                            String[] ivs = ivstat.split(" ");
                            String[] evs = evstat.split(" ");
                            int shiny = rsara.getInt("shiny");
                            String duid = rsara.getString("uuid");
                            String[] suid = duid.split(" ");
                            UUID puid = UUID.fromString(suid[0]);
                            String poname = suid[1];

                            String move = rsara.getString("moves");
                            move = move.replaceAll("\\[", "");
                            move = move.replaceAll("\\]", "");
                            String[] moves = move.split(", ");

                            //String moves = rsara.getString("moves");


                            PokemonSpec spec = PokemonSpec.from(pokei);
                            Pokemon pokemon = spec.create();
                            pokemon.setLevel(leveli);
                            pokemon.setNature(EnumNature.natureFromString(naturei.toString()));
                            pokemon.setGrowth(EnumGrowth.growthFromString(growth));
                            int ivsth = Integer.parseInt(ivs[0]);
                            int ivsta = Integer.parseInt(ivs[1]);
                            int ivstd = Integer.parseInt(ivs[2]);
                            int ivstsa = Integer.parseInt(ivs[3]);
                            int ivstsd = Integer.parseInt(ivs[4]);
                            int ivstsp = Integer.parseInt(ivs[5]);
                            int evsth = Integer.parseInt(evs[0]);
                            int evsta = Integer.parseInt(evs[1]);
                            int evstd = Integer.parseInt(evs[2]);
                            int evstsa = Integer.parseInt(evs[3]);
                            int evstsd = Integer.parseInt(evs[4]);
                            int evstsp = Integer.parseInt(evs[5]);

                            pokemon.getIVs().set(StatsType.HP, ivsth);
                            pokemon.getIVs().set(StatsType.Attack, ivsta);
                            pokemon.getIVs().set(StatsType.Defence, ivstd);
                            pokemon.getIVs().set(StatsType.SpecialAttack, ivstsa);
                            pokemon.getIVs().set(StatsType.SpecialDefence, ivstsd);
                            pokemon.getIVs().set(StatsType.Speed, ivstsp);
                            pokemon.getEVs().set(StatsType.HP, evsth);
                            pokemon.getEVs().set(StatsType.Attack, evsta);
                            pokemon.getEVs().set(StatsType.Defence, evstd);
                            pokemon.getEVs().set(StatsType.SpecialAttack, evstsa);
                            pokemon.getEVs().set(StatsType.SpecialDefence, evstsd);
                            pokemon.getEVs().set(StatsType.Speed, evstsp);
                            pokemon.setGender(Gender.getGender(gender));
                            pokemon.setAbilitySlot(Integer.valueOf(ability));
                            //pokemon.setAbility(String.valueOf(AbilityBase.getAbility(ability)));
                            pokemon.setForm(form);
                            if(shiny == 1) {
                                pokemon.setShiny(true);
                            }

                            int mlen1 = moves.length;
                            int mslot1 = 0;
                            int i1 = 0;
                            Attack at = new Attack("");
                            while (i1 < mlen1 && i1 < 3 && moves[i1] != null && !moves[i1].toString().equals("null")) {
                                at = new Attack(moves[i1]);
                                pokemon.getMoveset().set(mslot1, at);
                                mslot1++;
                                i1++;
                            }

                            //pokemon.setOriginalTrainer((EntityPlayerMP) Sponge.getServer().getPlayer(ortrainer).get());
                            if(heldList.contains(", " + held.toLowerCase() + ",")) {
                                ItemStack heldi = ItemStack.of((ItemType) PixelmonItemsHeld.getHeldItem(held));
                                net.minecraft.item.ItemStack heldn = ItemStackUtil.toNative(heldi);
                                pokemon.setHeldItem(heldn);
                            }
                            Pixelmon.storageManager.getPCForPlayer((EntityPlayerMP) Sponge.getServer().getPlayer(rsara.getString("isim")).get()).add(pokemon);
                            if(!String.valueOf(puid).equals("00000000-0000-0000-0000-000000000000")) {
                                pokemon.setOriginalTrainer(puid, poname);
                            }
                            String sqldel = "DELETE FROM Beklenenler WHERE id='" + rsara.getInt("id") + "'";
                            PreparedStatement preparedStatement3 = connect.prepareStatement(sqldel);
                            preparedStatement3.executeUpdate();
                            src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Pokemon PC'nize gonderildi, " + pokei));
                        }



                    }catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                            if (statmt2 != null) statmt2.close();
                            if (statmt3 != null) statmt3.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                            if (connect2 != null) connect2.close();
                            if (connect3 != null) connect3.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                return CommandResult.success();
            })
            .build();

    CommandSpec gui = CommandSpec.builder()
            .description(Text.of("Takas guisini acmanizi saglar."))
            .permission("ytrade.gui")
            .executor((CommandSource src, CommandContext args) -> {
                try {
                    final Player player_cmd_src = (Player)src;
                    final Player player_args = (Player)src;
                    final Gui gui = new Gui(player_args, player_cmd_src);
                    player_cmd_src.openInventory(gui.getbackpack());
                } catch(Exception e) {
                    e.printStackTrace();
                }

                return CommandResult.success();
            })
            .build();

    CommandSpec takas = CommandSpec.builder()
            .description(Text.of("Takas yapmanizi saglar."))
            .permission("ytrade.takas")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("kod"))),
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("slot")))
            )
            .executor((CommandSource src, CommandContext args) -> {
                try {
                    Integer kod = args.<Integer>getOne("kod").get();
                    Integer slot = args.<Integer>getOne("slot").get();

                    connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt = connect.createStatement();
                    try {
                        Class.forName(JDBC_DRIVER);

                        String sqlara = "SELECT * FROM Istenenler WHERE kod='" + kod + "'";
                        ResultSet rsara = statmt.executeQuery(sqlara);

                        connect2 = DriverManager.getConnection(DB_URL3, USER, PASS);
                        statmt2 = connect2.createStatement();

                        String sqlara2 = "SELECT * FROM Verilenler WHERE kod='" + kod + "'";
                        ResultSet rsara2 = statmt2.executeQuery(sqlara2);

                        if(rsara.next() && rsara2.next()) {
                            final UUID uuid = ((EntityPlayerMP) src).getUniqueID();
                            if (Kadayiftrade.pSlot.get(uuid) != null) {

                                Optional<Player> spl = Sponge.getServer().getPlayer(src.getName());
                                PlayerPartyStorage p = Pixelmon.storageManager.getParty(spl.get().getUniqueId());
                                if (p.get(slot) != null && p.countAll() != 1) {
                                    // Istenenler
                                    String pokei = rsara.getString("pokemon");
                                    double ivsi = Double.parseDouble(rsara.getString("ivs"));
                                    String naturei = rsara.getString("nature");
                                    String growth = rsara.getString("growth");
                                    int leveli = rsara.getInt("level");
                                    String ability = rsara.getString("ability");
                                    String gender = rsara.getString("gender");
                                    int form = rsara.getInt("form");
                                    int shiny = rsara.getInt("shiny");

                                    // Verilenler
                                    String pokev = rsara2.getString("pokemon");
                                    double ivsv = Double.parseDouble(rsara2.getString("ivs"));
                                    String naturev = rsara2.getString("nature");
                                    String growthv = rsara2.getString("growth");
                                    int levelv = rsara2.getInt("level");
                                    String abilityv = rsara2.getString("ability");
                                    String genderv = rsara2.getString("gender");
                                    int form2 = rsara2.getInt("form");
                                    int shiny2 = rsara2.getInt("shiny");
                                    String move = rsara2.getString("moves");
                                    move = move.replaceAll("\\[", "");
                                    move = move.replaceAll("\\]", "");
                                    String[] moves = move.split(", ");
                                    String duid = rsara2.getString("uuid");
                                    String[] suid = duid.split(" ");
                                    UUID puid = UUID.fromString(suid[0]);
                                    String poname = suid[1];

                                    //System.out.print(puid.toString());

                                    Pokemon srcvpl = p.get(slot);
                                    String poke = srcvpl.getSpecies().toString();
                                    EnumNature nature1 = srcvpl.getNature();
                                    EnumGrowth growth1 = srcvpl.getGrowth();
                                    int level1 = srcvpl.getLevel();
                                    String ability1 = srcvpl.getAbilityName();
                                    String gender1 = srcvpl.getGender().toString();
                                    int form1 = srcvpl.getFormEnum().getForm();
                                    Boolean shiny1 = srcvpl.isShiny();
                                    int ability1i = srcvpl.getAbilitySlot();
                                    Attack[] moves1 = srcvpl.getMoveset().attacks;



                                    if (p.get(slot).getSpecies() == EnumSpecies.get(pokei)) {
                                        double ib = srcvpl.getIVs().getPercentage(1);
                                        if (ib >= ivsi) {
                                            if (naturei.toLowerCase().equals(srcvpl.getNature().toString().toLowerCase()) || naturei.toLowerCase().equals("fe")) {
                                                if (growth.toLowerCase().equals(srcvpl.getGrowth().toString().toLowerCase()) || growth.toLowerCase().equals("fe")) {
                                                    if (leveli <= srcvpl.getLevel()) {
                                                        if (ability.toLowerCase().equals(ability1.toLowerCase()) || ability.toLowerCase().equals("fe")) {
                                                            if (gender.toLowerCase().equals(gender1.toLowerCase()) || gender.toLowerCase().equals("fe")) {
                                                                if (form == form1 || form == 00) {
                                                                    if ((shiny == 1 && shiny1 == true) || (shiny == 0 && shiny1 == false)) {
                                                                        // Takası isteyene gidecek pokemon - takası kabul edenden silinecek
                                                                        PokemonSpec spec = PokemonSpec.from(poke);
                                                                        Pokemon pokemon = spec.create();
                                                                        pokemon.setLevel(srcvpl.getLevel());
                                                                        pokemon.setNature(EnumNature.natureFromString(nature1.toString()));
                                                                        pokemon.setGrowth(growth1);
                                                                        int ivsth = srcvpl.getIVs().hp;
                                                                        int ivsta = srcvpl.getIVs().attack;
                                                                        int ivstd = srcvpl.getIVs().defence;
                                                                        int ivstsa = srcvpl.getIVs().specialAttack;
                                                                        int ivstsd = srcvpl.getIVs().specialDefence;
                                                                        int ivstsp = srcvpl.getIVs().speed;
                                                                        int evsth = srcvpl.getEVs().hp;
                                                                        int evsta = srcvpl.getEVs().attack;
                                                                        int evstd = srcvpl.getEVs().defence;
                                                                        int evstsa = srcvpl.getEVs().specialAttack;
                                                                        int evstsd = srcvpl.getEVs().specialDefence;
                                                                        int evstsp = srcvpl.getEVs().speed;

                                                                        pokemon.getIVs().set(StatsType.HP, ivsth);
                                                                        pokemon.getIVs().set(StatsType.Attack, ivsta);
                                                                        pokemon.getIVs().set(StatsType.Defence, ivstd);
                                                                        pokemon.getIVs().set(StatsType.SpecialAttack, ivstsa);
                                                                        pokemon.getIVs().set(StatsType.SpecialDefence, ivstsd);
                                                                        pokemon.getIVs().set(StatsType.Speed, ivstsp);
                                                                        pokemon.getEVs().set(StatsType.HP, evsth);
                                                                        pokemon.getEVs().set(StatsType.Attack, evsta);
                                                                        pokemon.getEVs().set(StatsType.Defence, evstd);
                                                                        pokemon.getEVs().set(StatsType.SpecialAttack, evstsa);
                                                                        pokemon.getEVs().set(StatsType.SpecialDefence, evstsd);
                                                                        pokemon.getEVs().set(StatsType.Speed, evstsp);
                                                                        pokemon.setGender(Gender.getGender(gender1));
                                                                        pokemon.setAbilitySlot(ability1i);
                                                                        //pokemon.setAbility(String.valueOf(AbilityBase.getAbility(ability1)));
                                                                        pokemon.setForm(form);
                                                                        if (shiny == 1) {
                                                                            pokemon.setShiny(true);
                                                                        }
                                                                        //pokemon.setOriginalTrainer((EntityPlayerMP) Sponge.getServer().getPlayer(srcvpl.getOriginalTrainer()).get());
                                                                        //Player entmp1 = Sponge.getServer().getPlayer(srcvpl.getOriginalTrainer()).get();
                                                                        //EntityPlayerMP emp1 = (EntityPlayerMP) entmp1;
                                                                        if (heldList.contains(", " + srcvpl.getHeldItem().getDisplayName().toLowerCase() + ",")) {
                                                                            pokemon.setHeldItem(srcvpl.getHeldItem());
                                                                        }

                                                                        int mlen = moves1.length;
                                                                        int mslot = 0;
                                                                        int i = 0;
                                                                        while (i <= mlen && i <= 3 && moves1[i] != null && !moves1[i].toString().equals("null")) {
                                                                            pokemon.getMoveset().set(mslot, moves1[i]);
                                                                            mslot++;
                                                                            i++;
                                                                        }
                                                                        i = 0;

                                                                        if (Sponge.getServer().getPlayer(rsara.getString("isim")).isPresent()) {
                                                                            Pixelmon.storageManager.getPCForPlayer((EntityPlayerMP) Sponge.getServer().getPlayer(rsara.getString("isim")).get()).add(pokemon);
                                                                            pokemon.setOriginalTrainer(srcvpl.getOriginalTrainerUUID() , srcvpl.getOriginalTrainer());
                                                                        } else {

                                                                            connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                                                                            statmt = connect.createStatement();

                                                                            String sqlrs1 = "SELECT id FROM Beklenenler ORDER BY id DESC";
                                                                            ResultSet rs1 = statmt.executeQuery(sqlrs1);

                                                                            rs1.next();
                                                                            int idr1 = rs1.getInt("id");
                                                                            int id = idr1 + 1;

                                                                            String sqlistenenler = "INSERT INTO Beklenenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                                                            PreparedStatement preparedStatement = connect.prepareStatement(sqlistenenler);
                                                                            preparedStatement.setInt(1, id);
                                                                            preparedStatement.setString(2, rsara.getString("isim"));
                                                                            preparedStatement.setString(3, rsara.getString("pokemon"));
                                                                            preparedStatement.setString(4, null);
                                                                            preparedStatement.setString(5, srcvpl.getNature().toString());
                                                                            preparedStatement.setString(6, srcvpl.getGrowth().toString());
                                                                            preparedStatement.setInt(7, srcvpl.getLevel());
                                                                            preparedStatement.setString(8, "" + srcvpl.getAbilitySlot());
                                                                            preparedStatement.setString(9, srcvpl.getGender().toString());
                                                                            preparedStatement.setString(10, ivsth + " " + ivsta + " " + ivstd + " " + ivstsa + " " + ivstsd + " " + ivstsp);
                                                                            preparedStatement.setString(11, srcvpl.getOriginalTrainer());
                                                                            preparedStatement.setString(12, srcvpl.getHeldItem().getDisplayName());
                                                                            preparedStatement.setInt(13, srcvpl.getFormEnum().getForm());
                                                                            if (srcvpl.isShiny() == true) {
                                                                                preparedStatement.setInt(14, 1);
                                                                            } else {
                                                                                preparedStatement.setInt(14, 0);
                                                                            }
                                                                            preparedStatement.setString(15, evsth + " " + evsta + " " + evstd + " " + evstsa + " " + evstsd + " " + evstsp);
                                                                            preparedStatement.setString(16, Arrays.toString(moves1));
                                                                            preparedStatement.setString(17, srcvpl.getOriginalTrainerUUID() + " " + srcvpl.getOriginalTrainer());
                                                                            preparedStatement.setInt(18, kod);
                                                                            preparedStatement.executeUpdate();
                                                                        }
                                                                        p.set(slot, null);
                                                                        spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Takas basarili."));

                                                                        // Takası kabul edene gidecek pokemon - takasi isteyenden silinecek
                                                                        PokemonSpec spec2 = PokemonSpec.from(pokev);
                                                                        Pokemon pokemon2 = spec2.create();
                                                                        pokemon2.setLevel(levelv);
                                                                        pokemon2.setNature(EnumNature.natureFromString(naturev));
                                                                        pokemon2.setGrowth(EnumGrowth.valueOf(growthv));
                                                                        String ivsv2 = rsara2.getString("ivstat");
                                                                        String evsv2 = rsara2.getString("evstat");
                                                                        String[] ivstat = ivsv2.split(" ");
                                                                        String[] evstat = evsv2.split(" ");
                                                                        pokemon2.getIVs().set(StatsType.HP, Integer.parseInt(ivstat[0]));
                                                                        pokemon2.getIVs().set(StatsType.Attack, Integer.parseInt(ivstat[1]));
                                                                        pokemon2.getIVs().set(StatsType.Defence, Integer.parseInt(ivstat[2]));
                                                                        pokemon2.getIVs().set(StatsType.SpecialAttack, Integer.parseInt(ivstat[3]));
                                                                        pokemon2.getIVs().set(StatsType.SpecialDefence, Integer.parseInt(ivstat[4]));
                                                                        pokemon2.getIVs().set(StatsType.Speed, Integer.parseInt(ivstat[5]));
                                                                        pokemon2.getEVs().set(StatsType.HP, Integer.parseInt(evstat[0]));
                                                                        pokemon2.getEVs().set(StatsType.Attack, Integer.parseInt(evstat[1]));
                                                                        pokemon2.getEVs().set(StatsType.Defence, Integer.parseInt(evstat[2]));
                                                                        pokemon2.getEVs().set(StatsType.SpecialAttack, Integer.parseInt(evstat[3]));
                                                                        pokemon2.getEVs().set(StatsType.SpecialDefence, Integer.parseInt(evstat[4]));
                                                                        pokemon2.getEVs().set(StatsType.Speed, Integer.parseInt(evstat[5]));
                                                                        pokemon2.setGender(Gender.getGender(genderv));

                                                                        //pokemon2.setAbilitySlot(Integer.valueOf(abilityv)); // ------------------------ hata
                                                                        //pokemon2.setAbility(String.valueOf(AbilityBase.getAbility(abilityv)));
                                                                        pokemon2.setForm(form2);
                                                                        if (shiny2 == 1) {
                                                                            pokemon2.setShiny(true);
                                                                        }
                                                                        //pokemon2.setOriginalTrainer((EntityPlayerMP) Sponge.getServer().getPlayer(rsara2.getString("ortrainer")).get());
                                                                        // UUID puid = Sponge.getServer().getPlayer(rsara2.getString("ortrainer")).get().getUniqueId();
                                                                        //Player entmp = Sponge.getServer().getPlayer(rsara2.getString("ortrainer")).get();
                                                                        //EntityPlayerMP emp = (EntityPlayerMP) entmp;
                                                                        //pokemon2.setOriginalTrainer(emp);

                                                                        int mlen1 = moves.length;
                                                                        int mslot1 = 0;
                                                                        int i1 = 0;
                                                                        Attack at = new Attack("");
                                                                        //System.out.println(moves);
                                                                        //System.out.println(move);
                                                                        while (i1 <= mlen1-1 && i1 <= 3 && moves[i1] != null && !moves[i1].equals("null")) {
                                                                            at = new Attack(moves[i1]);
                                                                            pokemon2.getMoveset().set(mslot1, at);
                                                                            mslot1++;
                                                                            i1++;
                                                                        }


                                                                        if (Sponge.getServer().getPlayer(src.getName()).isPresent()) {
                                                                            Pixelmon.storageManager.getPCForPlayer((EntityPlayerMP) src).add(pokemon2);
                                                                            pokemon2.setAbilitySlot(Integer.valueOf(abilityv)); // çözüldü ? ------------------------ hata
                                                                            if (heldList.contains(", " + rsara2.getString("held").toLowerCase() + ",")) {
                                                                                String held = rsara2.getString("held");
                                                                                ItemStack heldi = ItemStack.of((ItemType) PixelmonItemsHeld.getHeldItem(held));
                                                                                net.minecraft.item.ItemStack heldn = ItemStackUtil.toNative(heldi);
                                                                                pokemon2.setHeldItem(heldn);
                                                                            }
                                                                            pokemon2.setOriginalTrainer(puid, poname);
                                                                        }

                                                                        if (Sponge.getServer().getPlayer(rsara.getString("isim")).isPresent()) {
                                                                            Optional<Player> ep = Sponge.getServer().getPlayer(rsara.getString("isim"));
                                                                            ep.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Takas basarili."));
                                                                        }

                                                                        connect2 = DriverManager.getConnection(DB_URL3, USER, PASS);
                                                                        statmt2 = connect2.createStatement();
                                                                        connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                                                                        statmt = connect.createStatement();

                                                                        String sqldel = "DELETE FROM Istenenler WHERE kod='" + kod + "'";
                                                                        PreparedStatement preparedStatement3 = connect.prepareStatement(sqldel);
                                                                        preparedStatement3.executeUpdate();

                                                                        String sqldelver = "DELETE FROM Verilenler WHERE kod='" + kod + "'";
                                                                        PreparedStatement preparedStatement2 = connect2.prepareStatement(sqldelver);
                                                                        preparedStatement2.executeUpdate();

                                                                        if (Kadayiftrade.pSlot.get(uuid) != null) {
                                                                            Kadayiftrade.pSlot.remove(uuid);
                                                                        }
                                                                    } else {
                                                                        spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemonun shiny ozelligi takasta istenen pokemon ile ayni degil."));
                                                                    }
                                                                } else {
                                                                    spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemonun formu takasta istenen pokemon ile ayni degil."));
                                                                }
                                                            } else {
                                                                spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemonun genderi takasta istenen pokemon ile ayni degil."));
                                                            }
                                                        } else {
                                                            spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemonun abilitysi takasta istenen pokemon ile ayni degil."));
                                                        }
                                                    } else {
                                                        spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemonun leveli takasta istenen pokemon ile ayni degil."));
                                                    }
                                                } else {
                                                    spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemonun growth'u takasta istenen pokemon ile ayni degil."));
                                                }
                                            } else {
                                                spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemonun naturesi takasta istenen pokemon ile ayni degil."));
                                            }
                                        } else {
                                            spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemonun IVs'i takasta istenen pokemon ile ayni degil."));
                                        }
                                    } else {
                                        spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Sectiginiz slottaki pokemon takasta istenen pokemon ile ayni degil."));
                                    }

                                }
                            } else {
                                src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Takasi sadece gui uzerinden yapabilirsiniz."));
                            }
                        }

                    }catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                            if (statmt2 != null) statmt2.close();
                            if (statmt3 != null) statmt3.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                            if (connect2 != null) connect2.close();
                            if (connect3 != null) connect3.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }


                }catch(SQLException se) {
                    se.printStackTrace();
                } catch(Exception e) {
                    e.printStackTrace();
                }


                return CommandResult.success();
            })
            .build();


    CommandSpec list = CommandSpec.builder()
            .description(Text.of("Takas listesini gosterir."))
            .permission("ytrade.liste")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("sayfa")))
            )
            .executor((CommandSource src, CommandContext args) -> {
                try {
                    connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt = connect.createStatement();
                    try {
                        Class.forName(JDBC_DRIVER);

                        String sqlara = "SELECT * FROM Istenenler";
                        ResultSet rsara = statmt.executeQuery(sqlara);

                        connect2 = DriverManager.getConnection(DB_URL3, USER, PASS);
                        statmt2 = connect2.createStatement();
                        String sqlara2 = "SELECT * FROM Verilenler";
                        ResultSet rsara2 = statmt2.executeQuery(sqlara2);

                        Integer sayfa = args.<Integer>getOne("sayfa").get();
                        rsara.next();
                        rsara2.next();


                        int sf = sayfa * 5;
                        int esf = sf - 5;
                        statmt2 = connect2.createStatement();
                        String sqlara3 = "SELECT * FROM Istenenler WHERE id BETWEEN " + esf + " AND " + sf + "";
                        ResultSet rsara3 = statmt2.executeQuery(sqlara3);
                        connect3 = DriverManager.getConnection(DB_URL3, USER, PASS);
                        statmt3 = connect3.createStatement();
                        String sqlara4 = "SELECT * FROM Verilenler WHERE id BETWEEN " + esf + " AND " + sf + "";
                        ResultSet rsara4 = statmt3.executeQuery(sqlara4);
                        rsara3.next();
                        rsara4.next();

                        while(rsara3.next() && rsara4.next()) {
                            System.out.println(rsara3.getString("kod"));
                            Text ctext = Text.builder("Nick: ").color(TextColors.GREEN).append(
                                    Text.builder(rsara3.getString("isim") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Istenen: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara3.getString("pokemon") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("IVS: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara3.getString("ivs") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Nature: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara3.getString("nature") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Growth: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara3.getString("growth") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Level: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara3.getString("level") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Ability: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara3.getString("ability") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Gender: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara3.getString("gender") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("\n----------\n").color(TextColors.GREEN).build()).append(
                                    Text.builder("Verecegi: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara4.getString("pokemon") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("IVS: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara4.getString("ivstat") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Nature: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara4.getString("nature") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Growth: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara4.getString("growth") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Level: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara4.getString("level") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Ability: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara4.getString("ability") + " ").color(TextColors.AQUA).build()).append(
                                    Text.builder("Gender: ").color(TextColors.GREEN).build()).append(
                                    Text.builder(rsara4.getString("gender") + " ").color(TextColors.AQUA).build()).append().build();

                            Text ktext = Text.builder(String.valueOf(rsara3.getInt("kod") + "\n")).color(TextColors.AQUA).onHover(TextActions.showText(ctext)).build();
                            src.sendMessage(ktext);
                        }

                    }catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                            if (statmt2 != null) statmt2.close();
                            if (statmt3 != null) statmt3.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                            if (connect2 != null) connect2.close();
                            if (connect3 != null) connect3.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }


                }catch(SQLException se) {
                    se.printStackTrace();
                } catch(Exception e) {
                    e.printStackTrace();
                }


                return CommandResult.success();
            })
            .build();

    CommandSpec ekle = CommandSpec.builder()
            .description(Text.of("Trade'ye pokemon eklemenizi saglar."))
            .permission("ytrade.ekle")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("verilecek"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("pokemon"))),
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("ivs"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("nature"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("growth"))),
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("level"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("gender"))),
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("form"))),
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("shiny"))),
                    GenericArguments.remainingJoinedStrings(Text.of("ability"))
                    )
            .executor((CommandSource src, CommandContext args) -> {
                Integer verilecek0 = args.<Integer>getOne("verilecek").get();
                Integer verilecek = verilecek0 - 1;
                String pokemon = args.<String>getOne("pokemon").get();
                Integer ivs = args.<Integer>getOne("ivs").get();
                String nature = args.<String>getOne("nature").get();
                String growth = args.<String>getOne("growth").get();
                Integer level = args.<Integer>getOne("level").get();
                String gender = args.<String>getOne("gender").get();
                Integer form = args.<Integer>getOne("form").get();
                Integer shiny = args.<Integer>getOne("shiny").get();
                String ability = args.<String>getOne("ability").get();

                try {
                    connect = DriverManager.getConnection(DB_URL3, USER, PASS);
                    statmt = connect.createStatement();
                    try {
                        Class.forName(JDBC_DRIVER);
                        statmt2 = connect.createStatement();

                        String sqlara3 = "SELECT * FROM Istenenler WHERE isim='" + src.getName() + "'";
                        ResultSet rsara3 = statmt2.executeQuery(sqlara3);
                        int sayi = 0;

                        while(rsara3.next()) {
                            sayi++;
                        }


                        if(sayi < 3 && verilecek >= 0) {
                            Optional<Player> spl = Sponge.getServer().getPlayer(src.getName());
                            PlayerPartyStorage p = Pixelmon.storageManager.getParty(spl.get().getUniqueId());
                            Pokemon srcvpl = p.get(verilecek);
                            int pid = Pokedex.nameToID(pokemon);
                            String idd = String.format("%03d", pid);

                            //.contains(", " + srcvpl.getHeldItem().getDisplayName().toLowerCase() + ",")
                            if (p.countAll() != 1 && verilecek < 6 && verilecek >= 0 && p.get(verilecek) != null) {
                                if (pokeList.contains(", " + pokemon.toLowerCase() + ",")) {
                                    if (natureList.contains((", " + nature.toLowerCase() + ",")) || nature.toLowerCase().equals("fe")) {
                                        if (growthList.contains((", " + growth.toLowerCase() + ",")) || growth.toLowerCase().equals("fe")) {
                                            // Ability
                                            if(abilityList.contains(", " + ability.toLowerCase() + ",") || ability.toLowerCase().equals("fe")) {
                                                jsonReadAbility(idd, String.valueOf(form), "ability");
                                                if (json.contains("\"" + ability.toLowerCase() + "\"") || ability.toLowerCase().equals("fe")) {
                                                    //Gender
                                                    if (genderList.contains(", " + gender.toLowerCase() + ",") || gender.toLowerCase() == "fe") {
                                                        jsonReadAbility(idd, "", "gender");
                                                        if((Integer.valueOf(json) > 0 && gender.toLowerCase().equals("male")) || (Integer.valueOf(json) >= 0 && Integer.valueOf(json) != 100 && gender.toLowerCase().equals("female")) || (gender.toLowerCase().equals("fe"))) {
                                                            if (ivs <= 100 && ivs > 0) {
                                                                if (level <= 100 && level > 0) {
                                                                    //Form
                                                                    jsonReadAbility(idd, "", "form");
                                                                    if ((Integer.valueOf(json) >= form && form > 0) || (form == 00)) {
                                                                        if (shiny == 0 || shiny == 1) {

                                                                            String sqlrs1 = "SELECT id FROM Istenenler ORDER BY id DESC";
                                                                            ResultSet rs1 = statmt.executeQuery(sqlrs1);

                                                                            rs1.next();
                                                                            int idr1 = rs1.getInt("id");
                                                                            int id = idr1 + 1;

                                                                            Random random = new Random();
                                                                            int kodv = random.nextInt(10001);


                                                                            statmt = connect.createStatement();

                                                                            if (spl.isPresent()) {
                                                                                //Pokemon srcvpl = p.get(verilecek);
                                                                                String poke = srcvpl.getSpecies().toString();
                                                                                int ivsat = srcvpl.getIVs().attack;
                                                                                int ivsdef = srcvpl.getIVs().defence;
                                                                                int ivshp = srcvpl.getIVs().hp;
                                                                                int ivsspa = srcvpl.getIVs().specialAttack;
                                                                                int ivsspd = srcvpl.getIVs().specialDefence;
                                                                                int ivsspe = srcvpl.getIVs().speed;
                                                                                int evsat = srcvpl.getEVs().attack;
                                                                                int evsdef = srcvpl.getEVs().defence;
                                                                                int evshp = srcvpl.getEVs().hp;
                                                                                int evsspa = srcvpl.getEVs().specialAttack;
                                                                                int evsspd = srcvpl.getEVs().specialDefence;
                                                                                int evsspe = srcvpl.getEVs().speed;
                                                                                double ivst = srcvpl.getIVs().getPercentage(1);
                                                                                EnumNature nature1 = srcvpl.getNature();
                                                                                EnumGrowth growth1 = srcvpl.getGrowth();
                                                                                int level1 = srcvpl.getLevel();
                                                                                String ability1 = srcvpl.getAbilityName();
                                                                                String gender1 = srcvpl.getGender().toString();
                                                                                String ortrainer = srcvpl.getOriginalTrainer();
                                                                                //String held1 = srcvpl.getHeldItem().getDisplayName();
                                                                                int form1 = srcvpl.getFormEnum().getForm();
                                                                                Boolean shiny1 = srcvpl.isShiny();
                                                                                UUID duid = Sponge.getServer().getPlayer(src.getName()).get().getUniqueId();
                                                                                UUID puid = srcvpl.getOriginalTrainerUUID();
                                                                                String poname = srcvpl.getOriginalTrainer();

                                                                                //String move1 = srcvpl.getMoveset().get


                                                                                Attack[] ats = srcvpl.getMoveset().attacks;
                                                                                //System.out.println(ats);
                                                                                int i = 0;
                                                                                String t = "";
                                                                                String[] test;
                                                                                int len = ats.length;
                                                                                while (i <= len &&  i <= 3 && ats[i] != null && !ats[i].toString().equals("null")) {
                                                                                    if (i > 3) {
                                                                                        i = 0;
                                                                                    }
                                                                                    t = t + ats[i] + ",";
                                                                                    i++;
                                                                                }
                                                                                i = 0;
                                                                                test = t.split(",");
                                                                                String[] fintest = Arrays.copyOf(test, test.length-1);
                                                                                //System.out.println(test);
                                                                                //System.out.println(t);
                                                                                //System.out.println(test);



                                                                                String held1 = "yok";
                                                                                if (heldList.contains(", " + srcvpl.getHeldItem().getDisplayName().toLowerCase() + ",")) {
                                                                                    held1 = srcvpl.getHeldItem().getDisplayName();
                                                                                }


                                                                                //System.out.println(srcvpl.getFormEnum().getForm());


                                                                                PlayerPartyStorage ps = Pixelmon.storageManager.getParty(spl.get().getUniqueId());
                                                                                ps.set(verilecek, null);


                                                                                String sqlistenenler = "INSERT INTO Istenenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                                                                PreparedStatement preparedStatement = connect.prepareStatement(sqlistenenler);
                                                                                preparedStatement.setInt(1, id);
                                                                                preparedStatement.setString(2, src.getName());
                                                                                preparedStatement.setString(3, pokemon);
                                                                                preparedStatement.setString(4, ivs.toString());
                                                                                preparedStatement.setString(5, nature);
                                                                                preparedStatement.setString(6, growth);
                                                                                preparedStatement.setInt(7, level);
                                                                                preparedStatement.setString(8, ability);
                                                                                preparedStatement.setString(9, gender);
                                                                                preparedStatement.setString(10, "");
                                                                                preparedStatement.setString(11, ortrainer);
                                                                                preparedStatement.setString(12, "");
                                                                                preparedStatement.setInt(13, form);
                                                                                preparedStatement.setInt(14, shiny);
                                                                                preparedStatement.setString(15, "");
                                                                                preparedStatement.setString(16, "");
                                                                                preparedStatement.setString(17, "");
                                                                                preparedStatement.setInt(18, kodv);
                                                                                preparedStatement.executeUpdate();

                                                                                String sqlverilenler = "INSERT INTO Verilenler(id, isim, pokemon, ivs, nature, growth, level, ability, gender, ivstat, ortrainer, held, form, shiny, evstat, moves, uuid, kod) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                                                                PreparedStatement preparedStatement2 = connect.prepareStatement(sqlverilenler);
                                                                                preparedStatement2.setInt(1, id);
                                                                                preparedStatement2.setString(2, src.getName());
                                                                                preparedStatement2.setString(3, poke);
                                                                                preparedStatement2.setString(4, String.valueOf(ivst));
                                                                                preparedStatement2.setString(5, nature1.toString());
                                                                                preparedStatement2.setString(6, growth1.toString());
                                                                                preparedStatement2.setInt(7, level1);
                                                                                preparedStatement2.setString(8, String.valueOf(srcvpl.getAbilitySlot()));
                                                                                preparedStatement2.setString(9, gender1);
                                                                                preparedStatement2.setString(10, ivshp + " " + ivsat + " " + ivsdef + " " + ivsspa + " " + ivsspd + " " + ivsspe);
                                                                                preparedStatement2.setString(11, ortrainer);
                                                                                preparedStatement2.setString(12, held1);
                                                                                preparedStatement2.setInt(13, form1);
                                                                                if (shiny1 == true) {
                                                                                    preparedStatement2.setInt(14, 1);
                                                                                } else {
                                                                                    preparedStatement2.setInt(14, 0);
                                                                                }
                                                                                preparedStatement2.setString(15, evshp + " " + evsat + " " + evsdef + " " + evsspa + " " + evsspd + " " + evsspe);
                                                                                preparedStatement2.setString(16, Arrays.toString(test));
                                                                                if(!String.valueOf(puid).equals("00000000-0000-0000-0000-000000000000") && poname != null && puid != null) {
                                                                                    preparedStatement2.setString(17, puid + " " + poname);
                                                                                } else {
                                                                                    preparedStatement2.setString(17, spl.get().getUniqueId() + " " + spl.get().getName());
                                                                                }
                                                                                preparedStatement2.setInt(18, kodv);
                                                                                preparedStatement2.executeUpdate();


                                                                                spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "yTrade'e istediginiz pokemonu eklediniz, karsiliginda " + verilecek0 + " slotundaki pokemonunuzu vereceksiniz. Takas kodunuz: ", TextColors.AQUA, kodv));


                                                                                Text ctext = Text.builder("Nick: ").color(TextColors.GREEN).append(
                                                                                        Text.builder(src.getName() + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Istenen: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(pokemon + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("IVS: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(ivs + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Nature: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(nature + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Growth: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(growth + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Level: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(level + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Ability: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(ability + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Gender: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(gender + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Held: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder("Yok" + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Form: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(form + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Shiny: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(shiny + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("\n------------\n").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder("Verecegi: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(poke + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("IVS: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(ivshp + " " + ivsat + " " + ivsdef + " " + ivsspa + " " + ivsspd + " " + ivsspe + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Nature: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(nature1.toString() + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Growth: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(growth1.toString() + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Level: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(level1 + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Ability: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(ability1 + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Gender: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(gender1 + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Held: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(held1 + " ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder("Form: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(form1 + " ").color(TextColors.AQUA).build()).append().build();

                                                                                Text ytext = Text.builder("[").color(TextColors.AQUA).append(
                                                                                        Text.builder("yTrade").color(TextColors.LIGHT_PURPLE).build()).append(
                                                                                        Text.builder("] ").color(TextColors.AQUA).build()).append(
                                                                                        Text.builder(src.getName()).color(TextColors.BLUE).build()).append(
                                                                                        Text.builder(" isimli oyuncu bir takas olusturdu: ").color(TextColors.GREEN).build()).append(
                                                                                        Text.builder(String.valueOf(kodv)).color(TextColors.BLUE).onHover(TextActions.showText(ctext)).build()).append().append().build();

                                                                                Sponge.getServer().getBroadcastChannel().send(ytext);
                                                                            }


                                                                        } else {
                                                                            spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Shiny 0 veya 1 olmak zorundadir. 0 Normal, 1 Shiny."));
                                                                        }
                                                                    } else {
                                                                        spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Bu pokemonun boyle bir formu bulunmamaktadir."));
                                                                    }
                                                                } else {
                                                                    spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Level 100'den buyuk veya 1'dan kucuk olamaz."));
                                                                }
                                                            } else {
                                                                spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "IVs 100'den buyuk veya 0'dan kucuk olamaz."));
                                                            }

                                                        } else {
                                                            spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Bu pokemonun boyle bir genderi bulunmamakta." + Integer.valueOf(json)));
                                                        }

                                                    } else {
                                                        spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Bu isimde bir gender bulunmamakta."));
                                                    }

                                                } else {
                                                    spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Bu pokemonun boyle bir abilitysi bulunmamakta."));
                                                }

                                            } else {
                                                spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Bu isimde bir ability bulunmamakta."));
                                            }
                                        } else {
                                            spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Bu isimde bir growth bulunmamakta."));
                                        }
                                    } else {
                                        spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Bu isimde bir nature bulunmamakta."));
                                    }
                                } else {
                                    spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "Bu isimde bir pokemon bulunmamakta."));
                                }
                            } else {
                                spl.get().sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "yTrade'i kullanabilmek icin uzerinizde en az iki pokemon bulunmali ve vereceginiz pokemonun slotu olarak en az 1 yazmalisiniz."));
                            }
                        } else {
                            src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "3'ten fazla takas olusturamaz veya 0'dan kucuk slot yazamazsiniz."));
                        }




                    }catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                            if (statmt2 != null) statmt2.close();
                            if (statmt3 != null) statmt3.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                            if (connect2 != null) connect2.close();
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



                return CommandResult.success();
                    })
            .build();

    CommandSpec myCommandSpec = CommandSpec.builder()
            .description(Text.of("yTrade plugininin bilgi ve uyari mesajlarini gosterir."))
            .permission("ytrade.ytrade")
            .child(ekle, "ekle")
            .child(list, "liste")
            .child(takas, "takas")
            .child(gui, "gui")
            .child(al, "tw")
            .child(admin_sil, "adsil")
            .child(bilgi, "bilgi")
            .child(oyuncu_sil, "sil")
            .executor((CommandSource src, CommandContext args) -> {
                src.sendMessage(Text.of(TextColors.AQUA, "[", TextColors.LIGHT_PURPLE, "yTrade", TextColors.AQUA, "]", TextColors.GREEN, "yTrade v1.0 by YogurtluKadayif"));
                src.sendMessage(Text.of(TextColors.AQUA, "->", TextColors.DARK_PURPLE, "yTrade'i kullanan herkes /ytrade bilgi'de bulunan maddeleri kabul etmis olarak sayilacaktir."));
                src.sendMessage(Text.of(TextColors.AQUA, ">", TextColors.LIGHT_PURPLE, " Komutlar (ek = ekleyeceginiz pokemonun, is = istediginiz pokemonun)"));
                src.sendMessage(Text.of(TextColors.AQUA, "/", TextColors.DARK_PURPLE, " ytrade ekle <ek slotu> <is adi> <is min. ivs> <is nature> <is growth> <is min. level> <is gender> <is form> <is ability> "));
                src.sendMessage(Text.of(TextColors.AQUA, "/", TextColors.DARK_PURPLE, " ytrade liste <sayfa>"));
                src.sendMessage(Text.of(TextColors.AQUA, "/", TextColors.DARK_PURPLE, " ytrade gui"));
                src.sendMessage(Text.of(TextColors.AQUA, "/", TextColors.DARK_PURPLE, " ytrade takas <takas kodu> <vereceginiz pokemonun slotu>"));
                src.sendMessage(Text.of(TextColors.AQUA, "/", TextColors.DARK_PURPLE, " ytrade tw | Oyunda degilken gelen pokemonlari almanizi saglar."));
                src.sendMessage(Text.of(TextColors.AQUA, "/", TextColors.DARK_PURPLE, " ytrade sil <kod> | Olusturdugunuz takasi silmenizi saglar."));

                Optional<Player> spl = Sponge.getServer().getPlayer(src.getName());
                PlayerPartyStorage p = Pixelmon.storageManager.getParty(spl.get().getUniqueId());
                Pokemon srcvpl = p.get(0);
                /*if(srcvpl.getHeldItem() == null) {
                    System.out.println("null");
                } else {
                    System.out.println("null degil - " + srcvpl.getHeldItem().getDisplayName());
                }*/

                /*String held1 = "";
                if(heldList.contains(", " + srcvpl.getHeldItem().getDisplayName().toLowerCase() + ",")) {
                    held1 = srcvpl.getHeldItem().getDisplayName();
                    System.out.println("contains");
                }*/

                /*Attack[] ats = srcvpl.getMoveset().attacks;
                //System.out.println(ats);
                int i = 0;
                String t = "";
                String[] test;
                int len = ats.length;
                while (i <= len &&  i <= 3 && ats[i] != null && !ats[i].toString().equals("null")) {
                    if (i > 3) {
                        i = 0;
                    }
                    System.out.println(ats[i]);
                    t = t + ats[i] + ", ";
                    System.out.println(t);
                    i++;
                }

                i = 0;
                test = t.split(",");

                String[] fintest = Arrays.copyOf(test, test.length-1);
                //System.out.println(fintest);*/


                //Attack a = new Attack("Quick Attack");
                //srcvpl.getMoveset().set(0, a);

                /*int pid = Pokedex.nameToID(srcvpl.getDisplayName());
                String id = String.format("%03d", pid);
                //System.out.println(id);


                jsonReadAbility(id, "0", "ability");*/


                //UUID puid = Sponge.getServer().getPlayer(src.getName()).get().getUniqueId();
                //System.out.println(puid.toString());



                /*ItemStack heldi = ItemStack.of((ItemType) PixelmonItemsHeld.getHeldItem(held1));
                net.minecraft.item.ItemStack heldn = ItemStackUtil.toNative(heldi);
                srcvpl.setHeldItem(heldn);*/


                return CommandResult.success();
            })
            .build();


}
