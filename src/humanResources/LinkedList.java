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
        if(!(value instanceof Employee)){
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
        Employee[] employees = getEmployees();
        for (Employee employee : employees) {
            if (node.getValue().equals(employee) && !(value instanceof Employee))
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

    public boolean addAllSet(Collection<? extends Employee> c){
        Employee[] employeesCollection = (Employee[]) c.toArray();
        Node<T> node;
        Employee[] employees = getEmployees();
        int counter = 0;

        for(int j = 0; j < employeesCollection.length; j++) {
            node = new Node<>((T)employeesCollection[j]);
            for (int i = 0; i < employees.length; i++) {
                if (node.getValue().equals(employees[i]))
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

    public boolean addAllNode(Collection<? extends Employee> c) {
        Employee[] employeesCollection = (Employee[]) c.toArray();
        Node<T> node;
        Employee[] employees = getEmployees();
        int counter = 0;

        for (int j = 0; j < employeesCollection.length; j++) {
            node = new Node<>((T) employeesCollection[j]);
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
        Employee[] retainEmployees = (Employee[]) c.toArray();
        Employee[] currentEmployees = getEmployees();
        int counter = 0;

        clearList();

        for(int i = 0; i < currentEmployees.length; i++) {
            for (int j = 0; j < retainEmployees.length; j++) {
                if (currentEmployees[i].equals(retainEmployees[j])) {
                    addNodeSet((T) retainEmployees[j]);
                    counter++;
                }
            }
        }

        return counter <= 0;
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
        Employee[] employeesCollection = (Employee[]) c.toArray();

        for(int i = 0; i < employeesCollection.length; i++){
            while(current != null){
                if(current.getValue().equals(employeesCollection[i])){
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
        Employee[] employeesCollection = (Employee[]) c.toArray();
        Node<T> current = head;
        int counter = 0;

        for(int i = 0; i < employeesCollection.length; i++) {
            while (current != null) {
                if (current.getValue().equals(employeesCollection[i])){
                    counter++;
                    break;
                }
                current = current.getNext();
            }
        }

        return counter == employeesCollection.length;
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
        ListIterator<T> listIterator = new ListIterator<>((T[])getEmployees());
        return listIterator.iterator();
    }
}
