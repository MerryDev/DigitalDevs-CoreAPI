package de.digitaldevs.core.reflection;

import de.digitaldevs.core.reflection.types.PackageType;
import org.jetbrains.annotations.NotNull;

/**
 * This class is an util for accessing classes during runtime.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class ClassAccessor {

    /**
     * Gets the class with the specific name.
     *
     * @param clazzName The name of the class. Cannot be null.
     * @return The class or {@code null} if no class with the given name was found.
     */
    public static Class<?> access(@NotNull String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the {@code net.minecraft.server class} with the specific name. <br>
     * This method is using the {@code access()}-method.
     *
     * @param clazzName     The name of the class. Cannot be null.
     * @param serverVersion The version the server is running on. Cannot be null.
     * @return The class or {@code null} if no class with the given name was found.
     */
    public static Class<?> accessNMSClazz(@NotNull String clazzName, @NotNull String serverVersion) {
        return access("net.minecraft.server." + serverVersion + "." + clazzName);
    }

    /**
     * Gets the {@code org.bukkit class} with the specific name. The class is located in the root package {@code org.bukkit}. <br>
     * This method is using the {@code access()}-method.
     *
     * @param clazzName The name of the class. Cannot be null.
     * @return The class or {@code null} if no class with the given name was found.
     */
    public static Class<?> accessBukkitClazz(@NotNull String clazzName) {
        return access("org.bukkit." + clazzName);
    }

    /**
     * Gets the {@code org.bukkit} class with the specific name. The class is located in a specific sub-package. <br>
     * This method is using the {@code access()}-method.
     *
     * @param packageType The package where the class is located. Cannot be null.
     * @param clazzName   The name of the class. Cannot be null.
     * @return The class or {@code null} if no class with the given name was found.
     */
    public static Class<?> accessBukkitClazzInPackage(@NotNull PackageType packageType, @NotNull String clazzName) {
        return access("org.bukkit." + packageType.getKey() + "." + clazzName);
    }

    /**
     * Gets the {@code org.bukkit.craftbukkit class} with the specific name. <br>
     * The class is located in root package {@code org.bukkit.craftbukkit.<version>}.<br>
     * This method is using the {@code access()}-method.
     *
     * @param clazzName     The name of the class. Cannot be null.
     * @param serverVersion The version the server is running on. Cannot be null.
     * @return The class or {@code null} if no class with the given name was found.
     */
    public static Class<?> accessCraftBukkitClazz(@NotNull String clazzName, @NotNull String serverVersion) {
        return access("org.bukkit.craftbukkit." + serverVersion + "." + clazzName);
    }

    /**
     * Gets the {@code org.bukkit.craftbukkit class} with the specific name. The class is located in a specific sub-package.<br>
     * This method is using the {@code access()}-method.
     *
     * @param clazzName     The name of the class. Cannot be null.
     * @param packageType   The package where the class is located. Cannot be null.
     * @param serverVersion The version the server is running on. Cannot be null.
     * @return The class or {@code null} if no class with the given name was found.
     */
    public static Class<?> accessCraftBukkitClazzInPackage(@NotNull PackageType packageType, @NotNull String clazzName, @NotNull String serverVersion) {
        return access("org.bukkit.craftbukkit." + serverVersion + "." + packageType.getKey() + "." + clazzName);
    }

}
