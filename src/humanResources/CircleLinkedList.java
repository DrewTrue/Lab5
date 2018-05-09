package humanResources;

import java.util.Collection;
import java.util.Iterator;

public class CircleLinkedList<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Node<T> getHead(){
        return head;
    }

    public Node<T> getTail(){
        return tail;
    }

    public boolean addNodeSet(T value){
        Node<T> node = new Node<T>(value);
        BusinessTravel[] businessTravels = getTravels();
        for(int i = 0; i < businessTravels.length; i++){
            if(node.getValue().equals(businessTravels[i]) && !(value instanceof BusinessTravel))
                return false;
        }
        if(head == null) {
            head = node;
            tail = node;
            tail.setNext(head);
        }
        else {
            node.setNext(head);
            tail.setNext(node);
            tail = node;
        }
        size++;
        return true;
    }

    public boolean addAllSets(Collection<? extends BusinessTravel> c){
        BusinessTravel[] businessTravels = (BusinessTravel[]) c.toArray();
        Node<T> node;
        BusinessTravel[] travels = getTravels();
        int counter = 0;

        for(int i = 0; i < travels.length; i++){
            node = new Node<>((T)travels[i]);

            for (int j = 0; j < travels.length; j++) {
                if (node.getValue().equals(travels[j]))
                    break;
            }
            if(head == null) {
                head = node;
                tail = node;
                tail.setNext(head);
            }
            else {
                node.setNext(head);
                tail.setNext(node);
                tail = node;
            }
            size++;
            counter++;
        }

        return counter > 0;
    }

    public boolean retainAll(Collection<?> c) {
        BusinessTravel[] retainTravels = (BusinessTravel[]) c.toArray();
        BusinessTravel[] currentTravels = getTravels();
        int counter = 0;

        clearList();

        for(int i = 0; i < currentTravels.length; i++) {
            for (int j = 0; j < retainTravels.length; j++) {
                if (currentTravels[i].equals(retainTravels[j])) {
                    addNodeSet((T) retainTravels[j]);
                    counter++;
                }
            }
        }

        return counter == 0;
    }

    public boolean removeNode(T value){
        Node<T> current = head;
        Node<T> previous = null;

        if(isEmpty())
            return false;

        do{
            if(current.getValue().equals(value)){
                if(previous != null){
                    previous.setNext(current.getNext());
                    if(current == tail)
                        tail = previous;
                }
                else{
                    head = current.getNext();
                    tail.setNext(current.getNext());
                }
                size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }while(current != head);

        return false;
    }

    public boolean removeAll(Collection<?> c) {
        Node<T> current = head;
        Node<T> previous = null;
        int counter = 0;
        BusinessTravel[] businessTravels = (BusinessTravel[]) c.toArray();

        if (isEmpty())
            return false;

        for (int i = 0; i < businessTravels.length; i++) {
            do {
                if (current.getValue().equals(businessTravels[i])) {
                    if (previous != null) {
                        previous.setNext(current.getNext());
                        if (current == tail)
                            tail = previous;
                    } else {
                        head = current.getNext();
                        tail.setNext(current.getNext());
                    }
                    size--;
                    counter++;
                    break;
                }
                previous = current;
                current = current.getNext();
            } while (current != head);
        }

        return counter > 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clearList(){
        head = null;
        tail = null;
        size =  0;
    }

    public boolean contains(T value){
        Node<T> current = head;

        while(current != null){
            if(current.getValue().equals(value))
                return true;
            current = current.getNext();
        }

        return false;
    }

    public boolean containsAll(Collection<?> c){
        Node<T> current = head;
        BusinessTravel[] businessTravels = (BusinessTravel[]) c.toArray();
        int counter = 0;
        for(int i = 0; i < businessTravels.length; i++) {
            while (current != null) {
                if (current.getValue().equals(businessTravels[i])) {
                    counter++;
                    break;
                }
                current = current.getNext();
            }
        }

        return counter == businessTravels.length;
    }

    public BusinessTravel[] getTravels(){
        BusinessTravel[] businessTravels = new BusinessTravel[size];
        Node node = head;
        int counter = 0;
        do {
            businessTravels[counter] = (BusinessTravel) node.getValue();
            node = node.getNext();
            counter++;
        }while(node != head);
        return businessTravels;
    }

    public Iterator<T> iterator(){
        ListIterator<T> iterator = new ListIterator<>((T[])getTravels());
        return iterator.iterator();
    }

    public ListIterator<T> listIterator(){
        ListIterator<T> listIterator = new ListIterator<>((T[])getTravels());
        return (ListIterator<T>) listIterator.iterator();
    }
}