/*
 * Generated by Design2see. All rights reserved.
 */
package com.d2s.framework.test.model.domain;

/**
 * Employee entity.
 * <p>
 * Generated by Design2see. All rights reserved.
 * <p>
 * 
 * @hibernate.mapping default-access =
 *                    "com.d2s.framework.model.persistence.hibernate.property.EntityPropertyAccessor"
 *                    package = "com.d2s.framework.sample.model.domain"
 * @hibernate.class table = "EMPLOYEE" dynamic-insert = "true" dynamic-update =
 *                  "true" persister =
 *                  "com.d2s.framework.model.persistence.hibernate.entity.persister.EntityProxySingleTableEntityPersister"
 * @author Generated by Design2see
 */
public interface Employee extends com.d2s.framework.test.model.domain.Person {

  /**
   * Gets the hireDate.
   * 
   * @hibernate.property type = "date"
   * @hibernate.column name = "HIRE_DATE"
   * @return the hireDate.
   */
  java.util.Date getHireDate();

  /**
   * Sets the hireDate.
   * 
   * @param hireDate
   *          the hireDate to set.
   */
  void setHireDate(java.util.Date hireDate);

  /**
   * Gets the annualSalary.
   * 
   * @hibernate.property
   * @hibernate.column name = "ANNUAL_SALARY" scale = "7"
   * @return the annualSalary.
   */
  java.lang.Integer getAnnualSalary();

  /**
   * Sets the annualSalary.
   * 
   * @param annualSalary
   *          the annualSalary to set.
   */
  void setAnnualSalary(java.lang.Integer annualSalary);

  /**
   * Gets the manager.
   * 
   * @hibernate.many-to-one cascade = "save-update,lock"
   * @hibernate.column name = "MANAGER_ID"
   * @return the manager.
   */
  com.d2s.framework.test.model.domain.Employee getManager();

  /**
   * Sets the manager.
   * 
   * @param manager
   *          the manager to set.
   */
  void setManager(com.d2s.framework.test.model.domain.Employee manager);

  /**
   * Gets the managedEmployees.
   * 
   * @hibernate.set cascade =
   *                "persist,merge,save-update,lock,refresh,evict,replicate"
   *                inverse = "true" order-by="NAME"
   * @hibernate.key column = "MANAGER_ID"
   * @hibernate.one-to-many class =
   *                        "com.d2s.framework.sample.model.domain.Employee"
   * @return the managedEmployees.
   */
  java.util.Set<com.d2s.framework.test.model.domain.Employee> getManagedEmployees();

  /**
   * Sets the managedEmployees.
   * 
   * @param managedEmployees
   *          the managedEmployees to set.
   */
  void setManagedEmployees(
      java.util.Set<com.d2s.framework.test.model.domain.Employee> managedEmployees);

  /**
   * Adds an element to the managedEmployees.
   * 
   * @param managedEmployeesElement
   *          the managedEmployees element to add.
   */
  void addToManagedEmployees(
      com.d2s.framework.test.model.domain.Employee managedEmployeesElement);

  /**
   * Removes an element from the managedEmployees.
   * 
   * @param managedEmployeesElement
   *          the managedEmployees element to remove.
   */
  void removeFromManagedEmployees(
      com.d2s.framework.test.model.domain.Employee managedEmployeesElement);

  /**
   * Gets the department.
   * 
   * @hibernate.many-to-one cascade = "save-update,lock"
   * @hibernate.column name = "DEPARTMENT_ID"
   * @return the department.
   */
  com.d2s.framework.test.model.domain.Department getDepartment();

  /**
   * Sets the department.
   * 
   * @param department
   *          the department to set.
   */
  void setDepartment(com.d2s.framework.test.model.domain.Department department);

  /**
   * Gets the employeeProjects.
   * 
   * @hibernate.set cascade = "none" table = "PROJECT_PROJECT_MEMBERS" inverse =
   *                "true"
   * @hibernate.key column = "EMPLOYEE_ID"
   * @hibernate.many-to-many class =
   *                         "com.d2s.framework.sample.model.domain.Project"
   *                         column = "PROJECT_ID"
   * @return the employeeProjects.
   */
  java.util.Set<com.d2s.framework.test.model.domain.Project> getEmployeeProjects();

  /**
   * Sets the employeeProjects.
   * 
   * @param employeeProjects
   *          the employeeProjects to set.
   */
  void setEmployeeProjects(
      java.util.Set<com.d2s.framework.test.model.domain.Project> employeeProjects);

  /**
   * Adds an element to the employeeProjects.
   * 
   * @param employeeProjectsElement
   *          the employeeProjects element to add.
   */
  void addToEmployeeProjects(
      com.d2s.framework.test.model.domain.Project employeeProjectsElement);

  /**
   * Removes an element from the employeeProjects.
   * 
   * @param employeeProjectsElement
   *          the employeeProjects element to remove.
   */
  void removeFromEmployeeProjects(
      com.d2s.framework.test.model.domain.Project employeeProjectsElement);

}
