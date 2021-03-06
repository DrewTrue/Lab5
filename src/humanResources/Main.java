package humanResources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args){
        String[] array = new String[]{"s", "Ad", "asd", "rq", "asdgg"};
        ListIterator<String> listIterator = new ListIterator<>(array);
        Iterator<String> it = listIterator.iterator();

        while (it.hasNext()){
            System.out.println(it.next());
        }

        List<Integer> araBringGoat = new ArrayList<>();
        araBringGoat.add(1);
        araBringGoat.add(12);
        //araBringGoat.add(111);

        List<Integer> araBringGoat2 = new ArrayList<>();
        araBringGoat2.add(1);
        araBringGoat2.add(12);
        araBringGoat2.add(111);

        System.out.println(araBringGoat.retainAll(araBringGoat2));
        System.out.println(araBringGoat.isEmpty());
        System.out.println(araBringGoat.contains(111));

        Department department1 = new Department("a");
        Department department2 = new Department("b");
        Department department3 = new Department("c");

        Department[] departments = new Department[]{department1,department2,department3};

        for (Department department:departments) {
            System.out.println(department.getName());
        }

    }
}
