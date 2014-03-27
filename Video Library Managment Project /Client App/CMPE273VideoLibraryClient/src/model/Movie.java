/**
 * Movie.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;

public class Movie  implements java.io.Serializable {
    private int availableCopies;

    private java.lang.String errorMsg;

    private model.MovieCategory movieCategory;

    private int movieCategoryId;

    private int movieId;

    private java.lang.String movieName;

    private java.lang.String prodBanner;

    private java.lang.String releaseDate;

    private float rentAmount;

    private int totalCopies;

    public Movie() {
    }

    public Movie(
           int availableCopies,
           java.lang.String errorMsg,
           model.MovieCategory movieCategory,
           int movieCategoryId,
           int movieId,
           java.lang.String movieName,
           java.lang.String prodBanner,
           java.lang.String releaseDate,
           float rentAmount,
           int totalCopies) {
           this.availableCopies = availableCopies;
           this.errorMsg = errorMsg;
           this.movieCategory = movieCategory;
           this.movieCategoryId = movieCategoryId;
           this.movieId = movieId;
           this.movieName = movieName;
           this.prodBanner = prodBanner;
           this.releaseDate = releaseDate;
           this.rentAmount = rentAmount;
           this.totalCopies = totalCopies;
    }


    /**
     * Gets the availableCopies value for this Movie.
     * 
     * @return availableCopies
     */
    public int getAvailableCopies() {
        return availableCopies;
    }


    /**
     * Sets the availableCopies value for this Movie.
     * 
     * @param availableCopies
     */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }


    /**
     * Gets the errorMsg value for this Movie.
     * 
     * @return errorMsg
     */
    public java.lang.String getErrorMsg() {
        return errorMsg;
    }


    /**
     * Sets the errorMsg value for this Movie.
     * 
     * @param errorMsg
     */
    public void setErrorMsg(java.lang.String errorMsg) {
        this.errorMsg = errorMsg;
    }


    /**
     * Gets the movieCategory value for this Movie.
     * 
     * @return movieCategory
     */
    public model.MovieCategory getMovieCategory() {
        return movieCategory;
    }


    /**
     * Sets the movieCategory value for this Movie.
     * 
     * @param movieCategory
     */
    public void setMovieCategory(model.MovieCategory movieCategory) {
        this.movieCategory = movieCategory;
    }


    /**
     * Gets the movieCategoryId value for this Movie.
     * 
     * @return movieCategoryId
     */
    public int getMovieCategoryId() {
        return movieCategoryId;
    }


    /**
     * Sets the movieCategoryId value for this Movie.
     * 
     * @param movieCategoryId
     */
    public void setMovieCategoryId(int movieCategoryId) {
        this.movieCategoryId = movieCategoryId;
    }


    /**
     * Gets the movieId value for this Movie.
     * 
     * @return movieId
     */
    public int getMovieId() {
        return movieId;
    }


    /**
     * Sets the movieId value for this Movie.
     * 
     * @param movieId
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }


    /**
     * Gets the movieName value for this Movie.
     * 
     * @return movieName
     */
    public java.lang.String getMovieName() {
        return movieName;
    }


    /**
     * Sets the movieName value for this Movie.
     * 
     * @param movieName
     */
    public void setMovieName(java.lang.String movieName) {
        this.movieName = movieName;
    }


    /**
     * Gets the prodBanner value for this Movie.
     * 
     * @return prodBanner
     */
    public java.lang.String getProdBanner() {
        return prodBanner;
    }


    /**
     * Sets the prodBanner value for this Movie.
     * 
     * @param prodBanner
     */
    public void setProdBanner(java.lang.String prodBanner) {
        this.prodBanner = prodBanner;
    }


    /**
     * Gets the releaseDate value for this Movie.
     * 
     * @return releaseDate
     */
    public java.lang.String getReleaseDate() {
        return releaseDate;
    }


    /**
     * Sets the releaseDate value for this Movie.
     * 
     * @param releaseDate
     */
    public void setReleaseDate(java.lang.String releaseDate) {
        this.releaseDate = releaseDate;
    }


    /**
     * Gets the rentAmount value for this Movie.
     * 
     * @return rentAmount
     */
    public float getRentAmount() {
        return rentAmount;
    }


    /**
     * Sets the rentAmount value for this Movie.
     * 
     * @param rentAmount
     */
    public void setRentAmount(float rentAmount) {
        this.rentAmount = rentAmount;
    }


    /**
     * Gets the totalCopies value for this Movie.
     * 
     * @return totalCopies
     */
    public int getTotalCopies() {
        return totalCopies;
    }


    /**
     * Sets the totalCopies value for this Movie.
     * 
     * @param totalCopies
     */
    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Movie)) return false;
        Movie other = (Movie) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.availableCopies == other.getAvailableCopies() &&
            ((this.errorMsg==null && other.getErrorMsg()==null) || 
             (this.errorMsg!=null &&
              this.errorMsg.equals(other.getErrorMsg()))) &&
            ((this.movieCategory==null && other.getMovieCategory()==null) || 
             (this.movieCategory!=null &&
              this.movieCategory.equals(other.getMovieCategory()))) &&
            this.movieCategoryId == other.getMovieCategoryId() &&
            this.movieId == other.getMovieId() &&
            ((this.movieName==null && other.getMovieName()==null) || 
             (this.movieName!=null &&
              this.movieName.equals(other.getMovieName()))) &&
            ((this.prodBanner==null && other.getProdBanner()==null) || 
             (this.prodBanner!=null &&
              this.prodBanner.equals(other.getProdBanner()))) &&
            ((this.releaseDate==null && other.getReleaseDate()==null) || 
             (this.releaseDate!=null &&
              this.releaseDate.equals(other.getReleaseDate()))) &&
            this.rentAmount == other.getRentAmount() &&
            this.totalCopies == other.getTotalCopies();
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
        _hashCode += getAvailableCopies();
        if (getErrorMsg() != null) {
            _hashCode += getErrorMsg().hashCode();
        }
        if (getMovieCategory() != null) {
            _hashCode += getMovieCategory().hashCode();
        }
        _hashCode += getMovieCategoryId();
        _hashCode += getMovieId();
        if (getMovieName() != null) {
            _hashCode += getMovieName().hashCode();
        }
        if (getProdBanner() != null) {
            _hashCode += getProdBanner().hashCode();
        }
        if (getReleaseDate() != null) {
            _hashCode += getReleaseDate().hashCode();
        }
        _hashCode += new Float(getRentAmount()).hashCode();
        _hashCode += getTotalCopies();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Movie.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model", "Movie"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableCopies");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "availableCopies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "errorMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model", "MovieCategory"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieCategoryId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieCategoryId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movieName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "movieName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodBanner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "prodBanner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("releaseDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "releaseDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rentAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "rentAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCopies");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "totalCopies"));
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
