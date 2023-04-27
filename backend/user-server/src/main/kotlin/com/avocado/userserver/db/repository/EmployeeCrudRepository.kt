package com.avocado.userserver.db.repository

import com.avocado.userserver.db.entity.Employee
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
//interface EmployeeCrudRepository : CoroutineCrudRepository<Employee, String> {
interface EmployeeCrudRepository : ReactiveCrudRepository<Employee, String> {

}