package humanResources;

import java.util.Iterator;

public class Main {
    public static void main(String[] args){
        String[] array = new String[]{"s", "Ad", "asd", "rq", "asdgg"};
        ListIterator<String> listIterator = new ListIterator<>(array);
        Iterator<String> it = listIterator.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
        BusinessTravel businessTravel = new BusinessTravel();
        BusinessTravel businessTravel2 = new BusinessTravel();

        LinkedList<BusinessTravel> linkedList = new LinkedList<>();
        linkedList.addNodeList(businessTravel);
        linkedList.addNodeList(businessTravel2);
        Iterator<BusinessTravel> i = linkedList.iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }
}
