package model;

import contract.Identifiable;

public class MaintenanceRecord implements Identifiable {
    private final int id;
    private final int vehicleId;
    private String serviceDate;
    private double mileage;
    private String issue;
    private boolean resolved;

    public MaintenanceRecord(int id, int vehicleId, String serviceDate, double mileage, String issue, boolean resolved) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.serviceDate = serviceDate;
        this.mileage = mileage;
        this.issue = issue;
        this.resolved = resolved;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public double getMileage() {
        return mileage;
    }

    public String getIssue() {
        return issue;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    @Override
    public boolean equals(Object o) {
        MaintenanceRecord other = (MaintenanceRecord) o;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "MaintenanceRecord{" +
                "id=" + id +
                ", vehicleId=" + vehicleId +
                ", serviceDate='" + serviceDate + '\'' +
                ", mileage=" + mileage +
                ", issue='" + issue + '\'' +
                ", resolved=" + resolved +
                '}';
    }
}
