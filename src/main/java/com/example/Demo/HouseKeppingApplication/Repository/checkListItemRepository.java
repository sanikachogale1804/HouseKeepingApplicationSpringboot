package com.example.Demo.HouseKeppingApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.Demo.HouseKeppingApplication.Entity.checkListItem;

@RepositoryRestResource(path = "checkListItems")
public interface checkListItemRepository extends JpaRepository<checkListItem, Long>{

}
