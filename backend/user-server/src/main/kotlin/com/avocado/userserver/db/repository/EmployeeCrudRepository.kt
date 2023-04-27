package com.avocado.userserver.db.repository

import com.avocado.userserver.db.entity.Employee
import com.avocado.userserver.db.entity.Person
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
//interface EmployeeCrudRepository : CoroutineCrudRepository<Employee, String> {
interface EmployeeCrudRepository : ReactiveCrudRepository<Employee, String> {
    @Query("select * from person where firstname = :firstname")
    fun findAllByFirstname(firstname: String): Flux<Person>
}