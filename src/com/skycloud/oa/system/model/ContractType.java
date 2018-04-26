package com.skycloud.oa.system.model;




/**
 * 合同类型
 * @author jiahy
 *
 */
public class ContractType{


    // Fields    

     private Integer key;
     private String value;


    // Constructors

    /** default constructor */
    public ContractType() {
    }

    
    /** full constructor */
    public ContractType(String value) {
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