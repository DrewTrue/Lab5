package humanResources;

import java.time.*;
import java.util.*;
import java.util.ListIterator;

public class Project implements EmployeeGroup{
    private String name;
    private int size;
    private Node<Employee> head;
    private Node<Employee> tail;

    private final static Node DEFAULT_HEAD = null;
    private final static Node DEFAULT_TAIL = null;
    private final static int DEFAULT_SIZE = 0;

    public Project(String name){
        this.name = name;
        //this.head = DEFAULT_HEAD;
        //this.tail = DEFAULT_TAIL;
        this.size = DEFAULT_SIZE;
    }

    public Project(String name, Employee[] employees){
        this.name = name;
        this.size = employees.length;
        Node<Employee> node;
        for(int i = 0; i < employees.length; i++){
            node = new Node<>(employees[i]);
            if(head == null)
                head = node;
            else
                tail.setNext(node);
            tail = node;
        }
    }

    @Override
    public int getPartTimeEmployeesQuantity(){
        int quantity = 0;
        Employee[] employees = getEmployees();
        for(int i = 0; i < size; i++) {
            if(employees[i] instanceof PartTimeEmployee) {
                quantity++;
            }
        }
        return quantity;
    }

    @Override
    public int getStaffEmployeesQuantity(){
        int quantity = 0;
        Employee[] employees = getEmployees();
        for(int i = 0; i < size; i++){
            if(employees[i] instanceof StaffEmployee){
                quantity++;
            }
        }
        return quantity;
    }

    @Override
    public int getCurrentTravellersQuantity(){
        int quantity = 0;
        Employee[] employees = getEmployees();
        for(int i = 0; i < size; i++){
            if(((StaffEmployee)employees[i]).isTravelNow())
                quantity++;
        }
        return quantity;
    }

    @Override
    public Employee[] getCurrentTravellers(LocalDate beginTravelMark, LocalDate endTravelMark){
        Employee[] newEmployees = new Employee[getStaffEmployeesQuantity()];
        Employee[] currentEmployees = getEmployees();
        int counter = 0;
        for(int i = 0; i < size; i++){
            if(((StaffEmployee)currentEmployees[i]).getTravelDaysFromTimeLapse(beginTravelMark, endTravelMark) > 0)
                newEmployees[counter] = currentEmployees[i];
        }
        return newEmployees;
    }

    @Override
    public void addEmployee(Employee employee) throws AlreadyAddedException {
        Employee[] employeesHelper = getEmployees();
        for(int i = 0; i < employeesHelper.length; i++){
            if(employee.equals(employeesHelper[i])){
                throw new AlreadyAddedException();
            }
        }
        Node<Employee> node = new Node<>(employee);
        if(head == null)
            head = node;
        else
            tail.setNext(node);
        tail = node;
        size++;
    }

    @Override
    public Employee[] getEmployeesSortedBySalary() {
        Employee[] employees = new Employee[size];
        if(size > 1) {
            //quickSort(employees, 0, size - 1);
            Arrays.sort(employees);
            return employees;
        }
        return employees;
    }

    @Override
    public Employee[] getEmployees(){
        Employee[] employees = new Employee[size];
        Node node = head;
        int counter = 0;
        while(node != null){
            employees[counter] = (Employee) node.getValue();
            node = node.getNext();
            counter++;
        }
        return employees;
    }

    @Override
    public int employeeQuantity(){
        return size;
    }

    @Override
    public boolean removeEmployee(String firstName, String secondName){
        Node<Employee> current = head;
        Node<Employee> previous = null;

        while(current != null){
            if(current.getValue().getFirstName().equals(firstName) && current.getValue().getSecondName().equals(secondName)){
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

    @Override
    public boolean removeEmployee(Employee employee){
        Node<Employee> current = head;
        Node<Employee> previous = null;

        while(current != null){
            if(current.getValue().equals(employee)){
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

    @Override
    public Employee getEmployee(String firstName, String secondName){
        Employee[] employees = getEmployees();
        for(int i = 0; i < size; i++){
            if(employees[i].getFirstName().equals(firstName) && employees[i].getSecondName().equals(secondName)){
                return employees[i];
            }
        }
        return null;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    @Override
    public Employee mostValuableEmployee(){
        return getEmployeesSortedBySalary()[0];
    }

    private void swapEmployee(Employee[] employees, int i, int j) {
        Employee template = employees[i];
        employees[i] = employees[j];
        employees[j] = template;
    }

    private void quickSort(Employee[] employees, int begin, int end) {
        int i = begin, j = end, pivot = employees[(begin + end) / 2].getSalary();
        do {
            while (employees[i].getSalary() > pivot && i < end) i++;
            while (employees[j].getSalary() < pivot && j > begin) j--;
            if (i <= j) {
                swapEmployee(employees, i, j);
                i++;
                j--;
            }
        }
        while (i <= j);
        if (begin < j) quickSort(employees, begin, j);
        if (i < end) quickSort(employees, i, end);
    }

    @Override
    public Employee get(int index) {
        return null;
    }

    @Override
    public Employee set(int index, Employee element) {
        return null;
    }

    @Override
    public void add(int index, Employee element) {

    }

    @Override
    public Employee remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Employee> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Employee> listIterator(int index) {
        return null;
    }

    @Override
    public List<Employee> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Employee> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Employee employee) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Employee> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Employee> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj instanceof Project && name == ((Project) obj).name && size == ((Project) obj).size) {
            Employee[] employee = getEmployees();
            Employee[] employeeObject = ((Project) obj).getEmployees();
            for(int i = 0; i < size; i++) {
                if(employee[i].equals(employeeObject[i]))
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Project ").append(name).append(": ").append(size).append("\n");
        Employee[] employees = getEmployees();
        for(int i = 0; i < size; i++){
            result.append(employees[i].toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        Employee[] employees = getEmployees();
        return name.hashCode() ^ size ^ employees.hashCode();
    }
}