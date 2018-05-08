package humanResources;

import java.util.*;

public class LinkedList<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Node<T> getHead(){
        return head;
    }

    public Node<T> getTail(){
        return tail;
    }

    public boolean addNodeList(T value){
        if(!(value instanceof BusinessTravel)){
            return false;
        }
        Node<T> node = new Node<T>(value);
        if(head == null)
            head = node;
        else
            tail.setNext(node);
        tail = node;
        size++;
        return true;
    }

    public boolean addNodeSet(T value){
        Node<T> node = new Node<T>(value);
        BusinessTravel[] travels = getTravels();
        for(int i = 0; i < travels.length; i++){
            if(node.getValue().equals(travels[i]) && !(value instanceof BusinessTravel))
                return false;
        }
        if(head == null)
            head = node;
        else
            tail.setNext(node);
        tail = node;
        size++;
        return true;
    }

    public boolean addAllSet(Collection<? extends BusinessTravel> c){
        BusinessTravel[] businessTravels = (BusinessTravel[]) c.toArray();
        Node<T> node;
        BusinessTravel[] travels = getTravels();
        int counter = 0;

        for(int j = 0; j < businessTravels.length; j++) {
            node = new Node<>((T)businessTravels[j]);
            for (int i = 0; i < travels.length; i++) {
                if (node.getValue().equals(travels[i]))
                    break;
            }
            if (head == null)
                head = node;
            else
                tail.setNext(node);
            tail = node;
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

        while(current != null){
            if(current.getValue().equals(value)){
                if(previous != null){
                    previous.setNext(current.getNext());
                    if(current.getNext() == null)
                        tail = previous;
                }
                else{
                    head = head.getNext();
                    if(head == null)
                        tail = null;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        Node<T> current = head;
        Node<T> previous = null;
        int counter = 0;
        BusinessTravel[] businessTravels = (BusinessTravel[]) c.toArray();

        for(int i = 0; i < businessTravels.length; i++){
            while(current != null){
                if(current.getValue().equals(businessTravels[i])){
                    if(previous != null){
                        previous.setNext(current.getNext());
                        if(current.getNext() == null)
                            tail = previous;
                    }
                    else{
                        head = head.getNext();
                        if(head == null)
                            tail = null;
                    }
                    size--;
                    counter++;
                    break;
                }
                previous = current;
                current = current.getNext();
            }
        }

        return counter > 0;
    }

    public int getSize() {
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
        BusinessTravel[] businessTravels = (BusinessTravel[]) c.toArray();
        Node<T> current = head;
        int counter = 0;

        for(int i = 0; i < businessTravels.length; i++) {
            while (current != null) {
                if (current.getValue().equals(businessTravels[i])){
                    counter++;
                    break;
                }
                current = current.getNext();
            }
        }

        return counter == businessTravels.length;
    }

    public BusinessTravel[] getTravels(){
        BusinessTravel[] travels = new BusinessTravel[size];
        Node node = head;
        int counter = 0;
        while(node != null){
            travels[counter] = (BusinessTravel) node.getValue();
            node = node.getNext();
            counter++;
        }
        return travels;
    }

    public Employee[] getEmployees(){
        Employee[] employee = new Employee[size];
        Node node = head;
        int counter = 0;
        while(node != null){
            employee[counter] = (Employee) node.getValue();
            node = node.getNext();
            counter++;
        }
        return employee;
    }

    public Iterator<T> iterator(){
        ListIterator<T> listIterator = new ListIterator<>((T[])getTravels());
        return listIterator.iterator();
    }
}
