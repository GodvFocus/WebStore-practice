package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
// Serializable 接口的主要作用是标记一个类的对象可以被序列化。
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
