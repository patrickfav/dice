package at.favre.tools.dice.encode;

import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Loader {

    public List<Encoder> load() {
        Reflections reflections = new Reflections("at.favre.tools.dice.encode");

        Set<Class<? extends Encoder>> allClasses = reflections.getSubTypesOf(Encoder.class);

        List<Encoder> encoders = new ArrayList<>(allClasses.size());
        for (Class<? extends Encoder> clazz : allClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                try {
                    encoders.add(clazz.newInstance());
                } catch (Exception e) {
                    throw new IllegalStateException("cannot instanciate " + clazz.getSimpleName() + ". Does it have a no-arg constructor?");
                }
            }
        }

        Set<String> modes = new HashSet<>();

        for (Encoder encoder : encoders) {
            for (String name : encoder.names()) {
                if (modes.contains(name)) {
                    throw new IllegalStateException(name + " is already defined in another encoder");
                }
                modes.add(name);
            }
        }

        return encoders;
    }
}
