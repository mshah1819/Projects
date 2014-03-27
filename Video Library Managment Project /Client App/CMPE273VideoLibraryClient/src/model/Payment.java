/**
 * Payment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;

public class Payment  implements java.io.Serializable {
    private java.lang.String cardCvv;

    private java.lang.String cardExpDate;

    private java.lang.String cardNumFour;

    private java.lang.String cardNumOne;

    private java.lang.String cardNumThree;

    private java.lang.String cardNumTwo;

    private java.lang.String cardType;

    private java.lang.String nameOnCard;

    public Payment() {
    }

    public Payment(
           java.lang.String cardCvv,
           java.lang.String cardExpDate,
           java.lang.String cardNumFour,
           java.lang.String cardNumOne,
           java.lang.String cardNumThree,
           java.lang.String cardNumTwo,
           java.lang.String cardType,
           java.lang.String nameOnCard) {
           this.cardCvv = cardCvv;
           this.cardExpDate = cardExpDate;
           this.cardNumFour = cardNumFour;
           this.cardNumOne = cardNumOne;
           this.cardNumThree = cardNumThree;
           this.cardNumTwo = cardNumTwo;
           this.cardType = cardType;
           this.nameOnCard = nameOnCard;
    }


    /**
     * Gets the cardCvv value for this Payment.
     * 
     * @return cardCvv
     */
    public java.lang.String getCardCvv() {
        return cardCvv;
    }


    /**
     * Sets the cardCvv value for this Payment.
     * 
     * @param cardCvv
     */
    public void setCardCvv(java.lang.String cardCvv) {
        this.cardCvv = cardCvv;
    }


    /**
     * Gets the cardExpDate value for this Payment.
     * 
     * @return cardExpDate
     */
    public java.lang.String getCardExpDate() {
        return cardExpDate;
    }


    /**
     * Sets the cardExpDate value for this Payment.
     * 
     * @param cardExpDate
     */
    public void setCardExpDate(java.lang.String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }


    /**
     * Gets the cardNumFour value for this Payment.
     * 
     * @return cardNumFour
     */
    public java.lang.String getCardNumFour() {
        return cardNumFour;
    }


    /**
     * Sets the cardNumFour value for this Payment.
     * 
     * @param cardNumFour
     */
    public void setCardNumFour(java.lang.String cardNumFour) {
        this.cardNumFour = cardNumFour;
    }


    /**
     * Gets the cardNumOne value for this Payment.
     * 
     * @return cardNumOne
     */
    public java.lang.String getCardNumOne() {
        return cardNumOne;
    }


    /**
     * Sets the cardNumOne value for this Payment.
     * 
     * @param cardNumOne
     */
    public void setCardNumOne(java.lang.String cardNumOne) {
        this.cardNumOne = cardNumOne;
    }


    /**
     * Gets the cardNumThree value for this Payment.
     * 
     * @return cardNumThree
     */
    public java.lang.String getCardNumThree() {
        return cardNumThree;
    }


    /**
     * Sets the cardNumThree value for this Payment.
     * 
     * @param cardNumThree
     */
    public void setCardNumThree(java.lang.String cardNumThree) {
        this.cardNumThree = cardNumThree;
    }


    /**
     * Gets the cardNumTwo value for this Payment.
     * 
     * @return cardNumTwo
     */
    public java.lang.String getCardNumTwo() {
        return cardNumTwo;
    }


    /**
     * Sets the cardNumTwo value for this Payment.
     * 
     * @param cardNumTwo
     */
    public void setCardNumTwo(java.lang.String cardNumTwo) {
        this.cardNumTwo = cardNumTwo;
    }


    /**
     * Gets the cardType value for this Payment.
     * 
     * @return cardType
     */
    public java.lang.String getCardType() {
        return cardType;
    }


    /**
     * Sets the cardType value for this Payment.
     * 
     * @param cardType
     */
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }


    /**
     * Gets the nameOnCard value for this Payment.
     * 
     * @return nameOnCard
     */
    public java.lang.String getNameOnCard() {
        return nameOnCard;
    }


    /**
     * Sets the nameOnCard value for this Payment.
     * 
     * @param nameOnCard
     */
    public void setNameOnCard(java.lang.String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Payment)) return false;
        Payment other = (Payment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cardCvv==null && other.getCardCvv()==null) || 
             (this.cardCvv!=null &&
              this.cardCvv.equals(other.getCardCvv()))) &&
            ((this.cardExpDate==null && other.getCardExpDate()==null) || 
             (this.cardExpDate!=null &&
              this.cardExpDate.equals(other.getCardExpDate()))) &&
            ((this.cardNumFour==null && other.getCardNumFour()==null) || 
             (this.cardNumFour!=null &&
              this.cardNumFour.equals(other.getCardNumFour()))) &&
            ((this.cardNumOne==null && other.getCardNumOne()==null) || 
             (this.cardNumOne!=null &&
              this.cardNumOne.equals(other.getCardNumOne()))) &&
            ((this.cardNumThree==null && other.getCardNumThree()==null) || 
             (this.cardNumThree!=null &&
              this.cardNumThree.equals(other.getCardNumThree()))) &&
            ((this.cardNumTwo==null && other.getCardNumTwo()==null) || 
             (this.cardNumTwo!=null &&
              this.cardNumTwo.equals(other.getCardNumTwo()))) &&
            ((this.cardType==null && other.getCardType()==null) || 
             (this.cardType!=null &&
              this.cardType.equals(other.getCardType()))) &&
            ((this.nameOnCard==null && other.getNameOnCard()==null) || 
             (this.nameOnCard!=null &&
              this.nameOnCard.equals(other.getNameOnCard())));
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
        if (getCardCvv() != null) {
            _hashCode += getCardCvv().hashCode();
        }
        if (getCardExpDate() != null) {
            _hashCode += getCardExpDate().hashCode();
        }
        if (getCardNumFour() != null) {
            _hashCode += getCardNumFour().hashCode();
        }
        if (getCardNumOne() != null) {
            _hashCode += getCardNumOne().hashCode();
        }
        if (getCardNumThree() != null) {
            _hashCode += getCardNumThree().hashCode();
        }
        if (getCardNumTwo() != null) {
            _hashCode += getCardNumTwo().hashCode();
        }
        if (getCardType() != null) {
            _hashCode += getCardType().hashCode();
        }
        if (getNameOnCard() != null) {
            _hashCode += getNameOnCard().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Payment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model", "Payment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardCvv");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cardCvv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardExpDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cardExpDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNumFour");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cardNumFour"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNumOne");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cardNumOne"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNumThree");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cardNumThree"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNumTwo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cardNumTwo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "cardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameOnCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model", "nameOnCard"));
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
