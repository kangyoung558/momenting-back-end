package com.momenting.momentingapp.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tbl_post")
public class Post extends BaseTimeEntity{
    @Id
    @Column(name = "post_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "userid")
    private String userId;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private String writer;

    private boolean secret;

}
