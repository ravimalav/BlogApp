package com.Blog.project.BlogApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private Integer categoryId;
    @Column(name="Title",length = 100,nullable = false)
    private String categoryTitle;
    @Column(name="Description")
    private String  categoryDesc;



     @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)

    private List<Post> posts=new ArrayList<>();
}
