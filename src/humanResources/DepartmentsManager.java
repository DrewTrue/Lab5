package humanResources;

import java.time.LocalDate;
import java.util.*;
import java.util.ListIterator;

public class DepartmentsManager implements GroupsManager{
    private String name;
    private EmployeeGroup[] groups;
    private int size;

    private final static int DEFAULT_SIZE = 8;

    public DepartmentsManager(String name) {
        this(name, new EmployeeGroup[DEFAULT_SIZE]);
    }

    public DepartmentsManager(String name, int size) {
        //this(name, new EmployeeGroup[DEFAULT_SIZE]);
        if(size < 0)
            throw new NegativeSizeException();
        this.size = size;
        this.name = name;
        this.groups = new EmployeeGroup[size];
    }

    public DepartmentsManager(String name, EmployeeGroup[] groups) {
        this.size = groups.length;
        this.name = name;
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public int getPartTimeEmployeesQuantity(){
        int quantity = 0;
        Employee[] employees;
        for(int i = 0; i < size; i++) {
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
        for(int i = 0; i < size; i++){
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
        for(int i = 0; i < size; i++) {
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
        int counter = 0;
        for(int i = 0; i < size; i++) {
            employees = groups[i].getEmployees();
            for (int j = 0; j < employees.length; j++) {
                if (((StaffEmployee) employees[i]).getTravelDaysFromTimeLapse(beginTravelMark, endTravelMark) > 0)
                    newEmployees[counter] = employees[i];
            }
        }
        return newEmployees;
    }

    @Override
    public EmployeeGroup[] getEmployeesGroups() {
        EmployeeGroup[] group = new EmployeeGroup[size];
        System.arraycopy(this.groups, 0, group,0, size);
        return group;
    }

    @Override
    public int groupsQuantity() {
        return size;
    }

    @Override
    public EmployeeGroup getEmployeeGroup(String name) {
        for (int i = 0; i < size; i++) {
            if (groups[i] != null & groups[i].getName().equals(name))
                return groups[i];
        }
        return null;
    }

    @Override
    public Employee mostValuableEmployee() {
        int maxSalary = 0, index = 0;
        Employee[] employee;
        for (int i = 0; i < size; i++) {
            if (groups[i] != null) {
                employee = groups[i].getEmployeesSortedBySalary();
                if (employee[0] != null & employee[0].getSalary() > maxSalary) {
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
        Employee[] employee;
        for (int i = 0; i < size; i++) {
            if (groups[i] != null) {
                employee = groups[i].getEmployees();
                for(int j = 0; j < employee.length; j++) {
                    if (employee[j] != null & (employee[j].getFirstName().equals(firstName)
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
        for (int i = 0; i < size; i++) {
            if(groups[i] != null) {
                for(int j = 0; j < groups[i].getEmployees().length; j++){
                    if(groups[i].getEmployees()[j].getJobTitle().equals(jobTitle))
                        quantity++;
                }
            }
        }
        return quantity;
    }

    @Override
    public int employeesQuantity() {
        int quantity = 0;
        for (int i = 0; i < size; i++) {
            if(groups[i] != null) {
                quantity += groups[i].getEmployees().length;//getSize
            }
        }
        return quantity;
    }

    @Override
    public void addGroup(EmployeeGroup group) throws AlreadyAddedException {
        EmployeeGroup[] groupsHelper = getEmployeesGroups();
        for(int i = 0; i < groupsHelper.length; i++){
            if(group.equals(groupsHelper[i]))
                throw new AlreadyAddedException();
        }
        if(group == null)
            return;
        if (size == groups.length) {
            EmployeeGroup[] groups = new EmployeeGroup[this.groups.length * 2];
            System.arraycopy(this.groups, 0, groups,0, size);
            this.groups = groups;
        }
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == null) {
                groups[i] = group;
                size++;
                break;
            }
        }
    }

    @Override
    public boolean removeGroup(String name) {
        for (int i = 0; i < size; i++) {
            if (groups[i] != null & groups[i].getName().equals(name)) {
                if(i < groups.length - 1)
                    System.arraycopy(groups, i + 1, groups, i, size - i - 1);
                groups[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int removeGroup(EmployeeGroup group) {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (groups[i] != null && groups[i].equals(group)) {
                if(i < groups.length - 1)
                    System.arraycopy(groups, i + 1, groups, i, size - i - 1);
                groups[size - 1] = null;
                size--;
                counter++;
            }
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
        for(int i = 0; i < size; i++){
            if(o.equals(groups[i]))
                return true;
        }

        return false;
    }

    @Override
    public Iterator<EmployeeGroup> iterator() {
        humanResources.ListIterator<EmployeeGroup> iterator = new humanResources.ListIterator<>(getEmployeesGroups());
        return iterator.iterator();
    }

    @Override
    public Object[] toArray() {
        return groups;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(a.length < size)
            a = (T[]) new EmployeeGroup[size];

        System.arraycopy(groups, 0, a, 0, size);

        return null;
    }

    @Override
    public boolean add(EmployeeGroup group) {
        try {
            this.addGroup(group);
        } catch (AlreadyAddedException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (groups[i].equals(o)) {
                if (i < groups.length - 1)
                    System.arraycopy(groups, i + 1, groups, i, size - i - 1);
                groups[size - 1] = null;
                size--;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        EmployeeGroup[] employeesCollection = (EmployeeGroup[]) c.toArray();
        int counter = 0;

        for (EmployeeGroup anEmployeesCollection : employeesCollection) {
            for (EmployeeGroup employeeGroup : groups) {
                if (anEmployeesCollection.equals(employeeGroup))
                    counter++;
            }
        }

        return counter == employeesCollection.length;
    }

    @Override
    public boolean addAll(Collection<? extends EmployeeGroup> c) {
        EmployeeGroup[] employeesCollection = (EmployeeGroup[]) c.toArray();
        for (EmployeeGroup anEmployeesCollection : employeesCollection) {
            try {
                this.addGroup(anEmployeesCollection);
            } catch (AlreadyAddedException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends EmployeeGroup> c) {
        EmployeeGroup[] employeesCollection = (EmployeeGroup[]) c.toArray();

        if (size == this.groups.length) {
            EmployeeGroup[] employeeGroups = new EmployeeGroup[this.groups.length * 2];
            System.arraycopy(this.groups,0,employeeGroups,0,size);
            this.groups = employeeGroups;

        }

        EmployeeGroup[] groupsHelper = new EmployeeGroup[groups.length];

        if(index > -1 && index < groups.length){
            System.arraycopy(this.groups, 0, groupsHelper, 0, index - 1);
            System.arraycopy(employeesCollection, 0, groupsHelper, index, employeesCollection.length);
            System.arraycopy(this.groups, index, groupsHelper, index + employeesCollection.length, size - index);
            this.groups = groupsHelper;
            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        EmployeeGroup[] employeesCollection = (EmployeeGroup[]) c.toArray();
        int counter = 0;

        for (EmployeeGroup anEmployeesCollection : employeesCollection) {
            if (remove(anEmployeesCollection))
                counter++;
        }

        return counter > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        EmployeeGroup[] retainEmployees = (EmployeeGroup[]) c.toArray();
        EmployeeGroup[] currentEmployees = groups;
        EmployeeGroup[] newGroups = new EmployeeGroup[groups.length];
        int counter = 0;

        for(int i = 0; i < currentEmployees.length; i++){
            for(int j = 0; j < retainEmployees.length; j++){
                if(currentEmployees[i].equals(retainEmployees[i])){
                    newGroups[counter] = retainEmployees[j];
                    counter++;
                }
            }
        }

        groups = newGroups;

        return counter <= 0;
    }

    @Override
    public void clear() {
        int employeeSize = groups.length;
        groups = new EmployeeGroup[employeeSize];
        size = 0;
    }

    @Override
    public EmployeeGroup get(int index) {
        if(index > -1 && index < groups.length)
            return groups[index];
        return null;
    }

    @Override
    public EmployeeGroup set(int index, EmployeeGroup element) {
        if(index > -1 && index < groups.length)
            return groups[index] = element;
        return null;
    }

    @Override
    public void add(int index, EmployeeGroup element) {
        if(element == null)
            return;

        if (size == this.groups.length) {
            EmployeeGroup[] employeeGroups = new EmployeeGroup[this.groups.length * 2];
            System.arraycopy(this.groups,0,employeeGroups,0,size);
            this.groups = employeeGroups;

        }

        EmployeeGroup[] employeeGroupsHelper = new EmployeeGroup[groups.length];

        if(index > -1 && index < groups.length){
            System.arraycopy(this.groups, 0, employeeGroupsHelper, 0, index - 1);
            employeeGroupsHelper[index] = element;
            System.arraycopy(this.groups, index, employeeGroupsHelper, index + 1, size - index);
            this.groups = employeeGroupsHelper;
        }
    }

    @Override
    public EmployeeGroup remove(int index) {
        if (index < groups.length - 1) {
            System.arraycopy(groups, index + 1, groups, index, size - index - 1);
            groups[size - 1] = null;
            size--;
            return groups[index];
        }

        return null;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(groups[i].equals(o))
                return i;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size - 1; i > -1; i--){
            if(groups[i].equals(o))
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