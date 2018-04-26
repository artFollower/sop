package com.skycloud.oa.system.model;

/**
 * 批文等级定义表
 * @author jiahy
 *
 */
public class OfficeGrade{



     private Integer key;
     private String value;


    // Constructors

    /** default constructor */
    public OfficeGrade() {
    }

    
    /** full constructor */
    public OfficeGrade(String value) {
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