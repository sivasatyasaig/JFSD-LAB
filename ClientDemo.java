package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();

        // Insert Records
        Transaction transaction = session.beginTransaction();
        Project p1 = new Project("Project A", 12, 100000, "John Doe");
        Project p2 = new Project("Project B", 8, 75000, "Jane Smith");
        Project p3 = new Project("Project C", 18, 150000, "David Lee");
        session.save(p1);
        session.save(p2);
        session.save(p3);
        transaction.commit();

        // Aggregate Queries
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Project.class);

        // Count
        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        System.out.println("Count: " + count);

        // Max
        criteria.setProjection(Projections.max("budget"));
        Double maxBudget = (Double) criteria.uniqueResult();
        System.out.println("Max Budget: " + maxBudget);

        // Min
        criteria.setProjection(Projections.min("budget"));
        Double minBudget = (Double) criteria.uniqueResult();
        System.out.println("Min Budget: " + minBudget);

        // Sum
        criteria.setProjection(Projections.sum("budget"));
        Double sumBudget = (Double) criteria.uniqueResult();
        System.out.println("Sum of Budgets: " + sumBudget);

        // Average
        criteria.setProjection(Projections.avg("budget"));
        Double avgBudget = (Double) criteria.uniqueResult();
        System.out.println("Average Budget: " + avgBudget);

        session.getTransaction().commit();
        session.close();
        factory.close();
    }
}
