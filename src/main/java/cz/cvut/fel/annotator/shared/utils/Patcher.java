package cz.cvut.fel.annotator.shared.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Patcher {

    public static <T extends Object> void setIfChanged(Supplier<T> fromGetter,
                                                       Supplier<T> toGetter, Consumer<T> toSetter) {
        T fromValue = fromGetter.get();
        T toValue = toGetter.get();
        if (fromValue != null && fromValue != toValue) {
            toSetter.accept(fromValue);
        }
    }
}