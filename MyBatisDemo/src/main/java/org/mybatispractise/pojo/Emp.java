package org.mybatispractise.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp implements Serializable {

    private Integer eid;
    private String name;
    private Integer age;
    private Character sex;
    private String email;
    private Integer did;
    private Dept dept;
}
