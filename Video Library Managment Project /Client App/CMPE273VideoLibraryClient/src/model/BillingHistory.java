/**
 * BillingHistory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;

public class BillingHistory  implements java.io.Serializable {
    private java.lang.String movieAddedOn;

    private float movieAmountPaid;

    private java.lang.String movieName;

    private java.lang.String movieState;

    public BillingHistory() {
    }

    public BillingHistory(
           java.lang.String movieAddedOn,
           float movieAmountPaid,
           java.lang.String movieName,
           java.lang.String movieState) {
           this.movieAddedOn = movieAddedOn;
           this.movieAmountPaid = movieAmountPaid;
           this.movieName = movieName;
           this.movieState = movieState;
    }


    /**
     * Gets the movieAddedOn value for this BillingHistory.
     * 
     * @return movieAddedOn
     */
    public java.lang.String getMovieAddedOn() {
        return movieAddedOn;
    }


    /**
     * Sets the movieAddedOn value for this BillingHistory.
     * 
     * @param movieAddedOn
     */
    public void setMovieAddedOn(java.lang.String movieAddedOn) {
        this.movieAddedOn = movieAddedOn;
    }


    /**
     * Gets the movieAmountPaid value for this BillingHistory.
     * 
     * @return movieAmountPaid
     */
    public float getMovieAmountPaid() {
        return movieAmountPaid;
    }


    /**
     * Sets the movieAmountPaid value for this BillingHistory.
     * 
     * @param movieAmountPaid
     */
    public void setMovieAmountPaid(float movieAmountPaid) {
        this.movieAmountPaid = movieAmountPaid;
    }


    /**
     * Gets the movieName value for this BillingHistory.
     * 
     * @return movieName
     */
    public java.lang.String getMovieName() {
        return movieName;
    }


    /**
     * Sets the movieName value for this BillingHistory.
     * 
     * @param movieName
     */
    public void setMovieName(java.lang.String movieName) {
        this.movieName = movieName;
    }


    /**
     * Gets the movieState value for this BillingHistory.
     * 
     * @return movieState
     */
    public java.lang.String getMovieState() {
        return movieState;
    }


    /**
     * Sets the movieState value for this BillingHistory.
     * 
     * @param movieState
     */
    public void setMovieState(java.lang.String movieState) {
        this.movieState = movieState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BillingHistory)) return false;
        BillingHistory other = (BillingHistory) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.movieAddedOn==null && other.getMovieAddedOn()==null) || 
             (this.movieAddedOn!=null &&
              this.movieAddedOn.equals(other.getMovieAddedOn()))) &&
            this.movieAmountPaid == other.getMovieAmountPaid() &&
            ((this.movieName==null && other.getMovieName()==null) || 
             (this.movieName!=null &&
              this.movieName.equals(other.getMovieName()))) &&
            ((this.movieState==null && other.getMovieState()==null) || 
             (this.movieState!=null &&
              this.movieState.equals(other.getMovieState())));
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
        if (getMovieAddedOn() != null) {
            _hashCode += getMovieAddedOn().hashCode();
        }
        _hashCode += new Float(getMovieAmountPaid()).hashCode();
        if (getMovieName() != null) {
            _hashCode += getMovieName().hashCode();
        }
        if (getMovieState() != null) {
            _hashCode += getMovieState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BillingHistory.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model", "BillingHistory"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieAddedOn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieAddedOn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieAmountPaid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieAmountPaid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieState"));
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
