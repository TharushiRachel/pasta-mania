package com.pastamania.service;

import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface StoreService {

    void retrieveCategoryAndPersist(Date date);

    void initialStorePersist();
}
