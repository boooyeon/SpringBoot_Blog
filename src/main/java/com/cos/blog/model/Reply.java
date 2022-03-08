package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //빌더패턴
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {
   @Id //Primary key
   @GeneratedValue(strategy=GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략 사용
   private int id; //시퀀스, auto_increment
   
   @Column(nullable=false, length=200) // 대용량 데이터
   private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인 됨

   @ManyToOne //Many=Reply,  One=Board
   @JoinColumn(name="boardId")
   private Board board;
   
   @ManyToOne(fetch = FetchType.EAGER) //Many=Reply, One=User
   @JoinColumn(name="userId")
   private User user;
   
   // mappedBy 연관관계의 주인이 아니다
   // FK가 아니니까 DB컬럼을 만들지 말아라는 의미   
   @OneToMany (mappedBy = "board", fetch=FetchType.EAGER) //One=Board, Many=Reply
   private List<Reply> reply;
   
   @CreationTimestamp
   private Timestamp createDate;
}