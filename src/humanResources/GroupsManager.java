package humanResources;

import java.time.LocalDate;
import java.util.List;

public interface GroupsManager extends List<EmployeeGroup>{
    int employeesQuantity();
    int groupsQuantity();
    void addGroup(EmployeeGroup groupable) throws AlreadyAddedException;
    EmployeeGroup getEmployeeGroup(String name);
    EmployeeGroup[] getEmployeesGroups();
    int employeesQuantity(JobTitlesEnum jobTitle);
    EmployeeGroup getEmployeesGroup(String firstName, String secondName);
    Employee mostValuableEmployee();
    boolean removeGroup(String groupName);
    int removeGroup(EmployeeGroup group);

    int getPartTimeEmployeesQuantity();
    int getStaffEmployeesQuantity();
    int getCurrentTravellersQuantity();
    Employee[] getCurrentTravellers(LocalDate beginTravelMark, LocalDate endTravelMark);
}
