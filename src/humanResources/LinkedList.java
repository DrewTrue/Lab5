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
        Node<T> node = new Node<>(value);

        if(head == null)
            head = node;
        else
            tail.setNext(node);
        tail = node;
        size++;

        return true;
    }

    //bullshit method
    public boolean addNodeList(int index, T value){
        Node<T> nodeValue = new Node<T>(value);

        Node<T> node = head;
        Node<T> currentNode;
        int counter = 0;

        if(!isEmpty()) {
            if (index == 0) { // if node is in the beginning
                currentNode = head;
                head = nodeValue;
                head.setNext(currentNode);
                size++;
                return true;
            }

            if (index == size - 1) { // if node is in the end
                currentNode = tail;
                tail = nodeValue;
                return addNodeList(value);
            }
        } else {
            return addNodeList(value);
        }

        while(node != null) {
            if(counter == index){ // if node is in the middle
                currentNode = node;
                node = nodeValue;
                node.setNext(currentNode);
            }

            counter++;
            node = node.getNext();
        }

        if(counter > size){
            size = counter;
            return true;
        }

        return false;
    }

    public boolean addAllEmployees(Collection<? extends Employee> c) {
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

    public boolean addAllGroups(Collection<? extends EmployeeGroup> c) {
        EmployeeGroup[] groupsCollection = (EmployeeGroup[]) c.toArray();
        Node<T> node;
        EmployeeGroup[] groups = getGroups();
        int counter = 0;

        for (int j = 0; j < groupsCollection.length; j++) {
            node = new Node<>((T) groupsCollection[j]);
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

    //bullshit method x2
    public boolean addAllEmployees(int index, Collection<? extends Employee> c) {
        Employee[] employeesCollection = (Employee[]) c.toArray();
        Employee[] employees = getEmployees();
        boolean isAddAll1 = false;
        boolean isAddAll2 = false;
        boolean isAddAll3 = false;

        clearList();

        for(int i = 0; i < index; i++){
            isAddAll1 = addNodeList((T) employees[i]);
        }

        for (Employee anEmployeesCollection : employeesCollection) {
            isAddAll2 = addNodeList((T) anEmployeesCollection);
        }

        for(int i = index; i < employees.length; i++){
            isAddAll3 = addNodeList((T)employees[i]);
        }

        return isAddAll1 && isAddAll2 && isAddAll3;
    }

    public boolean addAllGroups(int index, Collection<? extends EmployeeGroup> c) {
        EmployeeGroup[] groupsCollection = (EmployeeGroup[]) c.toArray();
        EmployeeGroup[] groups = getGroups();
        boolean isAddAll1 = false;
        boolean isAddAll2 = false;
        boolean isAddAll3 = false;

        clearList();

        for(int i = 0; i < index; i++){
            isAddAll1 = addNodeList((T) groups[i]);
        }

        for (EmployeeGroup group : groupsCollection) {
            isAddAll2 = addNodeList((T) group);
        }

        for(int i = index; i < groups.length; i++){
            isAddAll3 = addNodeList((T)groups[i]);
        }

        return isAddAll1 && isAddAll2 && isAddAll3;
    }

    public EmployeeGroup setGroup(int index, T value){
        Node<T> nodeValue = new Node<T>(value);

        Node<T> node = head;
        Node<T> currentNode;
        int counter = 0;

        if (index == 0) { // if node is in the beginning
            head = nodeValue;
            return (EmployeeGroup) head.getValue();
        }

        if(index == size - 1){ // if node is in the end
            tail = nodeValue;
            return (EmployeeGroup) tail.getValue();
        }

        while(node != null) {
            if(counter == index){ // if node is in the middle
                node = nodeValue;
                return (EmployeeGroup) node.getValue();
            }

            counter++;
            node = node.getNext();
        }

        return null;
    }

    public Employee setEmployee(int index, T value){
        Node<T> nodeValue = new Node<T>(value);

        Node<T> node = head;
        Node<T> currentNode;
        int counter = 0;

        if (index == 0) { // if node is in the beginning
            head = nodeValue;
            return (Employee) head.getValue();
        }

        if(index == size - 1){ // if node is in the end
            tail = nodeValue;
            return (Employee) tail.getValue();
        }

        while(node != null) {
            if(counter == index){ // if node is in the middle
                node = nodeValue;
                return (Employee) node.getValue();
            }

            counter++;
            node = node.getNext();
        }

        return null;
    }

    public boolean retainAllEmployees(Collection<?> c) {
        Employee[] retainEmployees = (Employee[]) c.toArray();
        Employee[] currentEmployees = getEmployees();
        int counter = 0;

        clearList();

        for(int i = 0; i < currentEmployees.length; i++) {
            for (int j = 0; j < retainEmployees.length; j++) {
                if (currentEmployees[i].equals(retainEmployees[j])) {
                    addNodeList((T) retainEmployees[j]);
                    counter++;
                }
            }
        }

        return counter <= 0;
    }

    public boolean retainAllGroups(Collection<?> c) {
        EmployeeGroup[] retainGroups = (EmployeeGroup[]) c.toArray();
        EmployeeGroup[] currentGroups = getGroups();
        int counter = 0;

        clearList();

        for(int i = 0; i < currentGroups.length; i++) {
            for (int j = 0; j < retainGroups.length; j++) {
                if (currentGroups[i].equals(retainGroups[j])) {
                    addNodeList((T) retainGroups[j]);
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

    public EmployeeGroup removeGroup(int index){
        EmployeeGroup[] employeeGroups = getGroups();
        for(int i = 0; i < size; i++){
            if(index == i){
                removeNode((T) employeeGroups[i]);
                return employeeGroups[i];
            }
        }

        return null;
    }

    public Employee removeEmployee(int index){
        Employee[] employees = getEmployees();
        for(int i = 0; i < size; i++){
            if(index == i){
                removeNode((T) employees[i]);
                return employees[i];
            }
        }

        return null;
    }

    public boolean removeAllEmployees(Collection<?> c) {
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

    public boolean removeAllGroups(Collection<?> c) {
        Node<T> current = head;
        Node<T> previous = null;
        int counter = 0;
        EmployeeGroup[] groups = (EmployeeGroup[]) c.toArray();

        for(int i = 0; i < groups.length; i++){
            while(current != null){
                if(current.getValue().equals(groups[i])){
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

    public boolean containsAllEmployees(Collection<?> c){
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

    public boolean containsAllGroups(Collection<?> c){
        EmployeeGroup[] groups = (EmployeeGroup[]) c.toArray();
        Node<T> current = head;
        int counter = 0;

        for(int i = 0; i < groups.length; i++) {
            while (current != null) {
                if (current.getValue().equals(groups[i])){
                    counter++;
                    break;
                }
                current = current.getNext();
            }
        }

        return counter == groups.length;
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

    public EmployeeGroup[] getGroups(){
        EmployeeGroup[] employeeGroups = new EmployeeGroup[size];
        Node node = head;
        int counter = 0;

        while(node != null){
            employeeGroups[counter] = (EmployeeGroup) node.getValue();
            node = node.getNext();
            counter++;
        }

        return employeeGroups;
    }

    public BusinessTravel[] getTravels(){
        BusinessTravel[] businessTravels = new BusinessTravel[size];
        Node node = head;
        int counter = 0;
        while(node != null) {
            businessTravels[counter] = (BusinessTravel) node.getValue();
            node = node.getNext();
            counter++;
        }
        return businessTravels;
    }

    public Iterator<T> iteratorEmployees(){
        ListIterator<T> iterator = new ListIterator<>((T[])getEmployees());
        return iterator.iterator();
    }

    public ListIterator<T> listIteratorEmployees(){
        ListIterator<T> listIterator = new ListIterator<>((T[])getEmployees());
        return (ListIterator<T>) listIterator.iterator();
    }

    public Iterator<T> iteratorGroups(){
        ListIterator<T> iterator = new ListIterator<>((T[])getGroups());
        return iterator.iterator();
    }

    public ListIterator<T> listIteratorGroups(){
        ListIterator<T> listIterator = new ListIterator<>((T[])getGroups());
        return (ListIterator<T>) listIterator.iterator();
    }
}
