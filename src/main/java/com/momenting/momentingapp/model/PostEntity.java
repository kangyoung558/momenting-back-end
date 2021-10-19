package com.momenting.momentingapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_post")
public class PostEntity extends BaseTimeEntity{
    @Id
    @Column(name = "post_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String userId;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private String writer;

    private boolean secret;

}
