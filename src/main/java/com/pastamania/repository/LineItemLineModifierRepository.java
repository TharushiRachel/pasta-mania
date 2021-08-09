package com.pastamania.repository;

import com.pastamania.entity.LineItemLineDiscount;
import com.pastamania.entity.LineItemLineModifier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface LineItemLineModifierRepository extends JpaRepository<LineItemLineModifier,Long> {


}
