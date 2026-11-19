package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Resturant {
    private String resturantName;
    private String resturantId;
    private HashSet<String> serviceAreas;
    private FoodItem footItem;
    private double totalRating;
    private int ratingCount;
    private final List<String> ratingComments;

    public Resturant(String resturantName, String resturantId, HashSet<String> serviceAreas, FoodItem footItem) {
        this.resturantName = resturantName;
        this.resturantId = resturantId;
        this.serviceAreas = serviceAreas == null ? new HashSet<>() : serviceAreas;
        this.footItem = footItem;
        this.ratingComments = new ArrayList<>();
    }

    public Resturant(String resturantName, String[] serviceAreas, String foodName, double price, int quantity) {
        this(resturantName, String.valueOf(System.currentTimeMillis()), convertToHashSet(serviceAreas), new FoodItem(foodName, price, quantity));
    }

    private static HashSet<String> convertToHashSet(String[] areas) {
        HashSet<String> set = new HashSet<>();
        if (areas == null) {
            return set;
        }
        for (String area : areas) {
            if (area != null && !area.trim().isEmpty()) {
                set.add(area.trim());
            }
        }
        return set;
    }

    public String getResturantName() {
        return resturantName;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public String getResturantId() {
        return resturantId;
    }

    public void setResturantId(String resturantId) {
        this.resturantId = resturantId;
    }

    public HashSet<String> getServiceAreas() {
        return serviceAreas;
    }

    public void setServiceAreas(HashSet<String> serviceAreas) {
        this.serviceAreas = serviceAreas == null ? new HashSet<>() : serviceAreas;
    }

    public void addServiceArea(String area) {
        if (area != null && !area.trim().isEmpty()) {
            serviceAreas.add(area.trim());
        }
    }

    public FoodItem getFootItem() {
        return footItem;
    }

    public void setFootItem(FoodItem footItem) {
        this.footItem = footItem;
    }

    public boolean servesArea(String pincode) {
        return serviceAreas != null && serviceAreas.contains(pincode);
    }

    public double getAverageRating() {
        if (ratingCount == 0) {
            return 0.0;
        }
        return totalRating / ratingCount;
    }

    public void addRating(int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        totalRating += rating;
        ratingCount++;
        if (comment != null && !comment.trim().isEmpty()) {
            ratingComments.add(comment.trim());
        }
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public List<String> getRatingComments() {
        return ratingComments;
    }
}
