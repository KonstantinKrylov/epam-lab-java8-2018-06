package lambda.part1.example;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Guava", "Convert2MethodRef", "ComparatorCombinators", "SimplifiableIfStatement"})
public class Example3 {

    @Test
    public void sortPersonsByLastNameUsingArraysLambda() {
        Person[] persons = getPersons();

        // Expression-lambda
        Arrays.sort(persons, (left, right) -> left.getLastName().compareTo(right.getLastName()));

        // Без контекста лямбда-выражение бессмысленно
        // (left, right) -> left.getLastName().compareTo(right.getLastName());

        assertArrayEquals(new Person[]{
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30),
            new Person("Иван", "Мельников", 20)
        }, persons);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void findFirstAlexUsingForeach() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> result = FluentIterable.from(persons)
                                                .firstMatch(person -> "Алексей".equals(person.getFirstName()));

        if (result.isPresent()) {
            System.out.println(result.get());
            assertEquals(new Person("Алексей", "Доренко", 40), result.get());
        }
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void lastNamesSet() {
        List<Person> persons = Arrays.asList(getPersons());

        Function<Person, String> personStringFunction = person -> person.getLastName();
        Map<String, Person> personByLastName = FluentIterable.from(persons)
                                                             .uniqueIndex(personStringFunction);

        Map<String, Person> expected = new HashMap<>(persons.size());
        expected.put("Мельников", new Person("Иван", "Мельников", 20));
        expected.put("Доренко", new Person("Алексей", "Доренко", 40));
        expected.put("Зимов", new Person("Николай", "Зимов", 30));
        assertEquals(expected, personByLastName);
    }

    private Person[] getPersons() {
        return new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30)
        };
    }
}
