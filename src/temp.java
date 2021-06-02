import java.util.ArrayList;

public class temp {
    public static void main(String[] args) {
        Property p1 = new Property(new Person(20,"ShervinJan"));
        Person owner = p1.owner;
        p1.owner.print();
        owner.age = 4;
        p1.owner.print();


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