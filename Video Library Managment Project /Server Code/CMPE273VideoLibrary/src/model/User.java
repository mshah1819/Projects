package model;

public class User {

	private int id;
	private String salutation;
	private String firstName;
	private String middleName;
	private String lastName;
	private String displayName;
	private String dob;
	private String sex;
	private int addressId;
	private Address address;
	private String loginId;
	private String password;
	private String lastPwdUpdate;
	private int statusId;
	private Status status;
	private int preferenceId;
	private Preference preference;
	private String emailId;
	private int invalidLogins;
	private String lastLogin;
	private String subscriptionRenewal;
	private String ssnId;
	private float subscriptionFee;
	private String errorMsg;
	private String membershipId;
	private Payment payment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastPwdUpdate() {
		return lastPwdUpdate;
	}

	public void setLastPwdUpdate(String lastPwdUpdate) {
		this.lastPwdUpdate = lastPwdUpdate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Preference getPreference() {
		return preference;
	}

	public void setPreference(Preference preference) {
		this.preference = preference;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getInvalidLogins() {
		return invalidLogins;
	}

	public void setInvalidLogins(int invalidLogins) {
		this.invalidLogins = invalidLogins;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getSubscriptionRenewal() {
		return subscriptionRenewal;
	}

	public void setSubscriptionRenewal(String subscriptionRenewal) {
		this.subscriptionRenewal = subscriptionRenewal;
	}

	public String getSsnId() {
		return ssnId;
	}

	public void setSsnId(String ssnId) {
		this.ssnId = ssnId;
	}

	public float getSubscriptionFee() {
		return subscriptionFee;
	}

	public void setSubscriptionFee(float subscriptionFee) {
		this.subscriptionFee = subscriptionFee;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(int preferenceId) {
		this.preferenceId = preferenceId;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

}
