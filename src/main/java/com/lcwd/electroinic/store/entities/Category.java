package com.lcwd.electroinic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="categories")
public class Category {
    @Id
    @Column(name="ID")
    private String categoryId;
    @Column(name="category_title")
    private String title;
    @Column(name="category_desc")
    private String description;
    private String coverImage;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products=new ArrayList<>();

}
