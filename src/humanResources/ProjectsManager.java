package humanResources;

import java.time.LocalDate;
import java.util.*;
import java.util.ListIterator;

public class ProjectsManager implements GroupsManager{
    //private Node<EmployeeGroup> head;
    //private Node<EmployeeGroup> tail;
    private LinkedList<EmployeeGroup> list;
    private int size;

    private static final int DEFAULT_SIZE = 1;

    public ProjectsManager(){
        this(new Node<>(null));
    }

    public ProjectsManager(Node<EmployeeGroup> head){
        //this.head = head;
        //tail.setNext(head);
        //this.tail = head;
        list.addNodeList(head.getValue());
        this.size = DEFAULT_SIZE;
    }

    @Override
    public int getPartTimeEmployeesQuantity(){
        int quantity = 0;
        Employee[] employees;
        EmployeeGroup[] groups = getEmployeesGroups();
        for(int i = 0; i < groups.length; i++) {
            employees = groups[i].getEmployees();
            for(int j = 0; j < employees.length; j++) {
                if (employees[j] instanceof PartTimeEmployee) {
                    quantity++;
                }
            }
        }
        return quantity;
    }

    @Override
    public int getStaffEmployeesQuantity(){
        int quantity = 0;
        Employee[] employees;
        EmployeeGroup[] groups = getEmployeesGroups();
        for(int i = 0; i < groups.length; i++){
            employees = groups[i].getEmployees();
            for(int j = 0; j < employees.length; j++) {
                if (employees[j] instanceof StaffEmployee) {
                    quantity++;
                }
            }
        }
        return quantity;
    }

    @Override
    public int getCurrentTravellersQuantity(){
        int quantity = 0;
        Employee[] employees;
        EmployeeGroup[] groups = getEmployeesGroups();
        for(int i = 0; i < groups.length; i++) {
            employees = groups[i].getEmployees();
            for (int j = 0; j < employees.length; j++) {
                if (((StaffEmployee) employees[j]).isTravelNow())
                    quantity++;
            }
        }
        return quantity;
    }

    @Override
    public Employee[] getCurrentTravellers(LocalDate beginTravelMark, LocalDate endTravelMark){
        Employee[] newEmployees = new Employee[getStaffEmployeesQuantity()];
        Employee[] employees;
        EmployeeGroup[] groups = getEmployeesGroups();
        int counter = 0;
        for(int i = 0; i < groups.length; i++) {
            employees = groups[i].getEmployees();
            for (int j = 0; j < employees.length; j++) {
                if (((StaffEmployee) employees[i]).getTravelDaysFromTimeLapse(beginTravelMark, endTravelMark) > 0)
                    newEmployees[counter] = employees[i];
            }
        }
        return newEmployees;
    }

    @Override
    public EmployeeGroup[] getEmployeesGroups(){
        return list.getGroups();
    }

    @Override
    public int groupsQuantity() {
        return size;
    }

    @Override
    public EmployeeGroup getEmployeeGroup(String name) {
        EmployeeGroup[] groups = getEmployeesGroups();
        for (int i = 0; i < size; i++) {
            if (groups[i] != null && groups[i].getName().equals(name))
                return groups[i];
        }
        return null;
    }

    @Override
    public Employee mostValuableEmployee() {
        int maxSalary = 0, index = 0;
        Employee[] employee;
        EmployeeGroup[] groups = getEmployeesGroups();
        for (int i = 0; i < size; i++) {
            if (groups[i] != null) {
                employee = groups[i].getEmployeesSortedBySalary();
                if (employee[0] != null && employee[0].getSalary() > maxSalary) {
                    maxSalary = employee[0].getSalary();
                    index = i;
                }
            }
        }
        employee = groups[index].getEmployeesSortedBySalary();
        return employee[0];
    }

    @Override
    public EmployeeGroup getEmployeesGroup(String firstName, String secondName) {
        EmployeeGroup[] groups = getEmployeesGroups();
        Employee[] employee;
        for (int i = 0; i < size; i++) {
            if (groups[i] != null) {
                employee = groups[i].getEmployees();
                for(int j = 0; j < employee.length; j++) {
                    if (employee[j] != null && (employee[j].getFirstName().equals(firstName)
                            && employee[j].getSecondName().equals(secondName)))
                        return groups[i];
                }
            }
        }
        return null;
    }

