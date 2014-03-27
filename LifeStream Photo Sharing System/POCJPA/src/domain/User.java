package domain;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    private int userId;
 
    @Column(name="USER_NAME")
    private String userName;
 
//    @OneToMany(mappedBy="user")
//    private Set<Image> images;
//    
    public User() {}
 
    public int getUserId() {
        return userId;
    }
    
    public String getUserName() {
		return userName;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



}