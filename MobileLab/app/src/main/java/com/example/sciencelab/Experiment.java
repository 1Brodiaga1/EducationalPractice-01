package com.example.sciencelab;

public class Experiment {
    private int id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String status; // "В процессе", "Завершен", "Планируется", "Приостановлен"
    private String researcher;

    // Конструктор и геттеры/сеттеры
    public Experiment(int id, String title, String description, String startDate, String endDate, String status, String researcher) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.researcher = researcher;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public String getResearcher() { return researcher; }
}
