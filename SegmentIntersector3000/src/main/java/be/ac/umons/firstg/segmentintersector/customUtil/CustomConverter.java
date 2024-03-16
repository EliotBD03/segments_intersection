package be.ac.umons.firstg.segmentintersector.customUtil;

import javafx.util.StringConverter;


/**
 * A simple encapsulation of a {@link StringConverter}, with the main goal being to
 * deal with input values that aren't comprised between a given max and min value.
 * @param <T>   The class of the converter
 */
public class CustomConverter<T extends Comparable<T>> extends StringConverter<T>
{
    private final T minValue;
    private final T maxValue;
    private final T defaultValue;
    private final  StringConverter<T> convertor;

    public CustomConverter(StringConverter<T> convertor, T minValue, T maxValue) {
        this(convertor, minValue, minValue, maxValue);
    }
    public CustomConverter(StringConverter<T> convertor, T defaultValue, T minValue, T maxValue){
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.convertor = convertor;
    }


    @Override
    public String toString(T value)
    {
        return convertor.toString(value);
    }

    @Override
    public T fromString(String valueString)
    {
        if (valueString.isEmpty()) {
            return defaultValue;
        }
        try{
            convertor.fromString(valueString);
        }catch (NumberFormatException e){
            return defaultValue;
        }
        T res = convertor.fromString(valueString);
        if(res == null)
            return defaultValue;
        return res.compareTo(maxValue) < 0 ? (res.compareTo(minValue) > 0 ? res: minValue) : maxValue;
    }

}
