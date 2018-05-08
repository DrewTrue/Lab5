package humanResources;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class StaffEmployee extends Employee implements BusinessTraveller {
    private int bonus;
    private CircleLinkedList<BusinessTravel> businessTravelsSet;
    private int travelsQuantity;

    private static final int DEFAULT_VALUE = 0;

    public StaffEmployee(String firstName, String secondName){
        this(firstName, secondName, JobTitlesEnum.NONE, DEFAULT_VALUE, new CircleLinkedList<>());
    }

    public StaffEmployee(String firstName, String secondName, JobTitlesEnum jobTitles, int salary){
        this(firstName,secondName,jobTitles,salary, new CircleLinkedList<>());
    }

    public StaffEmployee(String firstName, String secondName, JobTitlesEnum jobTitle, int salary, CircleLinkedList<BusinessTravel> businessTravelsSet){
        super(firstName, secondName, jobTitle, salary);
        this.businessTravelsSet = businessTravelsSet;
        this.bonus = DEFAULT_VALUE;
        this.travelsQuantity = DEFAULT_VALUE;
    }

    public CircleLinkedList<BusinessTravel> getBusinessTravels() {
        return businessTravelsSet;
    }

    public void setBusinessTravels(CircleLinkedList<BusinessTravel> businessTravelsSet) {
        this.businessTravelsSet = businessTravelsSet;
    }

    @Override
    public int size() {
        return businessTravelsSet.getSize();
    }

    @Override
    public boolean isEmpty() {
        return businessTravelsSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return businessTravelsSet.contains((BusinessTravel) o);
    }

    @Override
    public Iterator<BusinessTravel> iterator() {
        ListIterator<BusinessTravel> iterator = new ListIterator<>(getTravels());
        return iterator.iterator();
    }

    @Override
    public Object[] toArray() {
        return getTravels();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(BusinessTravel travel) {
        return businessTravelsSet.addNodeSet(travel);
    }

    @Override
    public boolean remove(Object o) {
        return businessTravelsSet.removeNode((BusinessTravel) o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return businessTravelsSet.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends BusinessTravel> c) {
        return businessTravelsSet.addAllSet(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return businessTravelsSet.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return businessTravelsSet.removeAll(c);
    }

    @Override
    public void clear() {
        businessTravelsSet.clearList();
    }

    @Override
    public boolean isTravelNow(){
        BusinessTravel lastBusinessTravel = getTravels()[getTravels().length - 1];
        return LocalDate.now().isAfter(lastBusinessTravel.getBeginTravel())
                && LocalDate.now().isBefore(lastBusinessTravel.getEndTravel());
    }

    @Override
    public int getTravelDaysFromTimeLapse(LocalDate beginTravelMark, LocalDate endTravelMark){
        BusinessTravel[] businessTravels = getTravels();
        for (BusinessTravel businessTravel : businessTravels) {
            if (beginTravelMark.isAfter(businessTravel.getBeginTravel())
                    && endTravelMark.isBefore(businessTravel.getEndTravel()))
                return (int) ChronoUnit.DAYS.between(beginTravelMark, endTravelMark);

            if (beginTravelMark.isAfter(businessTravel.getBeginTravel())
                    && beginTravelMark.isBefore(businessTravel.getEndTravel())
                    && endTravelMark.isAfter(businessTravel.getEndTravel()))
                return (int) ChronoUnit.DAYS.between(beginTravelMark, businessTravel.getEndTravel());

            if (endTravelMark.isAfter(businessTravel.getBeginTravel())
                    && endTravelMark.isBefore(businessTravel.getEndTravel())
                    && beginTravelMark.isBefore(businessTravel.getBeginTravel()))
                return (int) ChronoUnit.DAYS.between(businessTravel.getBeginTravel(), endTravelMark);
        }
        return 0;
    }

    @Override
    public void addTravel(BusinessTravel travel) throws IllegalDatesException {
        BusinessTravel[] businessTravels = getTravels();
        for (BusinessTravel businessTravel : businessTravels) {
            if (travel.getBeginTravel().isAfter(businessTravel.getBeginTravel())
                    && travel.getBeginTravel().isBefore(businessTravel.getEndTravel()))
                throw new IllegalDatesException();
        }
        businessTravelsSet.addNodeSet(travel);
        travelsQuantity++;
    }

    @Override
    public BusinessTravel[] getTravels(){
        return businessTravelsSet.getTravels();
    }

    public int getTravelsQuantity(){
        return travelsQuantity;
    }

    @Override
    public int getBonus(){
        return bonus;
    }

    @Override
    public void setBonus(int bonus){
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString()).append("\n").append("Travels ").append("/n");
        BusinessTravel[] businessTravel = businessTravelsSet.getTravels();
        for (BusinessTravel aBusinessTravel : businessTravel) {
            result.append(aBusinessTravel.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        return obj instanceof StaffEmployee
                && super.equals(obj)
                && this.bonus == ((StaffEmployee) obj).bonus
                && travelsQuantity == ((StaffEmployee) obj).travelsQuantity;
    }

    @Override
    public int hashCode() {
        return travelsQuantity ^ bonus ^ businessTravelsSet.hashCode();
    }
}
