package hyperbolicOverthrall;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hyperbolicOverthrall.characters.*;
import hyperbolicOverthrall.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import hyperbolicOverthrall.cards.*;
import hyperbolicOverthrall.events.IdentityCrisisEvent;
import hyperbolicOverthrall.potions.PlaceholderPotion;
import hyperbolicOverthrall.util.IDCheckDontTouchPls;
import hyperbolicOverthrall.util.TextureLoader;
import hyperbolicOverthrall.variables.DefaultCustomVariable;
import hyperbolicOverthrall.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;



@SpireInitializer
public class HyperbolicOverthrall implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostBattleSubscriber{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(HyperbolicOverthrall.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Hyperbolic Overthrall";
    private static final String AUTHOR = "Witchy Scholar"; // And pretty soon - You!
    private static final String DESCRIPTION = "An enormous content mod for Slay the Spire. Hyperbolic Overthrall!";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color ALTRUIST_BLUE = CardHelper.getColor(46.0f, 46.0f, 80.0f);
    public static final Color ANARCHIST_RED = CardHelper.getColor(64.0f, 32.0f, 32.0f);
    public static final Color COGNIZANT_GREEN = CardHelper.getColor(32.0f, 70.0f, 32.0f);
    public static final Color EXECUTIVE_GRAY = CardHelper.getColor(64.0f, 64.0f, 70.0f);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_ALTRUIST_LIGHT_BLUE = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String ATTACK_ANARCHIST_DARK_CRIMSON = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String ATTACK_COGNIZANT_FIBRE_GREEN = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String ATTACK_EXECUTIVE_CONCRETE_GRAY = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String SKILL_ALTRUIST_LIGHT_BLUE = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String SKILL_ANARCHIST_DARK_CRIMSON = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String SKILL_COGNIZANT_FIBRE_GREEN = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String SKILL_EXECUTIVE_CONCRETE_GRAY = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String POWER_ALTRUIST_LIGHT_BLUE = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String POWER_ANARCHIST_DARK_CRIMSON = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String POWER_COGNIZANT_FIBRE_GREEN = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    private static final String POWER_EXECUTIVE_CONCRETE_GRAY = "hyperbolicOverthrallResources/images/512/bg_attack_WUKONG_gray.png";
    
    private static final String ENERGY_ORB_ALTRUIST_LIGHT_BLUE = "hyperbolicOverthrallResources/images/512/card_WUKONG_gray_orb.png";
    private static final String ENERGY_ORB_ANARCHIST_DARK_CRIMSON = "hyperbolicOverthrallResources/images/512/card_WUKONG_gray_orb.png";
    private static final String ENERGY_ORB_COGNIZANT_FIBRE_GREEN = "hyperbolicOverthrallResources/images/512/card_WUKONG_gray_orb.png";
    private static final String ENERGY_ORB_EXECUTIVE_CONCRETE_GRAY = "hyperbolicOverthrallResources/images/512/card_WUKONG_gray_orb.png";
    private static final String ALTRUIST_CARD_ENERGY_ORB = "hyperbolicOverthrallResources/images/512/card_small_orb.png";
    private static final String ANARCHIST_CARD_ENERGY_ORB = "hyperbolicOverthrallResources/images/512/card_small_orb.png";
    private static final String COGNIZANT_CARD_ENERGY_ORB = "hyperbolicOverthrallResources/images/512/card_small_orb.png";
    private static final String EXECUTIVE_CARD_ENERGY_ORB = "hyperbolicOverthrallResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_ALTRUIST_LIGHT_BLUE_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String ATTACK_ANARCHIST_DARK_CRIMSON_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String ATTACK_COGNIZANT_FIBRE_GREEN_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String ATTACK_EXECUTIVE_CONCRETE_GRAY_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String SKILL_ALTRUIST_LIGHT_BLUE_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String SKILL_ANARCHIST_DARK_CRIMSON_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String SKILL_COGNIZANT_FIBRE_GREEN_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String SKILL_EXECUTIVE_CONCRETE_GRAY_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String POWER_ALTRUIST_LIGHT_BLUE_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String POWER_ANARCHIST_DARK_CRIMSON_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String POWER_COGNIZANT_FIBRE_GREEN_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String POWER_EXECUTIVE_CONCRETE_GRAY_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String ENERGY_ORB_ALTRUIST_LIGHT_BLUE_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String ENERGY_ORB_ANARCHIST_DARK_CRIMSON_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String ENERGY_ORB_COGNIZANT_FIBRE_GREEN_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    private static final String ENERGY_ORB_EXECUTIVE_CONCRETE_GRAY_PORTRAIT = "hyperbolicOverthrallResources/images/1024/bg_attack_WUKONG_gray.png";
    
    // Character assets
    private static final String MONKEY_KING_BUTTON = "hyperbolicOverthrallResources/images/charSelect/DefaultCharacterButton.png";
    private static final String MONKEY_KING_PORTRAIT = "hyperbolicOverthrallResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String MONKEY_KING_SHOULDER_1 = "hyperbolicOverthrallResources/images/char/defaultCharacter/shoulder.png";
    public static final String MONKEY_KING_SHOULDER_2 = "hyperbolicOverthrallResources/images/char/defaultCharacter/shoulder2.png";
    public static final String MONKEY_KING_CORPSE = "hyperbolicOverthrallResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "hyperbolicOverthrallResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String MONKEY_KING_SKELETON_ATLAS = "hyperbolicOverthrallResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String MONKEY_KING_SKELETON_JSON = "hyperbolicOverthrallResources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public HyperbolicOverthrall() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        setModID("hyperbolicOverthrall");

        logger.info("Done subscribing");

        BaseMod.addColor(Altruist.Enums.COLOR_SKY_BLUE, ALTRUIST_BLUE, ALTRUIST_BLUE, ALTRUIST_BLUE,
                ALTRUIST_BLUE, ALTRUIST_BLUE, ALTRUIST_BLUE, ALTRUIST_BLUE,
                ATTACK_ALTRUIST_LIGHT_BLUE, SKILL_ALTRUIST_LIGHT_BLUE, POWER_ALTRUIST_LIGHT_BLUE, ENERGY_ORB_ALTRUIST_LIGHT_BLUE,
                ATTACK_ALTRUIST_LIGHT_BLUE_PORTRAIT, SKILL_ALTRUIST_LIGHT_BLUE_PORTRAIT, POWER_ALTRUIST_LIGHT_BLUE_PORTRAIT,
                ENERGY_ORB_ALTRUIST_LIGHT_BLUE_PORTRAIT, ALTRUIST_CARD_ENERGY_ORB);

        BaseMod.addColor(Anarchist.Enums.COLOR_DARK_CRIMSON, ANARCHIST_RED, ANARCHIST_RED, ANARCHIST_RED,
                ANARCHIST_RED, ANARCHIST_RED, ANARCHIST_RED, ANARCHIST_RED,
                ATTACK_ANARCHIST_DARK_CRIMSON, SKILL_ANARCHIST_DARK_CRIMSON, POWER_ANARCHIST_DARK_CRIMSON, ENERGY_ORB_ANARCHIST_DARK_CRIMSON,
                ATTACK_ANARCHIST_DARK_CRIMSON_PORTRAIT, SKILL_ANARCHIST_DARK_CRIMSON_PORTRAIT, POWER_ANARCHIST_DARK_CRIMSON_PORTRAIT,
                ENERGY_ORB_ANARCHIST_DARK_CRIMSON_PORTRAIT, ANARCHIST_CARD_ENERGY_ORB);

        BaseMod.addColor(Cognizant.Enums.COLOR_FIBRE_GREEN, COGNIZANT_GREEN, COGNIZANT_GREEN, COGNIZANT_GREEN,
                COGNIZANT_GREEN, COGNIZANT_GREEN, COGNIZANT_GREEN, COGNIZANT_GREEN,
                ATTACK_COGNIZANT_FIBRE_GREEN, SKILL_COGNIZANT_FIBRE_GREEN, POWER_COGNIZANT_FIBRE_GREEN, ENERGY_ORB_COGNIZANT_FIBRE_GREEN,
                ATTACK_COGNIZANT_FIBRE_GREEN_PORTRAIT, SKILL_COGNIZANT_FIBRE_GREEN_PORTRAIT, POWER_COGNIZANT_FIBRE_GREEN_PORTRAIT,
                ENERGY_ORB_COGNIZANT_FIBRE_GREEN_PORTRAIT, COGNIZANT_CARD_ENERGY_ORB);

        BaseMod.addColor(Executive.Enums.COLOR_CONCRETE_GRAY, EXECUTIVE_GRAY, EXECUTIVE_GRAY, EXECUTIVE_GRAY,
                EXECUTIVE_GRAY, EXECUTIVE_GRAY, EXECUTIVE_GRAY, EXECUTIVE_GRAY,
                ATTACK_EXECUTIVE_CONCRETE_GRAY, SKILL_EXECUTIVE_CONCRETE_GRAY, POWER_EXECUTIVE_CONCRETE_GRAY, ENERGY_ORB_EXECUTIVE_CONCRETE_GRAY,
                ATTACK_EXECUTIVE_CONCRETE_GRAY_PORTRAIT, SKILL_EXECUTIVE_CONCRETE_GRAY_PORTRAIT, POWER_EXECUTIVE_CONCRETE_GRAY_PORTRAIT,
                ENERGY_ORB_EXECUTIVE_CONCRETE_GRAY_PORTRAIT, EXECUTIVE_CARD_ENERGY_ORB);
        
        logger.info("Done creating the colors!");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = HyperbolicOverthrall.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = HyperbolicOverthrall.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = HyperbolicOverthrall.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        new HyperbolicOverthrall();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {

        BaseMod.addCharacter(new Altruist("The Altruist", Altruist.Enums.ALTRUIST),
                MONKEY_KING_BUTTON, MONKEY_KING_PORTRAIT, Altruist.Enums.ALTRUIST);

        BaseMod.addCharacter(new Anarchist("The Anarchist", Anarchist.Enums.ANARCHIST),
                MONKEY_KING_BUTTON, MONKEY_KING_PORTRAIT, Anarchist.Enums.ANARCHIST);

        BaseMod.addCharacter(new Cognizant("The Cognizant", Cognizant.Enums.COGNIZANT),
                MONKEY_KING_BUTTON, MONKEY_KING_PORTRAIT, Cognizant.Enums.COGNIZANT);

        BaseMod.addCharacter(new Executive("The Executive", Executive.Enums.EXECUTIVE),
                MONKEY_KING_BUTTON, MONKEY_KING_PORTRAIT, Executive.Enums.EXECUTIVE);
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events

        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID

        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default

        // Create a new event builder
        // Since this is a builder these method calls (outside of create()) can be skipped/added as necessary
        AddEventParams eventParams = new AddEventParams.Builder(IdentityCrisisEvent.ID, IdentityCrisisEvent.class) // for this specific event
            .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
            .playerClass(Anarchist.Enums.ANARCHIST) // Character specific event
            .create();

        // Add the event
        BaseMod.addEvent(eventParams);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.MONKEY_KING".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, Anarchist.Enums.ANARCHIST);
        
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar to our AbstractDefaultCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new LoneWolfRelic(), Executive.Enums.COLOR_CONCRETE_GRAY);
        BaseMod.addRelicToCustomPool(new PrismRingRelic(), Cognizant.Enums.COLOR_FIBRE_GREEN);
        BaseMod.addRelicToCustomPool(new adventureModeRelic(), Altruist.Enums.COLOR_SKY_BLUE);
        BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), Altruist.Enums.COLOR_SKY_BLUE);
        BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), Altruist.Enums.COLOR_SKY_BLUE);
        
        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        // (the others are all starters so they're marked as seen in the character file)
        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        // This method automatically adds any cards so you don't have to manually load them 1 by 1
        // For more specific info, including how to exclude cards from being added:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact ID in your pom.xml

        //TODO: Rename the "DefaultMod" with the modid in your ModTheSpire.json file
        //TODO: The artifact mentioned in ModTheSpire.json is the artifactId in pom.xml you should've edited earlier
        new AutoAdd("hyperbolicOverthrall") // ${project.artifactId}
            .packageFilter(AbstractDefaultCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
            .setDefaultSeen(true)
            .cards();

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        logger.info("Done adding cards!");
    }
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/hyperbolicOverthrall-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/hyperbolicOverthrall-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/hyperbolicOverthrall-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/hyperbolicOverthrall-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/hyperbolicOverthrall-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/hyperbolicOverthrall-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/hyperbolicOverthrall-Orb-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/hyperbolicOverthrall-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        for(AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if(card instanceof PostBattleCard) {
                ((PostBattleCard) card).postBattleEffect();
            }
        }
    }
}
