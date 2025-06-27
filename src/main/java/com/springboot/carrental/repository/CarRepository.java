package com.springboot.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer>{

	@Query("select c from Car c where status='AVAILABLE'")
	List<Car> getAllAvailable();

	@Query("select c from Car c where category=?1")
	List<Car> searchByCategory(String category);
//	using @Param
//	@Query("select c from Car c where c.branch.name= :branchName")
//	List<Car> searchByBranch(@Param(value = "branchName") String branchName);
	
	@Query("select c from Car c where branch.location like concat('%',?1,'%') AND status='AVAILABLE'")
	List<Car> searchByBranch(String branchName);

	@Query("select c from Car c where brand=?1")
	List<Car> searchByBrand(String brandName);

	
	
	@Query("select c from Car c where category like concat('%',?1,'%')"+
	"OR brand like concat('%',?1,'%')"+
	"OR model like concat('%',?1,'%')"+
	"OR c.branch.name like concat('%',?1,'%')"+
	"OR status like concat('%',?1,'%')")
	List<Car> searchByKeyword(String keyword);

	
	@Query("select c from Car c where c.lender.user.username=?1")
	List<Car> getByLender(String name);


//	using normal value assigning
//	@Query("select c from Car c where c.branch.name=?1")
//	List<Car> searchByBranch(String branchName);

}
