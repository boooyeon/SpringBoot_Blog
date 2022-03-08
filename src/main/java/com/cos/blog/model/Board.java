package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Board {

   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY) //auto_increment
   private int id;
   
   @Column(nullable = false, length = 100)
   private String title;
   
   @Lob // 대용량 데이터
   private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인 됨
   
   @ColumnDefault("0")
   private int count; //조회수
   
   @ManyToOne //Many=Board, One=User
   @JoinColumn(name="userId") //필드값이 userId로 만들어짐
   //DB는 오브젝트를 저장할 수 없다. 그래서 FK사용, 자바는 오브젝트를 저장할 수 있다(자바와 DB사이의 충돌)
   //따라서, 자바 DB의 자료형에 맞춰서 테이블을 만든다
   private User user; 
   
   @CreationTimestamp
   private Timestamp createDate;
}