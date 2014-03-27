package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import domain.Image;
import domain.User;
import util.HibernateUtil;

public class JPAManager {
	
	static String configPath;
	
	public JPAManager(String configPath){
		this.configPath = configPath;
	}
	
	public void addUser(String userName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User ui = new User();
		ui.setUserName(userName);
		session.save(ui);
		session.getTransaction().commit();
		System.out.println("User added::" + userName);
	}

	public void addImage(User user, byte[] userImage) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Image image = new Image();
		image.setImage(userImage);
		image.setUser(user);
		session.save(image);
		session.getTransaction().commit();
		System.out.println("Image added for::" + user.getUserId());
	}

	public void deleteUser(int userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "DELETE FROM User " + "WHERE userId = :userId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		session.getTransaction().commit();

	}
	
	
	public void deleteImage(int imageId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "DELETE FROM Image " + "WHERE imageId = :imageId";
		Query query = session.createQuery(hql);
		query.setParameter("imageId", imageId);
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		session.getTransaction().commit();

	}
	
	public void deleteImage(User user) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "DELETE FROM Image " + "WHERE user = :user";
		Query query = session.createQuery(hql);
		query.setParameter("user", user);
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		session.getTransaction().commit();
	}
	

	public User getUser(int userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from User where userId=:uid");
		q.setInteger("uid", userId);

		Object queryResult = q.uniqueResult();
		User user = (User) queryResult;
		session.getTransaction().commit();
		return user;
	}

	public ArrayList<Image> getImage(User user) {
		ArrayList<Image> arrImage = new ArrayList<Image>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from Image where user=:user");
		q.setParameter("user", user);

		List queryResult = q.list();
		Iterator itr = queryResult.iterator();
		while (itr.hasNext()) {
			Image image = (Image) itr.next();
			arrImage.add(image);
		}
		session.getTransaction().commit();
		return arrImage;

	}
}