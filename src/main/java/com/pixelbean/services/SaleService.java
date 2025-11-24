package com.pixelbean.services;

import com.pixelbean.models.Sale;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Stub service for sales management with in-memory storage
 */
public class SaleService {
    private static SaleService instance;
    private final Map<Long, Sale> sales;
    private Long nextId;
    
    private SaleService() {
        this.sales = new HashMap<>();
        this.nextId = 1L;
    }
    
    public static synchronized SaleService getInstance() {
        if (instance == null) {
            instance = new SaleService();
        }
        return instance;
    }
    
    public List<Sale> findAll() {
        return new ArrayList<>(sales.values());
    }
    
    public Sale findById(Long id) {
        return sales.get(id);
    }
    
    public Sale save(Sale sale) {
        if (sale.getId() == null) {
            sale.setId(nextId++);
        }
        sale.calculateTotal();
        sales.put(sale.getId(), sale);
        return sale;
    }
    
    public void delete(Long id) {
        sales.remove(id);
    }
    
    public List<Sale> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return sales.values().stream()
                .filter(s -> !s.getDate().isBefore(start) && !s.getDate().isAfter(end))
                .sorted(Comparator.comparing(Sale::getDate).reversed())
                .collect(Collectors.toList());
    }
    
    public List<Sale> findToday() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        return findByDateRange(startOfDay, endOfDay);
    }
    
    public List<Sale> findYesterday() {
        LocalDateTime startOfYesterday = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endOfYesterday = startOfYesterday.plusDays(1).minusSeconds(1);
        return findByDateRange(startOfYesterday, endOfYesterday);
    }
    
    public List<Sale> findThisWeek() {
        LocalDateTime startOfWeek = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        return findByDateRange(startOfWeek, now);
    }
    
    public List<Sale> findThisMonth() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        return findByDateRange(startOfMonth, now);
    }
    
    public double calculateTotal(List<Sale> salesList) {
        return salesList.stream()
                .mapToDouble(Sale::getTotal)
                .sum();
    }
}
