/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;

public class Address  implements java.io.Serializable {
    private java.lang.String addrLine1;

    private java.lang.String addrLine2;

    private java.lang.String addrLine3;

    private int addressId;

    private model.City city;

    private int cityId;

    private model.Country country;

    private int countryId;

    private model.State state;

    private int stateId;

    private java.lang.String telNo;

    private java.lang.String zipCode;

    public Address() {
    }

    public Address(
           java.lang.String addrLine1,
           java.lang.String addrLine2,
           java.lang.String addrLine3,
           int addressId,
           model.City city,
           int cityId,
           model.Country country,
           int countryId,
           model.State state,
           int stateId,
           java.lang.String telNo,
           java.lang.String zipCode) {
           this.addrLine1 = addrLine1;
           this.addrLine2 = addrLine2;
           this.addrLine3 = addrLine3;
           this.addressId = addressId;
           this.city = city;
           this.cityId = cityId;
           this.country = country;
           this.countryId = countryId;
           this.state = state;
           this.stateId = stateId;
           this.telNo = telNo;
           this.zipCode = zipCode;
    }


    /**
     * Gets the addrLine1 value for this Address.
     * 
     * @return addrLine1
     */
    public java.lang.String getAddrLine1() {
        return addrLine1;
    }


    /**
     * Sets the addrLine1 value for this Address.
     * 
     * @param addrLine1
     */
    public void setAddrLine1(java.lang.String addrLine1) {
        this.addrLine1 = addrLine1;
    }


    /**
     * Gets the addrLine2 value for this Address.
     * 
     * @return addrLine2
     */
    public java.lang.String getAddrLine2() {
        return addrLine2;
    }


    /**
     * Sets the addrLine2 value for this Address.
     * 
     * @param addrLine2
     */
    public void setAddrLine2(java.lang.String addrLine2) {
        this.addrLine2 = addrLine2;
    }


    /**
     * Gets the addrLine3 value for this Address.
     * 
     * @return addrLine3
     */
    public java.lang.String getAddrLine3() {
        return addrLine3;
    }


    /**
     * Sets the addrLine3 value for this Address.
     * 
     * @param addrLine3
     */
    public void setAddrLine3(java.lang.String addrLine3) {
        this.addrLine3 = addrLine3;
    }


    /**
     * Gets the addressId value for this Address.
     * 
     * @return addressId
     */
    public int getAddressId() {
        return addressId;
    }


    /**
     * Sets the addressId value for this Address.
     * 
     * @param addressId
     */
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }


    /**
     * Gets the city value for this Address.
     * 
     * @return city
     */
    public model.City getCity() {
        return city;
    }


    /**
     * Sets the city value for this Address.
     * 
     * @param city
     */
    public void setCity(model.City city) {
        this.city = city;
    }


    /**
     * Gets the cityId value for this Address.
     * 
     * @return cityId
     */
    public int getCityId() {
        return cityId;
    }


    /**
     * Sets the cityId value for this Address.
     * 
     * @param cityId
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }


    /**
     * Gets the country value for this Address.
     * 
     * @return country
     */
    public model.Country getCountry() {
        return country;
    }


    /**
     * Sets the country value for this Address.
     * 
     * @param country
     */
    public void setCountry(model.Country country) {
        this.country = country;
    }


    /**
     * Gets the countryId value for this Address.
     * 
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }


    /**
     * Sets the countryId value for this Address.
     * 
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


    /**
     * Gets the state value for this Address.
     * 
     * @return state
     */
    public model.State getState() {
        return state;
    }


    /**
     * Sets the state value for this Address.
     * 
     * @param state
     */
    public void setState(model.State state) {
        this.state = state;
    }


    /**
     * Gets the stateId value for this Address.
     * 
     * @return stateId
     */
    public int getStateId() {
        return stateId;
    }


    /**
     * Sets the stateId value for this Address.
     * 
     * @param stateId
     */
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }


    /**
     * Gets the telNo value for this Address.
     * 
     * @return telNo
     */
    public java.lang.String getTelNo() {
        return telNo;
    }


    /**
     * Sets the telNo value for this Address.
     * 
     * @param telNo
     */
    public void setTelNo(java.lang.String telNo) {
        this.telNo = telNo;
    }


    /**
     * Gets the zipCode value for this Address.
     * 
     * @return zipCode
     */
    public java.lang.String getZipCode() {
        return zipCode;
    }


    /**
     * Sets the zipCode value for this Address.
     * 
     * @param zipCode
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addrLine1==null && other.getAddrLine1()==null) || 
             (this.addrLine1!=null &&
              this.addrLine1.equals(other.getAddrLine1()))) &&
            ((this.addrLine2==null && other.getAddrLine2()==null) || 
             (this.addrLine2!=null &&
              this.addrLine2.equals(other.getAddrLine2()))) &&
            ((this.addrLine3==null && other.getAddrLine3()==null) || 
             (this.addrLine3!=null &&
              this.addrLine3.equals(other.getAddrLine3()))) &&
            this.addressId == other.getAddressId() &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            this.cityId == other.getCityId() &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            this.countryId == other.getCountryId() &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            this.stateId == other.getStateId() &&
            ((this.telNo==null && other.getTelNo()==null) || 
             (this.telNo!=null &&
              this.telNo.equals(other.getTelNo()))) &&
            ((this.zipCode==null && other.getZipCode()==null) || 
             (this.zipCode!=null &&
              this.zipCode.equals(other.getZipCode())));
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
        if (getAddrLine1() != null) {
            _hashCode += getAddrLine1().hashCode();
        }
        if (getAddrLine2() != null) {
            _hashCode += getAddrLine2().hashCode();
        }
        if (getAddrLine3() != null) {
            _hashCode += getAddrLine3().hashCode();
        }
        _hashCode += getAddressId();
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        _hashCode += getCityId();
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        _hashCode += getCountryId();
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        _hashCode += getStateId();
        if (getTelNo() != null) {
            _hashCode += getTelNo().hashCode();
        }
        if (getZipCode() != null) {
            _hashCode += getZipCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Address.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model", "Address"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addrLine1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "addrLine1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addrLine2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "addrLine2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addrLine3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "addrLine3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "addressId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "city"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model", "City"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cityId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model", "Country"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "countryId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model", "State"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "stateId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "telNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "zipCode"));
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
