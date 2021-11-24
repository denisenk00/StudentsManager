package com.denysenko.odz.dao;

import com.denysenko.odz.HibernateSessionFactoryUtil;
import com.denysenko.odz.models.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentsDao implements Dao<Student> {
    @Override
    public void create(Student student) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(student);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Student student) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(student);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Student student) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(student);
        tx.commit();
        session.close();
    }

    @Override
    public Student get(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Student.class, id);
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = (List<Student>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Student").list();
        return students;
    }
}