    @Override
    public int employeesQuantity(JobTitlesEnum jobTitle) {
        int quantity = 0;
        EmployeeGroup[] groups = getEmployeesGroups();
        Employee[] employee;
        for (int i = 0; i < size; i++) {
            if(groups[i] != null) {
                for(int j = 0; j < groups[i].getEmployees().length; j++){
                    employee = groups[i].getEmployees();
                    if(employee[j].getJobTitle().equals(jobTitle))
                        quantity++;
                }
            }
        }
        return quantity;
    }

    @Override
    public int employeesQuantity() {
        int quantity = 0;
        EmployeeGroup[] groups = getEmployeesGroups();
        for (int i = 0; i < size; i++) {
            if(groups[i] != null) {
                quantity += groups[i].employeeQuantity();
            }
        }
        return quantity;
    }

    @Override
    public void addGroup(EmployeeGroup group) throws AlreadyAddedException {
        list.addNodeList(group);
    }

    @Override
    public boolean removeGroup(String name) {
        EmployeeGroup[] employeeGroups = getEmployeesGroups();

        for(int i = 0; i < size; i++){
            if(employeeGroups[i].getName().equals(name))
                return list.removeNode(employeeGroups[i]);
        }
        return false;
    }

    @Override
    public int removeGroup(EmployeeGroup group) {
        int counter = 0;

        while(list.removeNode(group)){
            counter++;
        }

        return counter;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return list.contains((EmployeeGroup) o);
    }

    @Override
    public Iterator<EmployeeGroup> iterator() {
        humanResources.ListIterator<EmployeeGroup> iterator = new humanResources.ListIterator<>(getEmployeesGroups());
        return iterator.iterator();
    }

    @Override
    public Object[] toArray() {
        return getEmployeesGroups();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        EmployeeGroup[] employeeGroups = getEmployeesGroups();

        if(a.length < size)
            a = (T[]) new EmployeeGroup[size];

        System.arraycopy(employeeGroups, 0, a, 0, size);

        return null;
    }

    @Override
    public boolean add(EmployeeGroup group) {
        return list.addNodeList(group);
    }

    @Override
    public boolean remove(Object o) {
        return list.removeNode((EmployeeGroup) o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAllGroups(c);
    }

    @Override
    public boolean addAll(Collection<? extends EmployeeGroup> c) {
        return list.addAllGroups(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends EmployeeGroup> c) {
        return list.addAllGroups(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAllGroups(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAllGroups(c);
    }

    @Override
    public void clear() {
        list.clearList();
    }

    @Override
    public EmployeeGroup get(int index) {
        EmployeeGroup[] employeeGroups = getEmployeesGroups();

        for(int i = 0; i < size; i++){
            if(i == index)
                return employeeGroups[i];
        }

        return null;
    }

    @Override
    public EmployeeGroup set(int index, EmployeeGroup element) {
        return list.setGroup(index, element);
    }

    @Override
    public void add(int index, EmployeeGroup element) {
        list.addNodeList(index, element);
    }

    @Override
    public EmployeeGroup remove(int index) {
        return list.removeGroup(index);
    }

    @Override
    public int indexOf(Object o) {
        EmployeeGroup[] employeeGroups = getEmployeesGroups();

        for(int i = 0; i < size; i++){
            if(employeeGroups[i].equals(o))
                return i;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        EmployeeGroup[] employeeGroups = getEmployeesGroups();

        for(int i = size - 1; i > -1; i--){
            if(employeeGroups[i].equals(o))
                return i;
        }

        return -1;
    }

    @Override
    public ListIterator<EmployeeGroup> listIterator() {
        humanResources.ListIterator<EmployeeGroup> iterator = new humanResources.ListIterator<>(getEmployeesGroups());
        return (ListIterator<EmployeeGroup>) iterator.iterator();
    }

    @Override
    public ListIterator<EmployeeGroup> listIterator(int index) {
        humanResources.ListIterator<EmployeeGroup> iterator = new humanResources.ListIterator<>(getEmployeesGroups(), index);
        return (ListIterator<EmployeeGroup>) iterator.iterator();
    }

    @Override
    public List<EmployeeGroup> subList(int fromIndex, int toIndex) {
        EmployeeGroup[] groups = getEmployeesGroups();

        if(fromIndex < toIndex && fromIndex >= 0 && toIndex <= size) {
            LinkedList<EmployeeGroup> list = new LinkedList<>();
            for (int i = fromIndex; i <= toIndex; i++) {
                list.addNodeList(groups[i]);
            }
            return (List<EmployeeGroup>) list;
        }

        return null;
    }
}
