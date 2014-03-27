/**
 * MovieCart.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;

public class MovieCart  implements java.io.Serializable {
    private java.lang.String addedOn;

    private java.lang.String cartStatus;

    private java.lang.String errorMsg;

    private int movieId;

    private int qty;

    private int userId;

    public MovieCart() {
    }

    public MovieCart(
           java.lang.String addedOn,
           java.lang.String cartStatus,
           java.lang.String errorMsg,
           int movieId,
           int qty,
           int userId) {
           this.addedOn = addedOn;
           this.cartStatus = cartStatus;
           this.errorMsg = errorMsg;
           this.movieId = movieId;
           this.qty = qty;
           this.userId = userId;
    }


    /**
     * Gets the addedOn value for this MovieCart.
     * 
     * @return addedOn
     */
    public java.lang.String getAddedOn() {
        return addedOn;
    }


    /**
     * Sets the addedOn value for this MovieCart.
     * 
     * @param addedOn
     */
    public void setAddedOn(java.lang.String addedOn) {
        this.addedOn = addedOn;
    }


    /**
     * Gets the cartStatus value for this MovieCart.
     * 
     * @return cartStatus
     */
    public java.lang.String getCartStatus() {
        return cartStatus;
    }


    /**
     * Sets the cartStatus value for this MovieCart.
     * 
     * @param cartStatus
     */
    public void setCartStatus(java.lang.String cartStatus) {
        this.cartStatus = cartStatus;
    }


    /**
     * Gets the errorMsg value for this MovieCart.
     * 
     * @return errorMsg
     */
    public java.lang.String getErrorMsg() {
        return errorMsg;
    }


    /**
     * Sets the errorMsg value for this MovieCart.
     * 
     * @param errorMsg
     */
    public void setErrorMsg(java.lang.String errorMsg) {
        this.errorMsg = errorMsg;
    }


    /**
     * Gets the movieId value for this MovieCart.
     * 
     * @return movieId
     */
    public int getMovieId() {
        return movieId;
    }


    /**
     * Sets the movieId value for this MovieCart.
     * 
     * @param movieId
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }


    /**
     * Gets the qty value for this MovieCart.
     * 
     * @return qty
     */
    public int getQty() {
        return qty;
    }


    /**
     * Sets the qty value for this MovieCart.
     * 
     * @param qty
     */
    public void setQty(int qty) {
        this.qty = qty;
    }


    /**
     * Gets the userId value for this MovieCart.
     * 
     * @return userId
     */
    public int getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this MovieCart.
     * 
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MovieCart)) return false;
        MovieCart other = (MovieCart) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addedOn==null && other.getAddedOn()==null) || 
             (this.addedOn!=null &&
              this.addedOn.equals(other.getAddedOn()))) &&
            ((this.cartStatus==null && other.getCartStatus()==null) || 
             (this.cartStatus!=null &&
              this.cartStatus.equals(other.getCartStatus()))) &&
            ((this.errorMsg==null && other.getErrorMsg()==null) || 
             (this.errorMsg!=null &&
              this.errorMsg.equals(other.getErrorMsg()))) &&
            this.movieId == other.getMovieId() &&
            this.qty == other.getQty() &&
            this.userId == other.getUserId();
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
        if (getAddedOn() != null) {
            _hashCode += getAddedOn().hashCode();
        }
        if (getCartStatus() != null) {
            _hashCode += getCartStatus().hashCode();
        }
        if (getErrorMsg() != null) {
            _hashCode += getErrorMsg().hashCode();
        }
        _hashCode += getMovieId();
        _hashCode += getQty();
        _hashCode += getUserId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MovieCart.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model", "MovieCart"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addedOn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "addedOn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cartStatus"));
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
        elemField.setFieldName("movieId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "qty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
