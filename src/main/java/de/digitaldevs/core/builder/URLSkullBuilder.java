package de.digitaldevs.core.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.digitaldevs.core.reflection.ClassAccessor;
import de.digitaldevs.core.reflection.FieldAccessor;
import de.digitaldevs.core.reflection.types.PackageType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.UUID;

/**
 * This class is used for creating textured player skulls easily by getting the texture from the mojang session server.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class URLSkullBuilder {

    private final ItemStack itemStack;
    private final SkullMeta skullMeta;
    private String skinURL;

    private static final Class<?> SKULL_META_CLAZZ;
    private static final JsonParser PARSER = new JsonParser();

    static {
        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        SKULL_META_CLAZZ = ClassAccessor.accessCraftBukkitClazzInPackage(PackageType.INVENTORY, "CraftMetaSkull", serverVersion);
    }

    public URLSkullBuilder() {
        this.itemStack = new ItemBuilder(Material.SKULL_ITEM, (short) 3).build();
        this.skullMeta = (SkullMeta) this.itemStack.getItemMeta();
    }

    public URLSkullBuilder owner(String skinOwner) {
        this.skinURL = this.cacheUserByName(skinOwner);
        return this;
    }

    public URLSkullBuilder name(String name) {
        this.skullMeta.setDisplayName(name);
        return this;
    }

    public ItemStack build() {
        FieldAccessor.set(SKULL_META_CLAZZ, "profile", this.getProfile(this.skinURL));
        this.itemStack.setItemMeta(this.skullMeta);
        return this.itemStack;
    }

    private GameProfile getProfile(String skinURL) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        String base64encoded = Base64.getEncoder().encodeToString(new String("{textures:{SKIN:{url:\"" + skinURL + "\"}}}").getBytes());
        Property property = new Property("textures", base64encoded);
        profile.getProperties().put("textures", property);
        return profile;
    }

    private String convertUUID(String uuid) {
        return uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
    }

    private String readURL(String[] url) {
        try {
            Process process = Runtime.getRuntime().exec("curl " + url[0]);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder line = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) line.append(inputLine);
            if (line.toString().equals("")) return null;
            inputStreamReader.close();
            bufferedReader.close();
            return line.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String cacheUserByName(String name) {
        String url1 = readURL(new String[]{"https://api.minetools.eu/uuid/" + name});
        if (url1 != null) {
            Object json = PARSER.parse(url1);
            JsonObject object = (JsonObject) json;
            JsonElement uuidElement = object.get("id");
            if (uuidElement.getAsString().equals("null")) return null;
            String uuid = convertUUID(uuidElement.getAsString());
            String url = readURL(new String[]{"https://api.minetools.eu/profile/" + uuid.replace("-", "")});
            if (url != null) {
                json = PARSER.parse(url);
                object = (JsonObject) json;
                JsonObject propertiesDecodedElement = object.getAsJsonObject("decoded");
                JsonObject textures = propertiesDecodedElement.getAsJsonObject("textures");
                JsonObject skin = textures.getAsJsonObject("SKIN");
                JsonElement textureElement = skin.get("url");
                return textureElement.getAsString();
            }
        }
        return null;
    }

}
