package com.skycloud.oa.system.dto;



/**
 * ContractType entity. @author MyEclipse Persistence Tools
 */

public class ContractTypeDto  implements java.io.Serializable {


    // Fields    

     private Integer key;
     private String value;
     private String letter;
 	
 	public String getLetter() {
 		return letter;
 	}
 	public void setLetter(String letter) {
 		this.letter = letter;
 	}

    // Constructors

    /** default constructor */
    public ContractTypeDto() {
    }

    
    /** full constructor */
    public ContractTypeDto(String value) {
        this.value = value;
    }

   
    // Property accessors

    public Integer getKey() {
        return this.key;
    }
    
    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
   








}