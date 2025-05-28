package dao;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisDAO {

    public static JFreeChart createRoomStatusChart(int available, int occupied) {
        // Create data for bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(available, "Room", "Available");
        dataset.addValue(occupied, "Room", "Occupied");

        // Create bar chart
        return ChartFactory.createBarChart(
                "Room Status",          // Chart title
                "Status Type",          // X-axis label
                "Quantity",             // Y-axis label
                dataset                 // Chart data
        );
    }

    public static JFreeChart createGenderDistributionChart(int male, int female) {
        // Create data for bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(male, "Student", "Male");
        dataset.addValue(female, "Student", "Female");

        // Create bar chart
        return ChartFactory.createBarChart(
                "Student Gender Distribution",  // Chart title
                "Gender",                       // X-axis label
                "Quantity",                     // Y-axis label
                dataset                         // Chart data
        );
    }

    public static JFreeChart createCapacityChart(int totalCapacity, int totalOccupancy) {
        // Create data for bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(totalCapacity - totalOccupancy, "Capacity", "Vacant");
        dataset.addValue(totalOccupancy, "Capacity", "Occupied");

        // Create bar chart
        return ChartFactory.createBarChart(
                "Room Capacity",        // Chart title
                "Status Type",          // X-axis label
                "Quantity",             // Y-axis label
                dataset                 // Chart data
        );
    }
}
