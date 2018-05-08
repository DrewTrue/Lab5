package humanResources;

import java.time.*;
import java.util.*;
import java.util.ListIterator;

public class Department implements EmployeeGroup{
    private String name;
    private int size;
    private Employee[] employees;

    private final static int DEFAULT_SIZE = 8;

    public Department(String name) {
        this(name, DEFAULT_SIZE);
    }

    public Department(String name, int size){
        //this(name, new Employee[size]);
        if(size < 0)
            throw new NegativeSizeException();
        this.size = size;
        this.name = name;
        this.employees = new Employee[size];
    }

    public Department(String name, Employee[] employees) {
        this.size = employees.length;
        this.name = name;
        this.employees = employees;
    }

    @Override
    public int getPartTimeEmployeesQuantity(){
        int quantity = 0;
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
        for (Employee employee: employees) {
            if(employee instanceof StaffEmployee){
                quantity++;
            }
        }
        return quantity;
    }

    @Override
    public int getCurrentTravellersQuantity(){
        int quantity = 0;
        for(int i = 0; i < size; i++){
            if(((StaffEmployee)employees[i]).isTravelNow())
               quantity++;
        }
        return quantity;
    }

    @Override
    public Employee[] getCurrentTravellers(LocalDate beginTravelMark, LocalDate endTravelMark){
        Employee[] employees = new Employee[getStaffEmployeesQuantity()];
        int counter = 0;
        for(int i = 0; i < size; i++){
            if(((StaffEmployee)this.employees[i]).getTravelDaysFromTimeLapse(beginTravelMark, endTravelMark) > 0)
                employees[counter] = this.employees[i];
        }
        return employees;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    public int getSize(){
        return size;
    }

    @Override
    public Employee[] getEmployees() {
        Employee[] employees = new Employee[size];
        System.arraycopy(this.employees,0,employees,0,size);
        return employees;
    }

    public Employee[] getEmployees(String jobTitle) {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i] != null && employees[i].getJobTitle().equals(jobTitle))
                counter++;
        }
        Employee[] employee = new Employee[counter];
        counter = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i] != null && employees[i].getJobTitle().equals(jobTitle)) {
                //System.arraycopy(employees, i, employee, counter, 1);
                employee[counter] = employees[i];
                counter++;
            }
        }
        return employee;
    }

    @Override
    public Employee getEmployee(String firstName, String secondName){
        for(int i = 0; i < size; i++){
            if(employees[i].getFirstName().equals(firstName) && employees[i].getSecondName().equals(secondName))
                return employees[i];
        }
        return null;
    }

    @Override
    public Employee mostValuableEmployee(){
        return getEmployeesSortedBySalary()[0];
    }

    public Employee[] businessTravellers(){
        int counter = 0;

        StaffEmployee[] employees = new StaffEmployee[size];
        System.arraycopy(this.employees, 0, employees,0, size);

        for(int i = 0; i < size; i++){
            if(employees[i].getTravelsQuantity() != 0)
                counter++;
        }
        Employee[] employee = new Employee[counter];
        counter = 0;
        for(int i = 0; i < size; i++){
            if(employees[i].getTravelsQuantity() != 0) {
                //System.arraycopy(employees, i, employee, counter, 1);
                employee[counter] = employees[i];
                counter++;
            }
        }
        return employee;
    }

    public JobTitlesEnum[] jobTitles(){
        JobTitlesEnum[] jobTitlesEnum = new JobTitlesEnum[JobTitlesEnum.values().length];
        int counter = 0;
        for(int i = 0; i < JobTitlesEnum.values().length; i++){
            for(int j = 0; j < size; j++){
                if(employees[j].getJobTitle().equals(JobTitlesEnum.values()[i])) {
                    jobTitlesEnum[counter] = JobTitlesEnum.values()[i];
                    counter++;
                    break;
                }
            }
        }
        return jobTitlesEnum;
    }

    @Override
    public boolean removeEmployee(Employee employee) {
        for (int i = 0; i < size; i++) {
            if (employees[i].equals(employee)) {
                if (i < employees.length - 1)
                    System.arraycopy(employees, i + 1, employees, i, size - i - 1);
                employees[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    public int removeAll(JobTitlesEnum jobTitle){
        int counter = 0;
        Employee[] employees = new Employee[this.employees.length];
        for (int i = 0; i < size; i++) {
            if(!this.employees[i].getJobTitle().equals(jobTitle)){
                //System.arraycopy(employees, i, employee, counter, 1);
                employees[counter] = this.employees[i];
                counter++;
            }
        }
        size = counter;
        this.employees = employees;
        return size - counter;
    }

    public Employee[] getEmployees(JobTitlesEnum jobTitle) {
        int counter = 0;
        for(int i = 0; i < size; i++){
            if(employees[i].getJobTitle().equals(jobTitle))
                counter++;
        }
        Employee[] employee = new Employee[counter];
        counter = 0;
        for(int i = 0; i < size; i++){
            if(employees[i].getJobTitle().equals(jobTitle)) {
                System.arraycopy(employees, i, employee, counter,1);
                counter++;
            }
        }
        return employee;
    }

    @Override
    public int employeeQuantity() {
        return size;
    }

    @Override
    public Employee[] getEmployeesSortedBySalary() {
        Employee[] employees = this.employees;
        if(size > 1) {
            //quickSort(employees, 0, size - 1);

            Arrays.sort(employees, ((Comparator<Employee>) Employee::compareTo).reversed());

            /*Arrays.sort(employees, ((Comparator<Employee>) (o1, o2) -> {
                return o1.compareTo(o2);
            }).reversed());*/

            /*Arrays.sort(employees, new Comparator<Employee>() {
                @Override
                public int compare(Employee o1, Employee o2) {
                    return o1.compareTo(o2);
                }
            }.reversed());*/

            return employees;
        }
        return employees;
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
    public boolean removeEmployee(String firstName, String secondName) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getFirstName().equals(firstName) && employees[i].getSecondName().equals(secondName)) {
                if (i < employees.length - 1)
                    System.arraycopy(employees, i + 1, employees, i, size - i - 1);
                employees[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void addEmployee(Employee employee) throws AlreadyAddedException{
        Employee[] employeesHelper = getEmployees();
        for (Employee anEmployeesHelper : employeesHelper) {
            if (employee.equals(anEmployeesHelper)) {
                throw new AlreadyAddedException();
            }
        }
        if(employee == null)
            return;
        if (size == this.employees.length) {
            StaffEmployee[] staffEmployees = new StaffEmployee[this.employees.length * 2];
            System.arraycopy(this.employees,0,staffEmployees,0,size);
            this.employees = staffEmployees;
        }
        for (int i = 0; i < employees.length; i++) {
            if (this.employees[i] == null) {
                this.employees[i] = employee;
                size++;
                break;
            }
        }
    }

    @Override
    public Employee get(int index) {
        if(index > -1 && index < employees.length)
            return employees[index];
        return null;
    }

    @Override
    public Employee set(int index, Employee element) {
        if(index > -1 && index < employees.length)
            return employees[index] = element;
        return null;
    }

    @Override
    public void add(int index, Employee element) {
        if(element == null)
            return;

        if (size == this.employees.length) {
            StaffEmployee[] staffEmployees = new StaffEmployee[this.employees.length * 2];
            System.arraycopy(this.employees,0,staffEmployees,0,size);
            this.employees = staffEmployees;

        }

        Employee[] employeesHelper = new Employee[employees.length];

        if(index > -1 && index < employees.length){
            System.arraycopy(this.employees, 0, employeesHelper, 0, index - 1);
            employeesHelper[index] = element;
            System.arraycopy(this.employees, index, employeesHelper, index + 1, size - index);
            this.employees = employeesHelper;
        }
    }

    @Override
    public Employee remove(int index) {
        if (index < employees.length - 1) {
            System.arraycopy(employees, index + 1, employees, index, size - index - 1);
            employees[size - 1] = null;
            size--;
            return employees[index];
        }

        return null;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(employees[i].equals(o))
                return i;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size - 1; i > -1; i--){
            if(employees[i].equals(o))
                return i;
        }

        return -1;
    }

    @Override
    public ListIterator<Employee> listIterator() {
        humanResources.ListIterator<Employee> iterator = new humanResources.ListIterator<>(getEmployees());
        return (ListIterator<Employee>) iterator.iterator();
    }

    @Override
    public ListIterator<Employee> listIterator(int index) {
        humanResources.ListIterator<Employee> iterator = new humanResources.ListIterator<>(getEmployees(), index);
        return (ListIterator<Employee>) iterator.iterator();
    }

    @Override
    public List<Employee> subList(int fromIndex, int toIndex) {
        if(fromIndex <= toIndex && fromIndex >= 0 && toIndex <= size) {
            LinkedList<Employee> list = new LinkedList<>();
            for (int i = fromIndex; i <= toIndex; i++) {
                list.addNodeList(employees[i]);
            }
            return (List<Employee>) list;
        }

        return null;
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
            if(o.equals(employees[i]))
                return true;
        }

        return false;
    }

    @Override
    public Iterator<Employee> iterator() {
        humanResources.ListIterator<Employee> iterator = new humanResources.ListIterator<>(getEmployees());
        return iterator.iterator();
    }

    @Override
    public Object[] toArray() {
        return employees;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Employee employee){
        try {
            this.addEmployee(employee);
        } catch (AlreadyAddedException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getFirstName().equals(o)) {
                if (i < employees.length - 1)
                    System.arraycopy(employees, i + 1, employees, i, size - i - 1);
                employees[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Employee[] employeesCollection = (Employee[]) c.toArray();
        int counter = 0;

        for (Employee anEmployeesCollection : employeesCollection) {
            for (Employee employee : employees) {
                if (anEmployeesCollection.equals(employee))
                    counter++;
            }
        }

        return counter == employeesCollection.length;
    }

    @Override
    public boolean addAll(Collection<? extends Employee> c) {
        Employee[] employeesCollection = (Employee[]) c.toArray();
        for (Employee anEmployeesCollection : employeesCollection) {
            try {
                this.addEmployee(anEmployeesCollection);
            } catch (AlreadyAddedException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Employee> c) {
        Employee[] employeesCollection = (Employee[]) c.toArray();

        if (size == this.employees.length) {
            StaffEmployee[] staffEmployees = new StaffEmployee[this.employees.length * 2];
            System.arraycopy(this.employees,0,staffEmployees,0,size);
            this.employees = staffEmployees;

        }

        Employee[] employeesHelper = new Employee[employees.length];

        if(index > -1 && index < employees.length){
            System.arraycopy(this.employees, 0, employeesHelper, 0, index - 1);
            System.arraycopy(employeesCollection, 0, employeesHelper, index, employeesCollection.length);
            System.arraycopy(this.employees, index, employeesHelper, index + employeesCollection.length, size - index);
            this.employees = employeesHelper;
            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Employee[] employeesCollection = (Employee[]) c.toArray();
        int counter = 0;

        for (Employee anEmployeesCollection : employeesCollection) {
            if (remove(anEmployeesCollection))
                counter++;
        }

        return counter > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Employee[] retainEmployees = (Employee[]) c.toArray();
        Employee[] currentEmployees = employees;
        Employee[] newEmployees = new Employee[employees.length];
        int counter = 0;

        for(int i = 0; i < currentEmployees.length; i++){
            for(int j = 0; j < retainEmployees.length; j++){
                if(currentEmployees[i].equals(retainEmployees[i])){
                    newEmployees[counter] = retainEmployees[j];
                    counter++;
                }
            }
        }

        employees = newEmployees;

        return counter <= 0;
    }

    @Override
    public void clear() {
        int employeeSize = employees.length;
        employees = new Employee[employeeSize];
        size = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj instanceof Department && name == ((Department) obj).name && size == ((Department) obj).size) {
            Employee[] employee = getEmployees();
            Employee[] employeeObject = ((Department) obj).getEmployees();
            for(int i = 0; i < size; i++) {
                if(employee[i].equals(employeeObject[i]))
                    return true;
                }
            }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Department ").append(name).append(": ").append(size).append("\n");
        for(int i = 0; i < size; i++){
            result.append(employees[i].toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        int result = name.hashCode() ^ size;
        for(int i = 0; i < size; i++){
            result ^= employees[i].hashCode();
        }
        return result;
    }
}