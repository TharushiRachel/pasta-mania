package com.pastamania.service;

import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface CategoryService {

    void retrieveCategoryAndPersist(Date date);

    void initialCategoryPersist();
}
