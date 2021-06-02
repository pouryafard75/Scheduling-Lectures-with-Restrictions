import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class temp {
    public static void main(String[] args) {
        Set<Person> set = new HashSet<>();
        set.add(new Person(10,"p"));
        set.add(new Person(32,"q"));
        Iterator<Person> it = set.iterator();
        it.next().print();
        it.next().print();

    }
}
class Property{
    public final Person owner;
    Property(Person owner)
    {
        this.owner = owner;
    }
}
class Person{
    public int age;
    private String name;
    public Person(int age, String name) {  this.age = age;  this.name = name; }
    public void print()  {  System.out.println(age + " " + name); }
}