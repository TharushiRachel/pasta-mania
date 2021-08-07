//package com.pastamania.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Set;
//
///**
// * @author Pasindu Lakmal
// */
//@Entity
//@Data
//public class Modifier extends CreateModifyBaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long modifierId;
//
//    private String id;
//
//    private String name;
//
//    private Integer position;
//
//    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
//    private Set<Store> stores;
//
//    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
//    private Set<ModifierOption> modifierOptions;
//
//}
