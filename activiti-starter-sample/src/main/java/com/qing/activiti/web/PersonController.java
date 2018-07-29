package com.qing.activiti.web;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.qing.activiti.entity.Person;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping(value = "list")
    public Object create() {
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ProcessEngineConfiguration config = engine.getProcessEngineConfiguration();
        // 获取实体管理工厂
        EntityManagerFactory factory = (EntityManagerFactory)config.getJpaEntityManagerFactory();
        EntityManager em = factory.createEntityManager();
        // 开启事务
        em.getTransaction().begin();
        // 写入新数据
        Person p1 = new Person();
        p1.setName("Angus");
        p1.setAge(30);
        // 持久化对象
        em.persist(p1);
        // 提交事务
        em.getTransaction().commit();
        // 查询数据
        Query query = em.createQuery("SELECT p FROM Person p");
        List<Person> persons = query.getResultList();
        // 输出查询数据
        for(Person p : persons) {
            System.out.println("名称：" + p.getName() + ", 年龄：" + p.getAge());
        }
        return persons;
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "调用流程存储服务，查询部署数量："
                + repositoryService.createDeploymentQuery().count();
    }
}
