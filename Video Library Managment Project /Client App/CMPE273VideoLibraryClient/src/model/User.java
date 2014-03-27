/**
 * User.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;

public class User  implements java.io.Serializable {
    private model.Address address;

    private int addressId;

    private java.lang.String displayName;

    private java.lang.String dob;

    private java.lang.String emailId;

    private java.lang.String errorMsg;

    private java.lang.String firstName;

    private int id;

    private int invalidLogins;

    private java.lang.String lastLogin;

    private java.lang.String lastName;

    private java.lang.String lastPwdUpdate;

    private java.lang.String loginId;

    private java.lang.String membershipId;

    private java.lang.String middleName;

    private java.lang.String password;

    private model.Payment payment;

    private model.Preference preference;

    private int preferenceId;

    private java.lang.String salutation;

    private java.lang.String sex;

    private java.lang.String ssnId;

    private model.Status status;

    private int statusId;

    private float subscriptionFee;

    private java.lang.String subscriptionRenewal;

    public User() {
    }

    public User(
           model.Address address,
           int addressId,
           java.lang.String displayName,
           java.lang.String dob,
           java.lang.String emailId,
           java.lang.String errorMsg,
           java.lang.String firstName,
           int id,
           int invalidLogins,
           java.lang.String lastLogin,
           java.lang.String lastName,
           java.lang.String lastPwdUpdate,
           java.lang.String loginId,
           java.lang.String membershipId,
           java.lang.String middleName,
           java.lang.String password,
           model.Payment payment,
           model.Preference preference,
           int preferenceId,
           java.lang.String salutation,
           java.lang.String sex,
           java.lang.String ssnId,
           model.Status status,
           int statusId,
           float subscriptionFee,
           java.lang.String subscriptionRenewal) {
           this.address = address;
           this.addressId = addressId;
           this.displayName = displayName;
           this.dob = dob;
           this.emailId = emailId;
           this.errorMsg = errorMsg;
           this.firstName = firstName;
           this.id = id;
           this.invalidLogins = invalidLogins;
           this.lastLogin = lastLogin;
           this.lastName = lastName;
           this.lastPwdUpdate = lastPwdUpdate;
           this.loginId = loginId;
           this.membershipId = membershipId;
           this.middleName = middleName;
           this.password = password;
           this.payment = payment;
           this.preference = preference;
           this.preferenceId = preferenceId;
           this.salutation = salutation;
           this.sex = sex;
           this.ssnId = ssnId;
           this.status = status;
           this.statusId = statusId;
           this.subscriptionFee = subscriptionFee;
           this.subscriptionRenewal = subscriptionRenewal;
    }


    /**
     * Gets the address value for this User.
     * 
     * @return address
     */
    public model.Address getAddress() {
        return address;
    }


    /**
     * Sets the address value for this User.
     * 
     * @param address
     */
    public void setAddress(model.Address address) {
        this.address = address;
    }


    /**
     * Gets the addressId value for this User.
     * 
     * @return addressId
     */
    public int getAddressId() {
        return addressId;
    }


    /**
     * Sets the addressId value for this User.
     * 
     * @param addressId
     */
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }


    /**
     * Gets the displayName value for this User.
     * 
     * @return displayName
     */
    public java.lang.String getDisplayName() {
        return displayName;
    }


    /**
     * Sets the displayName value for this User.
     * 
     * @param displayName
     */
    public void setDisplayName(java.lang.String displayName) {
        this.displayName = displayName;
    }


    /**
     * Gets the dob value for this User.
     * 
     * @return dob
     */
    public java.lang.String getDob() {
        return dob;
    }


    /**
     * Sets the dob value for this User.
     * 
     * @param dob
     */
    public void setDob(java.lang.String dob) {
        this.dob = dob;
    }


    /**
     * Gets the emailId value for this User.
     * 
     * @return emailId
     */
    public java.lang.String getEmailId() {
        return emailId;
    }


    /**
     * Sets the emailId value for this User.
     * 
     * @param emailId
     */
    public void setEmailId(java.lang.String emailId) {
        this.emailId = emailId;
    }


    /**
     * Gets the errorMsg value for this User.
     * 
     * @return errorMsg
     */
    public java.lang.String getErrorMsg() {
        return errorMsg;
    }


    /**
     * Sets the errorMsg value for this User.
     * 
     * @param errorMsg
     */
    public void setErrorMsg(java.lang.String errorMsg) {
        this.errorMsg = errorMsg;
    }


    /**
     * Gets the firstName value for this User.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this User.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the id value for this User.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this User.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the invalidLogins value for this User.
     * 
     * @return invalidLogins
     */
    public int getInvalidLogins() {
        return invalidLogins;
    }


    /**
     * Sets the invalidLogins value for this User.
     * 
     * @param invalidLogins
     */
    public void setInvalidLogins(int invalidLogins) {
        this.invalidLogins = invalidLogins;
    }


    /**
     * Gets the lastLogin value for this User.
     * 
     * @return lastLogin
     */
    public java.lang.String getLastLogin() {
        return lastLogin;
    }


    /**
     * Sets the lastLogin value for this User.
     * 
     * @param lastLogin
     */
    public void setLastLogin(java.lang.String lastLogin) {
        this.lastLogin = lastLogin;
    }


    /**
     * Gets the lastName value for this User.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this User.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the lastPwdUpdate value for this User.
     * 
     * @return lastPwdUpdate
     */
    public java.lang.String getLastPwdUpdate() {
        return lastPwdUpdate;
    }


    /**
     * Sets the lastPwdUpdate value for this User.
     * 
     * @param lastPwdUpdate
     */
    public void setLastPwdUpdate(java.lang.String lastPwdUpdate) {
        this.lastPwdUpdate = lastPwdUpdate;
    }


    /**
     * Gets the loginId value for this User.
     * 
     * @return loginId
     */
    public java.lang.String getLoginId() {
        return loginId;
    }


    /**
     * Sets the loginId value for this User.
     * 
     * @param loginId
     */
    public void setLoginId(java.lang.String loginId) {
        this.loginId = loginId;
    }


    /**
     * Gets the membershipId value for this User.
     * 
     * @return membershipId
     */
    public java.lang.String getMembershipId() {
        return membershipId;
    }


    /**
     * Sets the membershipId value for this User.
     * 
     * @param membershipId
     */
    public void setMembershipId(java.lang.String membershipId) {
        this.membershipId = membershipId;
    }


    /**
     * Gets the middleName value for this User.
     * 
     * @return middleName
     */
    public java.lang.String getMiddleName() {
        return middleName;
    }


    /**
     * Sets the middleName value for this User.
     * 
     * @param middleName
     */
    public void setMiddleName(java.lang.String middleName) {
        this.middleName = middleName;
    }


    /**
     * Gets the password value for this User.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this User.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the payment value for this User.
     * 
     * @return payment
     */
    public model.Payment getPayment() {
        return payment;
    }


    /**
     * Sets the payment value for this User.
     * 
     * @param payment
     */
    public void setPayment(model.Payment payment) {
        this.payment = payment;
    }


    /**
     * Gets the preference value for this User.
     * 
     * @return preference
     */
    public model.Preference getPreference() {
        return preference;
    }


    /**
     * Sets the preference value for this User.
     * 
     * @param preference
     */
    public void setPreference(model.Preference preference) {
        this.preference = preference;
    }


    /**
     * Gets the preferenceId value for this User.
     * 
     * @return preferenceId
     */
    public int getPreferenceId() {
        return preferenceId;
    }


    /**
     * Sets the preferenceId value for this User.
     * 
     * @param preferenceId
     */
    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }


    /**
     * Gets the salutation value for this User.
     * 
     * @return salutation
     */
    public java.lang.String getSalutation() {
        return salutation;
    }


    /**
     * Sets the salutation value for this User.
     * 
     * @param salutation
     */
    public void setSalutation(java.lang.String salutation) {
        this.salutation = salutation;
    }


    /**
     * Gets the sex value for this User.
     * 
     * @return sex
     */
    public java.lang.String getSex() {
        return sex;
    }


    /**
     * Sets the sex value for this User.
     * 
     * @param sex
     */
    public void setSex(java.lang.String sex) {
        this.sex = sex;
    }


    /**
     * Gets the ssnId value for this User.
     * 
     * @return ssnId
     */
    public java.lang.String getSsnId() {
        return ssnId;
    }


    /**
     * Sets the ssnId value for this User.
     * 
     * @param ssnId
     */
    public void setSsnId(java.lang.String ssnId) {
        this.ssnId = ssnId;
    }


    /**
     * Gets the status value for this User.
     * 
     * @return status
     */
    public model.Status getStatus() {
        return status;
    }


    /**
     * Sets the status value for this User.
     * 
     * @param status
     */
    public void setStatus(model.Status status) {
        this.status = status;
    }


    /**
     * Gets the statusId value for this User.
     * 
     * @return statusId
     */
    public int getStatusId() {
        return statusId;
    }


    /**
     * Sets the statusId value for this User.
     * 
     * @param statusId
     */
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }


    /**
     * Gets the subscriptionFee value for this User.
     * 
     * @return subscriptionFee
     */
    public float getSubscriptionFee() {
        return subscriptionFee;
    }


    /**
     * Sets the subscriptionFee value for this User.
     * 
     * @param subscriptionFee
     */
    public void setSubscriptionFee(float subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }


    /**
     * Gets the subscriptionRenewal value for this User.
     * 
     * @return subscriptionRenewal
     */
    public java.lang.String getSubscriptionRenewal() {
        return subscriptionRenewal;
    }


    /**
     * Sets the subscriptionRenewal value for this User.
     * 
     * @param subscriptionRenewal
     */
    public void setSubscriptionRenewal(java.lang.String subscriptionRenewal) {
        this.subscriptionRenewal = subscriptionRenewal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            this.addressId == other.getAddressId() &&
            ((this.displayName==null && other.getDisplayName()==null) || 
             (this.displayName!=null &&
              this.displayName.equals(other.getDisplayName()))) &&
            ((this.dob==null && other.getDob()==null) || 
             (this.dob!=null &&
              this.dob.equals(other.getDob()))) &&
            ((this.emailId==null && other.getEmailId()==null) || 
             (this.emailId!=null &&
              this.emailId.equals(other.getEmailId()))) &&
            ((this.errorMsg==null && other.getErrorMsg()==null) || 
             (this.errorMsg!=null &&
              this.errorMsg.equals(other.getErrorMsg()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            this.id == other.getId() &&
            this.invalidLogins == other.getInvalidLogins() &&
            ((this.lastLogin==null && other.getLastLogin()==null) || 
             (this.lastLogin!=null &&
              this.lastLogin.equals(other.getLastLogin()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.lastPwdUpdate==null && other.getLastPwdUpdate()==null) || 
             (this.lastPwdUpdate!=null &&
              this.lastPwdUpdate.equals(other.getLastPwdUpdate()))) &&
            ((this.loginId==null && other.getLoginId()==null) || 
             (this.loginId!=null &&
              this.loginId.equals(other.getLoginId()))) &&
            ((this.membershipId==null && other.getMembershipId()==null) || 
             (this.membershipId!=null &&
              this.membershipId.equals(other.getMembershipId()))) &&
            ((this.middleName==null && other.getMiddleName()==null) || 
             (this.middleName!=null &&
              this.middleName.equals(other.getMiddleName()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.payment==null && other.getPayment()==null) || 
             (this.payment!=null &&
              this.payment.equals(other.getPayment()))) &&
            ((this.preference==null && other.getPreference()==null) || 
             (this.preference!=null &&
              this.preference.equals(other.getPreference()))) &&
            this.preferenceId == other.getPreferenceId() &&
            ((this.salutation==null && other.getSalutation()==null) || 
             (this.salutation!=null &&
              this.salutation.equals(other.getSalutation()))) &&
            ((this.sex==null && other.getSex()==null) || 
             (this.sex!=null &&
              this.sex.equals(other.getSex()))) &&
            ((this.ssnId==null && other.getSsnId()==null) || 
             (this.ssnId!=null &&
              this.ssnId.equals(other.getSsnId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            this.statusId == other.getStatusId() &&
            this.subscriptionFee == other.getSubscriptionFee() &&
            ((this.subscriptionRenewal==null && other.getSubscriptionRenewal()==null) || 
             (this.subscriptionRenewal!=null &&
              this.subscriptionRenewal.equals(other.getSubscriptionRenewal())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        _hashCode += getAddressId();
        if (getDisplayName() != null) {
            _hashCode += getDisplayName().hashCode();
        }
        if (getDob() != null) {
            _hashCode += getDob().hashCode();
        }
        if (getEmailId() != null) {
            _hashCode += getEmailId().hashCode();
        }
        if (getErrorMsg() != null) {
            _hashCode += getErrorMsg().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        _hashCode += getId();
        _hashCode += getInvalidLogins();
        if (getLastLogin() != null) {
            _hashCode += getLastLogin().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getLastPwdUpdate() != null) {
            _hashCode += getLastPwdUpdate().hashCode();
        }
        if (getLoginId() != null) {
            _hashCode += getLoginId().hashCode();
        }
        if (getMembershipId() != null) {
            _hashCode += getMembershipId().hashCode();
        }
        if (getMiddleName() != null) {
            _hashCode += getMiddleName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getPayment() != null) {
            _hashCode += getPayment().hashCode();
        }
        if (getPreference() != null) {
            _hashCode += getPreference().hashCode();
        }
        _hashCode += getPreferenceId();
        if (getSalutation() != null) {
            _hashCode += getSalutation().hashCode();
        }
        if (getSex() != null) {
            _hashCode += getSex().hashCode();
        }
        if (getSsnId() != null) {
            _hashCode += getSsnId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        _hashCode += getStatusId();
        _hashCode += new Float(getSubscriptionFee()).hashCode();
        if (getSubscriptionRenewal() != null) {
            _hashCode += getSubscriptionRenewal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(User.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model", "User"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model", "Address"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "addressId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("displayName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "displayName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dob");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "dob"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "emailId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "errorMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invalidLogins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "invalidLogins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastLogin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "lastLogin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastPwdUpdate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "lastPwdUpdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "loginId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("membershipId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "membershipId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("middleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "middleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "payment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model", "Payment"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "preference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model", "Preference"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferenceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "preferenceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salutation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "salutation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "sex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ssnId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "ssnId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model", "Status"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "statusId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriptionFee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "subscriptionFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriptionRenewal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "subscriptionRenewal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
