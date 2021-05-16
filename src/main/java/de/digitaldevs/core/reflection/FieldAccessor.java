package de.digitaldevs.core.reflection;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * This class is an util for getting and modifying objects during runtime.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class FieldAccessor {

    /**
     * Sets a new value into an field of an object.
     *
     * @param toModify  The object where a field should be modified. Cannot be null.
     * @param fieldName The name of the field wich should be modified. Cannot be null.
     * @param toSet     The new value the field should hold.
     */
    public static void set(@NotNull Object toModify, @NotNull String fieldName, Object toSet) {
        try {
            Field field = toModify.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(toModify, toSet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets the value of an field from an object.
     *
     * @param target    The object wich contains the field. Cannot be null.
     * @param fieldName The name of teh field. Cannot be null.
     * @return The value the field is holding. Can be an {@code Object} or {@code null}. Note, {@code null} is returned when the viel value is initialized with {@code null} or something went wrong.
     */
    public static Object get(@NotNull Object target, @NotNull String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
