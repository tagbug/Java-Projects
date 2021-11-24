package StreamExample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectingIntoMaps {
    public static class Person {
        private int id;
        private String name;

        /**
         * @param id
         * @param name
         */
        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Person [id=" + id + ", name=" + name + "]";
        }
    }

    public static Stream<Person> people() {
        return Stream.of(new Person(1001, "Peter"), new Person(1002, "Paul"), new Person(1003, "Mary"));
    }

    public static void main(String[] args) {
        /*
         * Map<Integer, String> idToName =
         * people().collect(Collectors.toMap(Person::getId, Person::getName));
         * System.out.println("idToName: " + idToName);
         * 
         * var idToPerson = people().collect(Collectors.toMap(Person::getId,
         * Function.identity())); System.out.println("idToPerson: " +
         * idToPerson.getClass().getName() + idToPerson);
         * 
         * idToPerson = people() .collect(Collectors.toMap(Person::getId,
         * Function.identity(), (existingValue, newValue) -> { throw new
         * IllegalStateException(); }, TreeMap::new)); System.out.println("idToPerson: "
         * + idToPerson.getClass().getName() + idToPerson);
         * 
         * var locales = Stream.of(Locale.getAvailableLocales()); Map<String, String>
         * languageNames = locales.collect( Collectors.toMap(Locale::getDisplayLanguage,
         * l -> l.getDisplayLanguage(l), (exist, newValue) -> exist));
         * System.out.println("languageNames: " + languageNames);
         */

        var locales = Stream.of(Locale.getAvailableLocales());
        var countryLanguageSets = locales.filter(elem -> elem.getDisplayCountry().matches(".*(中国|台湾).*"))
                .collect(Collectors.toMap(Locale::getDisplayCountry, l -> Set.of(l.getDisplayLanguage()), (a, b) -> {
                    Set<String> union = new HashSet<>(a);
                    union.addAll(b);
                    return union;
                }));
        System.out.println("countryLanguageSets: " + countryLanguageSets);
    }
}
