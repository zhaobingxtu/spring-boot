package com.milton.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

/**
 * @author milton.zhang
 * @description
 * @date 2017-05-22 17:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User {

    @Id
    private int id;
    private String loginname;
}
