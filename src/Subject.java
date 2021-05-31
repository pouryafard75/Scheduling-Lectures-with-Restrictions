import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Subject {
    public static ArrayList<Subject> list =new ArrayList<>();
    public Set<Teacher> teachers;
    private String name;

    //Getter for private field;
    public String getName()
    {
        return name;
    }


    public Subject(String name) {
        this.name = name;
        teachers = new HashSet<>();
    }

    public static Subject findByName(String name){
        for(Subject walk:list)
            if(walk.name.equals(name))
                return walk;
        return null;
    }

    @Override
    public String toString() {
        String ret = name+": ";
        for (Teacher t:teachers)
            ret+=t.getName()+" ";
        return ret;
    }
}
