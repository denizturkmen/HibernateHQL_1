package com.denizturkmen.Client;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.denizturkmen.Entity.Employee;
import com.denizturkmen.Util.HibernateUtil;

public class HQLTest {

	public static void main(String[] args) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// getAllEmployees(sessionFactory);
		// getAllEmployeeById(sessionFactory);
		// getAllEmployeeByIdAndEmail(sessionFactory);
		getAllEmployeeByIdAndNames(sessionFactory);
	}

	private static void getAllEmployeeByIdAndNames(SessionFactory sessionFactory) {
		try (Session session = sessionFactory.openSession()) {

			String HQL = "SELECT employeeId,employeeName FROM Employee";
			Query query = session.createQuery(HQL);

			List<Object[]> list = query.list();
			for (Object[] objects : list) {
				Integer employeeId = (Integer) objects[0];
				String employeeName = (String) objects[1];
				System.out.println(employeeId + "\t" + employeeName);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

	private static void getAllEmployeeByIdAndEmail(SessionFactory sessionFactory) {

		int empId = 2;
		String emailAdress = "eren@gmail.com";
		try (Session session = sessionFactory.openSession()) {

			// birinci alan class field ikinci paramtre olarak gönderdiğimiz yer
			String HQL = "FROM Employee WHERE employeeId=:empId AND email=:emailAdress";
			Query<Employee> query = session.createQuery(HQL, Employee.class);
			query.setParameter("emailAdress", emailAdress);
			query.setParameter("empId", empId);

			Employee employee = query.uniqueResult();
			System.out.println(employee);

		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

	private static void getAllEmployeeById(SessionFactory sessionFactory) {
		int empId = 2;

		try (Session session = sessionFactory.openSession()) {
			// employee classındaki field ismi
			// ? yerine direk parametrede verrebilirsin employeeId=1
			String HQL = "From Employee WHERE employeeId=?";

			Query<Employee> employeeQuery = session.createQuery(HQL, Employee.class);
			employeeQuery.setParameter(0, empId);

			// uniquresult sorguyla eşlesen sonuçları döndürür
			// bir tane varsa getsinleresult
			Employee employee = employeeQuery.getSingleResult();
			System.out.println(employee);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void getAllEmployees(SessionFactory sessionFactory) {

		try (Session session = sessionFactory.openSession()) {
			String HQL = "From Employee";

			Query<Employee> employeeQuery = session.createQuery(HQL, Employee.class);
			List<Employee> list = employeeQuery.list();
			list.forEach(System.out::println);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
