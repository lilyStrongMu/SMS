package com.shf.spring.sms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("gaoli_test")
public class GaoliTest {
  @TableId
  private Long id;
  private String name;
  private Long sex;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private java.sql.Date birthday;
  private Long tel;


  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Long getSex() {
    return sex;
  }

  public void setSex(long sex) {
    this.sex = sex;
  }


  public java.sql.Date getBirthday() {
    return birthday;
  }

  public void setBirthday(java.sql.Date birthday) {
    this.birthday = birthday;
  }


  public Long getTel() {
    return tel;
  }

  public void setTel(long tel) {
    this.tel = tel;
  }

}
