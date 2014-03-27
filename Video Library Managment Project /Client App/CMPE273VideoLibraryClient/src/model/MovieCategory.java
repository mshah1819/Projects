/**
 * MovieCategory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;

public class MovieCategory  implements java.io.Serializable {
    private java.lang.String movieCategory;

    private int movieCategoryId;

    public MovieCategory() {
    }

    public MovieCategory(
           java.lang.String movieCategory,
           int movieCategoryId) {
           this.movieCategory = movieCategory;
           this.movieCategoryId = movieCategoryId;
    }


    /**
     * Gets the movieCategory value for this MovieCategory.
     * 
     * @return movieCategory
     */
    public java.lang.String getMovieCategory() {
        return movieCategory;
    }


    /**
     * Sets the movieCategory value for this MovieCategory.
     * 
     * @param movieCategory
     */
    public void setMovieCategory(java.lang.String movieCategory) {
        this.movieCategory = movieCategory;
    }


    /**
     * Gets the movieCategoryId value for this MovieCategory.
     * 
     * @return movieCategoryId
     */
    public int getMovieCategoryId() {
        return movieCategoryId;
    }


    /**
     * Sets the movieCategoryId value for this MovieCategory.
     * 
     * @param movieCategoryId
     */
    public void setMovieCategoryId(int movieCategoryId) {
        this.movieCategoryId = movieCategoryId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MovieCategory)) return false;
        MovieCategory other = (MovieCategory) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.movieCategory==null && other.getMovieCategory()==null) || 
             (this.movieCategory!=null &&
              this.movieCategory.equals(other.getMovieCategory()))) &&
            this.movieCategoryId == other.getMovieCategoryId();
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
        if (getMovieCategory() != null) {
            _hashCode += getMovieCategory().hashCode();
        }
        _hashCode += getMovieCategoryId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MovieCategory.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model", "MovieCategory"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieCategoryId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieCategoryId"));
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
